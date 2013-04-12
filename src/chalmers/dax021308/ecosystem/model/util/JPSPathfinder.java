package chalmers.dax021308.ecosystem.model.util;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.obstacle.AbstractObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;


public class JPSPathfinder {
	
	private List<IObstacle> obsList;
	private IShape shape;
	private Dimension simulationDim;
	private double coordinateScaling;

	public JPSPathfinder(List<IObstacle> obsList, IShape shape, Dimension simulationDim) {
		this.obsList = obsList;
		this.shape = shape;
		this.simulationDim = simulationDim;
	}
	
	public List<Position> getShortestPath(Position start, Position end) {
		return getShortestPathJumpPointsSearch(start, end);
	}


	/**
	 * Internal Position class for use with A* shortest path algorithm.
	 * 
	 * @author Erik
	 *
	 */
	private class AStarPosition extends Position {
		double g_score;
		double f_score;
		AStarPosition came_from;
		public AStarPosition(double x, double y) {
			super(x,y);
		}

		public AStarPosition(AStarPosition a) {
			super(a.x,a.y);
		}

		public double getGF() {
			return g_score+f_score;
		}
		
		@Override
		public String toString() {
			return "AStarPosition G: " + g_score + " F: " + f_score + " " + super.toString();
		}
		
		public AStarPosition shift(double x, double y) {
			this.x += x;
			this.y += y;
			return this;
		}
		
		protected double getG() {
			if (g_score == 0) {
				final AStarPosition parent = came_from;
				g_score = parent != null ? parent.getG() + parent.getMoveCost(this) : 0.0D;
			}
			return g_score;
		}

		public double getMoveCost(AStarPosition node) {
			if (node == null)
				return 0;
			double x = node.getX(), y = node.getY(), px = getX(), py = getY();
			double steps = getSteps(x, y, px, py);
			return x == px || y == px ? steps : (double) steps * 1;
		}
		
		private double getSteps(double x, double y, double px, double py) {
			double temp;
			if ((temp = x - px) != 0) { // straight, horizontal AND will handle
										// diagonal
			} else if ((temp = y - py) != 0) { // straight, vertical
			} else
				return 0;
			return Math.abs(temp);
		}
		
		public AStarPosition derive(double x, double y) {
			return clone().shift(x, y);
		}

		@Override
		public AStarPosition setPosition(double x, double y) {
			this.x = x;
			this.y = y;
			return this;
		}

		
		@Override
		public AStarPosition clone() {
			AStarPosition clone = new AStarPosition(x, y);
			clone.came_from = this;
			return clone;
		}
	}
	

	private List<Position> reconstructPath(AStarPosition current_node) {
		List<Position> result = new ArrayList<Position>();
		AStarPosition current = current_node;
		while(current != null) {
			result.add(current);
			current = current.came_from;
		}
	    Collections.reverse(result);
		return result;
	}

	public List<Position> getShortestPathJumpPointsSearch(Position startPos, Position endPos) {
			AStarPosition start = new AStarPosition(startPos.getX(), startPos.getY());
			AStarPosition goal = new AStarPosition(endPos.getX(), endPos.getY());
			HashSet<AStarPosition> closedSet = new HashSet<AStarPosition>();
			HashSet<AStarPosition> openSet = new HashSet<AStarPosition>();
			openSet.add(start);
			//start.g_score = 0.0;
			//start.f_score = start.getDistance(goal);
			AStarPosition current = start;// the node in openset having the lowest
										// f_score[] value
			double lowScore = Integer.MAX_VALUE;
			while (!openSet.isEmpty()) {
				// Get the AStarPosition with the lowest estimated distance to target.
				lowScore = Integer.MAX_VALUE;
				for (AStarPosition n : openSet) {
					double score = n.f_score;
					if (score < lowScore) {
						current = n;
						lowScore = score;
					} else if(score == lowScore) {
						if(n != current) {
							current = n;
						}
					}
				}
//				();
//				try {
//					Thread.sleep(ITERATION_TIME);
//				} catch (InterruptedException e) {
//					
//				}
//				 Log.v("openset" + openSet);
////				 Log.v("closedSet" + closedSet);
////				 Log.v("g_score" + current.g_score);
////				 Log.v("f_score" + current.f_score);
//				 Log.v("current: " + current);
//				 Log.v("--------");
				if (current.equals(goal)) {
					return reconstructPath(current);
				}
				openSet.remove(current);
				closedSet.add(current);
				for(AStarPosition neighbour : getJPSNeighbours(current, obsList)) {
					AStarPosition jumpPoint = jump(neighbour, current, goal, obsList, shape);
					if (jumpPoint != null) {
						if (closedSet.contains(jumpPoint))
							continue;
						if (!openSet.contains(jumpPoint)) {
							jumpPoint.came_from = current;
							openSet.add(jumpPoint);
						} else if ((current.getG() + current.getMoveCost(jumpPoint)) < jumpPoint.getG()) { // G score of node with current node as it's parent
							AStarPosition instanceNode = retrieveInstance(openSet, jumpPoint);
							if (instanceNode != null)
								instanceNode.came_from = current;
						}
					}
				}
			}
			// Failure to find path.
			Log.e("Failed to find path to target!");
			return Collections.emptyList();
	}
	
	private AStarPosition retrieveInstance(HashSet<AStarPosition> openSet, AStarPosition node) {
		if (node == null)
			return null;
		final Iterator<AStarPosition> nI = openSet.iterator();
		while (nI.hasNext()) {
			final AStarPosition n = nI.next();
			if (node.equals(n))
				return n;
		}
		return null;
	}

	private AStarPosition[] getJPSNeighbours(AStarPosition current,	List<IObstacle> obsList) {
		double x = current.getX();
		double y = current.getY();
		LinkedList<AStarPosition> nodes = new LinkedList<AStarPosition>();
		AStarPosition parent = current.came_from;
		if (parent != null) { // determines whether to prune neighbors
			// normalize
			AStarPosition norm = normalizeDirection(x, y, parent.getX(), parent.getY());
			double dx = norm.getX()*coordinateScaling;
			double dy = norm.getY()*coordinateScaling;
			AStarPosition temp = new AStarPosition(current);
			if (((int) dx & (int) dy) != 0) { // diagonal direction
				// check straight directions in the direction of the diagonal move
				if (!AbstractObstacle.isInsideObstacleList(obsList, temp.setPosition(x, y + dy)))
					nodes.add(new AStarPosition(temp));
				if (!AbstractObstacle.isInsideObstacleList(obsList,temp.setPosition(x + dx, y)))
					nodes.add(new AStarPosition(temp));
				if (!AbstractObstacle.isInsideObstacleList(obsList,temp.setPosition(x + dx, y + dy)))
					nodes.add(new AStarPosition(temp));
				// forced neighbor checks
				if (AbstractObstacle.isInsideObstacleList(obsList,temp.setPosition(x - dx, y)))
					nodes.add(new AStarPosition(temp.shift(0, dy)));
				if (AbstractObstacle.isInsideObstacleList(obsList,temp.setPosition(x, y - dy)))
					nodes.add(new AStarPosition(temp.shift(dx, 0)));
			} else { // straight direction
				if (!AbstractObstacle.isInsideObstacleList(obsList,temp.setPosition(x + dx, y + dy))) {
					nodes.add(new AStarPosition(temp));
					// forced neighbor checks
					if (AbstractObstacle.isInsideObstacleList(obsList,temp.setPosition(x + dy, y + dx)))
						nodes.add(new AStarPosition(temp.shift(dx, dy)));
					if (AbstractObstacle.isInsideObstacleList(obsList,temp.setPosition(x - dy, y - dx)))
						nodes.add(new AStarPosition(temp.shift(dx, dy)));
				}
			}
		} else {
		// no parent, return all that aren't blocked
		AStarPosition[] ns = new AStarPosition[] { new AStarPosition(x, y - coordinateScaling), new AStarPosition(x + coordinateScaling, y - coordinateScaling), new AStarPosition(x + coordinateScaling, y), new AStarPosition(x + coordinateScaling, y + coordinateScaling),
		new AStarPosition(x, y + coordinateScaling), new AStarPosition(x - coordinateScaling, y + coordinateScaling), new AStarPosition(x - coordinateScaling, y), new AStarPosition(x - coordinateScaling, y - coordinateScaling) };
		for (int i = 0; i < ns.length; i++) {
			if (!AbstractObstacle.isInsideObstacleList(obsList, ns[i]))
				nodes.add(ns[i]);
			}
		}
		return nodes.toArray(new AStarPosition[nodes.size()]);
	}

	
	private AStarPosition normalizeDirection(double x, double y, double d, double e) {
		double dx = x - d, dy = y - e;
		dx /= Math.max(Math.abs(dx), 1);
		dy /= Math.max(Math.abs(dy), 1);
		return new AStarPosition(dx, dy);
	}
	
	private AStarPosition jump(AStarPosition node, AStarPosition parent, AStarPosition goal, List<IObstacle> obsList, IShape shape) {
		double x = node.getX(), y = node.getY(), px = parent.getX(), py = parent.getY();
		double dx = (x - px);
		double dy = (y - py);
		Log.e("Coordinatescaling: " + coordinateScaling);
		Log.e("dy: " + dy + " dx: " + dx);
//		Log.e("JUMP! node: " + node.toString() + " parent: " + parent.toString() + " dx: " + dx + " dy: " + dy);
		if (!walkable(node)) // check blocked
			return null;
		if (node.equals(goal)) // reached goal
			return new AStarPosition(node);
		
		// resolve forced neighbors
		AStarPosition temp = new AStarPosition(node);
		if (((int) dx & (int) dy) != 0) { // diagonal
			if ((walkable(temp.setPosition(x - dx, y + dy)) && !walkable(temp.setPosition(x - dx, y))) ||
			(walkable(temp.setPosition(x + dx, y - dy)) && !walkable(temp.setPosition(x, y - dy)))) {
				return new AStarPosition(node);
			}
			// recurse
			AStarPosition h = jump(node.derive(dx, 0), node, goal, obsList, shape);
			if (h != null)
				return new AStarPosition(node);
			AStarPosition v = jump(node.derive(0, dy), node, goal, obsList, shape);
			if (v != null)
				return new AStarPosition(node);
			} else if (dx == 0) { // vertical, dx = 0, dy = 1 or -1
				if ((walkable(temp.setPosition(x + coordinateScaling, y + dy)) && !walkable(temp.setPosition(x + coordinateScaling, y))) ||
						(walkable(temp.setPosition(x - coordinateScaling, y + dy)) && !walkable(temp.setPosition(x - coordinateScaling, y)))) {
					return new AStarPosition(node);
				}
		} else { // horizontal, dx = 1 or -1, dy = 0
			if ((walkable(temp.setPosition(x + dx, y + coordinateScaling)) && !walkable(temp.setPosition(x, y + coordinateScaling))) ||
					(walkable(temp.setPosition(x + dx, y - coordinateScaling)) && !walkable(temp.setPosition(x, y - coordinateScaling)))) {
				return new AStarPosition(node);
			}
		}
//		repaint();

		return jump(node.derive(dx, dy), node, goal, obsList, shape);
	}
	
	private boolean walkable(AStarPosition node) {
		return !AbstractObstacle.isInsideObstacleList(obsList, node) && shape.isInside(simulationDim, node);
	}
}
