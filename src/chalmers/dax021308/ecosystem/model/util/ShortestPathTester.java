package chalmers.dax021308.ecosystem.model.util;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chalmers.dax021308.ecosystem.model.environment.obstacle.AbstractObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.RectangularObstacle;
import chalmers.dax021308.ecosystem.model.util.shape.IShape;
import chalmers.dax021308.ecosystem.model.util.shape.SquareShape;

/**
 * Class for testing A* algorithm.
 * 
 * @author Erik Ramqvist
 *
 */
public class ShortestPathTester extends JPanel {
	private static final long serialVersionUID = 3766084045600317521L;
	private static final double HEURISTIC_UPSAMPLE = 1;
	private static final long ITERATION_TIME = 20;
	private static final double coordinateScaling = 10;
	private static final Dimension simulationDimension = new Dimension(750, 750);
	
	private static HashSet<AStarPosition> closedSet;
	private static HashSet<AStarPosition> openSet;
	private static AStarPosition current;
	
	private static List<IObstacle> obsList = new ArrayList<IObstacle>();
	static {
		obsList.add(new RectangularObstacle(200, 50, new Position(250, 400), Color.GRAY,0, true));
		obsList.add(new RectangularObstacle(50, 200, new Position(400, 250), Color.GRAY,0, true));
	}
	
	private AStarPosition start;
	private AStarPosition goal;
	private List<Position> result;
	private AStarPosition jumpNode;
	
	
	public ShortestPathTester() {

		JFrame frame = new JFrame();
		frame.setSize(new Dimension(750, 750));
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		setBackground(Color.WHITE);
		
		Position start = new Position(140.4120, 149.0); 
		Position end = new Position(500, 500); 
		Dimension dim = new Dimension(700, 700);
		goal = new AStarPosition(end.x, end.y);
		if(AbstractObstacle.isInsideObstacleList(obsList, start) || AbstractObstacle.isInsideObstacleList(obsList, end)) {
			Log.e("Either positions is inside obstacle.");
		}
		System.out.println("Distance: " + start.getDistance(end));
		long time;
		double elapsed;
		boolean executeJPS = false;
		boolean tryRealShortestPath = true;
		Stat<Double> stat = new Stat<Double>();
		IShape shape = new SquareShape();
		if(tryRealShortestPath) {
			for(int i = 0; i < 10; i++) {
				time = System.nanoTime();
				result = Position.getShortestPath(start, end, obsList, shape, dim, 0);
				elapsed = (System.nanoTime() - time)*0.000001;
				System.out.println("Real GetShortest path Completed in: " + elapsed + " ms. Positions: " + result.size());
				stat.addObservation(elapsed);
			}
		} else {
			if(executeJPS ) {
				for(int i = 0; i < 1; i++) {
					time = System.nanoTime();
					result = getShortestPath(start, end, obsList);
					elapsed = (System.nanoTime() - time)*0.000001;
					System.out.println("JumpPointsSearch Completed in: " + elapsed + " ms. Positions: " + result.size());
					stat.addObservation(elapsed);
				}
			} else {
				for(int i = 0; i < 10; i++) {
					time = System.nanoTime();
					result = getShortestPathHashSet(start, end, obsList);
					elapsed = (System.nanoTime() - time)*0.000001;
					System.out.println("HashSet Completed in: " + elapsed + " ms. Positions: " + result.size());
					stat.addObservation(elapsed);
				}
			}
		}
		Log.v("Mean value:" + stat.getMean());
		time = System.nanoTime();
		if(AbstractObstacle.isInsidePathList(obsList, start, end)) {
			Log.v("IS INSIDE PATH");
		} else {
			Log.v("IS NOT INSIDE PATH");
		}
		elapsed = (System.nanoTime() - time)*0.000001;
		System.out.println("AbstractObstacle.isInsidePathList Completed in: " + elapsed);
		//		time = System.nanoTime();
//		result = Position.getShortestPath(start, end);
//		elapsed = (System.nanoTime() - time)*0.000001;
//		System.out.println("Optimal solution Completed in: " + elapsed + " ms. Positions: " + result.size() + " Result: " + result);
/*
		time = System.nanoTime();
		result = getShortestPath(start, end, obsList);
		elapsed = (System.nanoTime() - time)*0.000001;
		System.out.println("LinkedList Completed in: " + elapsed + " ms. Positions: " + result.size());*/

		repaint();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		for(IObstacle o : obsList ) {
			if(o instanceof RectangularObstacle) {
				g.setColor(Color.GRAY);
				g.fillRect((int)(o.getPosition().getX()-o.getWidth()+1),
						(int)((o.getPosition().getY()-o.getHeight()+1)),
						(int)o.getWidth()*2 ,
						(int)o.getHeight()*2 );
			}
		}
		if(closedSet != null) {
			for(AStarPosition a : closedSet) {
				drawAStarPosition(a, g, Color.BLACK);
			}
		}
		if(openSet != null) {
			for(AStarPosition a : openSet) {
				drawAStarPosition(a, g, Color.BLUE);
				
			}
		}
		if(current != null) {
			drawAStarPosition(current, g, Color.GREEN);
		}

		if(jumpNode != null) {
				drawAStarPosition(jumpNode, g, Color.BLACK);
		}
		
		if(result != null) {
			for(Position a : result) {
				drawAStarPosition(a, g, Color.RED);
			}
		}

		if(goal != null) {
			drawAStarPosition(goal, g, Color.GREEN);
		}
	}

	
	
	private void drawAStarPosition(Position p, Graphics g, Color c) {
		//draw position
		g.setColor(c);
		g.fillRect((int) (p.getX()-coordinateScaling/2),
				(int)  (p.getY()-coordinateScaling/2),
				(int) coordinateScaling,
				(int) coordinateScaling);
	}
	
	public List<Position> getShortestPath(Position startPos, Position endPos, List<IObstacle> obsList) {
		double distance = startPos.getDistance(endPos);
		if(distance > 55) {
			double x = Math.round((endPos.getX() / coordinateScaling))*coordinateScaling;
			double y = Math.round((endPos.getY() / coordinateScaling))*coordinateScaling;
			Position newEnd = new Position(x, y);
			x = Math.round((startPos.getX() / coordinateScaling))*coordinateScaling;
			y = Math.round((startPos.getY() / coordinateScaling))*coordinateScaling;
			Position newStartPos = new Position(x, y);
			goal = new AStarPosition(newEnd.x, newEnd.y);
//			Log.e(newEnd + " " + newStartPos);
			return getShortestPathJumpPointsSearch(newStartPos, newEnd, obsList, coordinateScaling);
		} 
		return getShortestPathJumpPointsSearch(startPos, endPos, obsList, 1);
	}
	
	/**
	 * Calculates the shortest path to the target using A* search algorithm.
	 * <p>
	 * Fast for long distances.
	 * <p>
	 * TODO: Supply a obstacle-list and Shape?
	 * TODO: Make private when finished.
	 * 
	 * For use with target agents behind obstacles.
	 * 
	 * @see <a href="http://en.wikipedia.org/wiki/A*_search_algorithm">http://en.wikipedia.org/wiki/A*_search_algorithm</a>
	 * @param target
	 * @return
	 * @author Erik Ramqvist
	 */
	public List<Position> getShortestPathPriorityQueue(Position startPos, Position endPos, List<IObstacle> obsList, int coordinateUpsampling/*, IShape simShape*/) {
		start = new AStarPosition(startPos.getX(), startPos.getY());
		goal = new AStarPosition(endPos.getX(), endPos.getY());
		start.g_score = 0;
		start.f_score = heuristic_manhattan_distance(start, goal)*HEURISTIC_UPSAMPLE;
		closedSet = new HashSet<AStarPosition>();
		PriorityQueue<AStarPosition> openSet = new PriorityQueue<AStarPosition>(50, new AStarPositionComparator());
		openSet.add(start);
		current = start;//the node in openset having the lowest f_score[] value
		while(!openSet.isEmpty()) {
			Log.v("openset" + openSet);
			Log.v("closedSet" + closedSet);
//			Log.v("g_score" + g_score);
//			Log.v("f_score" + f_score);
			Log.v("current" + current);
			Log.v("--------");
			current = openSet.poll();
			
			//Slow down to see movement.
			
			repaint();
			try {
				Thread.sleep(ITERATION_TIME);
			} catch (InterruptedException e) {
				
			}
			closedSet.add(current);
			if(current.equals(goal)) {
				return reconstructPath(current);
			}
			for(AStarPosition neighbour : getNeighbours(current, obsList, coordinateUpsampling)) {
				double tentative_g_score = current.g_score + 1;
				if(closedSet.contains(neighbour)) {
					if(tentative_g_score >= neighbour.g_score) {
						continue;
					}
				}
				if(!openSet.contains(neighbour) || tentative_g_score < neighbour.g_score) {
					neighbour.came_from = current;
					neighbour.g_score = tentative_g_score;
					neighbour.f_score = neighbour.g_score + heuristic_manhattan_distance(goal, neighbour)*HEURISTIC_UPSAMPLE;
					if(!openSet.contains(neighbour)) {
						openSet.add(neighbour);
					}
				}
			}
		}	
		//Failure to find path.
		Log.e("Failed to find path to target!");
		return Collections.emptyList();
	}
	

	/**
	 * Calculates the shortest path to the target using A* search algorithm.
	 * <p>
	 * Fast for short distances.
	 * <p>
	 * TODO: Supply a obstacle-list and Shape?
	 * TODO: Make private when finished.
	 * 
	 * For use with target agents behind obstacles.
	 * 
	 * @see <a
	 *      href="http://en.wikipedia.org/wiki/A*_search_algorithm">http://en.wikipedia.org/wiki/A*_search_algorithm</a>
	 * @param target
	 * @return
	 * @author Erik Ramqvist
	 */
	public List<Position> getShortestPathHashSet(Position startPos,
			Position endPos, List<IObstacle> obsList/*, IShape simShape */) {
		AStarPosition start = new AStarPosition(startPos.getX(), startPos.getY());
		AStarPosition goal = new AStarPosition(endPos.getX(), endPos.getY());
		closedSet = new HashSet<AStarPosition>();
		openSet = new HashSet<AStarPosition>();
		openSet.add(start);
		start.g_score = 0.0;
		start.f_score = start.getDistance(goal)*HEURISTIC_UPSAMPLE;
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

			repaint();
			try {
				Thread.sleep(ITERATION_TIME);
			} catch (InterruptedException e) {
				
			}
			 Log.v("openset" + openSet);
//			 Log.v("closedSet" + closedSet);
//			 Log.v("g_score" + current.g_score);
//			 Log.v("f_score" + current.f_score);
			 Log.v("current: " + current);
			 Log.v("--------");
			if (current.equals(goal)) {
				return reconstructPath(current);
			}
			openSet.remove(current);
			closedSet.add(current);
			for (AStarPosition neighbour : getNeighbours(current, obsList)) {
				double tentative_g_score = current.g_score + 1;
				if (closedSet.contains(neighbour)) {
					if (tentative_g_score >= neighbour.g_score) {
						continue;
					}
				}
				if (!openSet.contains(neighbour) || tentative_g_score < neighbour.g_score) {
					neighbour.came_from = current;
					neighbour.g_score = tentative_g_score;
					neighbour.f_score = tentative_g_score + neighbour.getDistance(goal)*HEURISTIC_UPSAMPLE;
					if (!openSet.contains(neighbour)) {
						openSet.add(neighbour);
					}
				}
				Log.e(neighbour.toString());
			}
		}
		// Failure to find path.
		Log.e("Failed to find path to target!");
		return Collections.emptyList();
	}
	

	private static List<Position> reconstructPath(AStarPosition current_node) {
		List<Position> result = new ArrayList<Position>();
		AStarPosition current = current_node;
		while(current != null) {
			result.add(current);
			current = current.came_from;
		}
	    Collections.reverse(result);
		return result;
	}
	
	private static class AStarPositionComparator implements Comparator<AStarPosition> {

		@Override
		public int compare(AStarPosition o1, AStarPosition o2) {
			if(o1.getGF() > o2.getGF()) {
				return 1;
			} else if(o1.getGF() < o2.getGF()) {
				return -1;
			}
			return 0;
		}
	}
	

	/**
	 * Gets the neighbours of one AStarPosition.
	 * <p>
	 * Is shape really needed here? Since we guarantee Agents are inside the shape.
	 * 
	 * @param p1
	 * @param obsList
	 * @param shape
	 * @return
	 */
	public static List<AStarPosition> getNeighbours(AStarPosition p1, List<IObstacle> obsList/*, IShape shape*/) {
		List<AStarPosition> neighbours = new ArrayList<AStarPosition>(8);
		if(obsList.isEmpty()) {
			neighbours.add(new AStarPosition(p1.getX(), 	p1.getY()+1));
			neighbours.add(new AStarPosition(p1.getX()+1, p1.getY()));
			neighbours.add(new AStarPosition(p1.getX()-1, p1.getY()));
			neighbours.add(new AStarPosition(p1.getX(), 	p1.getY()-1));
			neighbours.add(new AStarPosition(p1.getX()+1, p1.getY()-1));
			neighbours.add(new AStarPosition(p1.getX()-1, p1.getY()+1));
			neighbours.add(new AStarPosition(p1.getX()+1, p1.getY()+1));
			neighbours.add(new AStarPosition(p1.getX()-1, p1.getY()-1));
		} else {
			double x = p1.getX();
			double y = p1.getY();
			AStarPosition p2 = new AStarPosition(x, y+1);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x+1, y);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x-1, y);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x, y-1);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x+1, y-1);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x-1, y+1);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x+1, y+1);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x-1, y-1);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			
		}
		return neighbours;
	}
	
	


	/**
	 * Gets the neighbours of one AStarPosition.
	 * <p>
	 * Is shape really needed here? Since we guarantee Agents are inside the shape.
	 * 
	 * @param p1
	 * @param obsList
	 * @param shape
	 * @return
	 */
	public static List<AStarPosition> getNeighbours(AStarPosition p1, List<IObstacle> obsList, int upsampleRate/*, IShape shape*/) {
		List<AStarPosition> neighbours = new ArrayList<AStarPosition>(8);
		if(obsList.isEmpty()) {
			neighbours.add(new AStarPosition(p1.getX(), 	p1.getY()+upsampleRate));
			neighbours.add(new AStarPosition(p1.getX(), 	p1.getY()-upsampleRate));
			neighbours.add(new AStarPosition(p1.getX()+upsampleRate, p1.getY()));
			neighbours.add(new AStarPosition(p1.getX()-upsampleRate, p1.getY()));
			neighbours.add(new AStarPosition(p1.getX()+upsampleRate, p1.getY()-upsampleRate));
			neighbours.add(new AStarPosition(p1.getX()-upsampleRate, p1.getY()+upsampleRate));
			neighbours.add(new AStarPosition(p1.getX()+upsampleRate, p1.getY()+upsampleRate));
			neighbours.add(new AStarPosition(p1.getX()-upsampleRate, p1.getY()-upsampleRate));
		} else {
			double x = p1.getX();
			double y = p1.getY();
			AStarPosition p2 = new AStarPosition(x, y+upsampleRate);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x+upsampleRate, y);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x-upsampleRate, y);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x, y-upsampleRate);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x+upsampleRate, y-upsampleRate);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x-upsampleRate, y+upsampleRate);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x+upsampleRate, y+upsampleRate);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			p2 = new AStarPosition(x-upsampleRate, y-upsampleRate);
			if(!AbstractObstacle.isInsideObstacleList(obsList, p2)) {
				neighbours.add(p2);
			}
			
		}
		return neighbours;
	}
	
	
	/**
	 * Internal Position class for use with A* shortest path algorithm.
	 * 
	 * @author Erik
	 *
	 */
	private static class AStarPosition extends Position {
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

	
	@SuppressWarnings("unused")
	private static double heuristic_vector_distance(AStarPosition start, AStarPosition goal /*, List<IObstacle> obsList, IShape simShape*/) {
	    return Math.sqrt(Math.pow(Math.abs(goal.getX() - start.getX()), 2) + Math.pow(Math.abs(goal.getY() - start.getY()), 2));
	}
	
    public static double heuristic_manhattan_distance(AStarPosition a, AStarPosition b /*, List<IObstacle> obsList, IShape simShape*/){
      //  return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
        return a.getDistance(b);
    }	
	
	public static void main(String[] args) {
		new ShortestPathTester();
	}
	
	////////////////////////////////////////////////////////////////////////
	// 	Jump points algorithm, WIP										  //
	// 	src: http://www.nicta.com.au/pub?doc=4856						  //
	////////////////////////////////////////////////////////////////////////
	
	
	public List<Position> getShortestPathJumpPointsSearch(Position startPos, Position endPos, List<IObstacle> obsList, double coordinateUpsampling) {
			AStarPosition start = new AStarPosition(startPos.getX(), startPos.getY());
			AStarPosition goal = new AStarPosition(endPos.getX(), endPos.getY());
			closedSet = new HashSet<AStarPosition>();
			openSet = new HashSet<AStarPosition>();
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
				for(AStarPosition neighbour : getJPSNeighbours(current, obsList, coordinateUpsampling)) {
					AStarPosition jumpPoint = jump(neighbour, current);
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

	private AStarPosition[] getJPSNeighbours(AStarPosition current,	List<IObstacle> obsList, double coordinateUpsampling) {
		double x = current.getX();
		double y = current.getY();
		LinkedList<AStarPosition> nodes = new LinkedList<AStarPosition>();
		AStarPosition parent = current.came_from;
		if (parent != null) { // determines whether to prune neighbors
			// normalize
			AStarPosition norm = normalizeDirection(x, y, parent.getX(), parent.getY());
			double dx = norm.getX()*coordinateUpsampling;
			double dy = norm.getY()*coordinateUpsampling;
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
		AStarPosition[] ns = new AStarPosition[] { new AStarPosition(x, y - coordinateUpsampling), new AStarPosition(x + coordinateUpsampling, y - coordinateUpsampling), new AStarPosition(x + coordinateUpsampling, y), new AStarPosition(x + coordinateUpsampling, y + coordinateUpsampling),
		new AStarPosition(x, y + coordinateUpsampling), new AStarPosition(x - coordinateUpsampling, y + coordinateUpsampling), new AStarPosition(x - coordinateUpsampling, y), new AStarPosition(x - coordinateUpsampling, y - coordinateUpsampling) };
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
	
	private AStarPosition jump(AStarPosition node, AStarPosition parent) {
		double x = node.getX(), y = node.getY(), px = parent.getX(), py = parent.getY();
		double dx = (x - px);
		double dy = (y - py);
		Log.e("Coordinatescaling: " + coordinateScaling);
		Log.e("dy: " + dy + " dx: " + dx);
//		Log.e("JUMP! node: " + node.toString() + " parent: " + parent.toString() + " dx: " + dx + " dy: " + dy);
		if (!walkable(node, obsList)) // check blocked
			return null;
		if (node.equals(goal)) // reached goal
			return new AStarPosition(node);
		
		// resolve forced neighbors
		AStarPosition temp = new AStarPosition(node);
		if (((int) dx & (int) dy) != 0) { // diagonal
			if ((walkable(temp.setPosition(x - dx, y + dy),obsList ) && !walkable(temp.setPosition(x - dx, y), obsList)) ||
			(walkable(temp.setPosition(x + dx, y - dy), obsList) && !walkable(temp.setPosition(x, y - dy), obsList))) {
				return new AStarPosition(node);
			}
			// recurse
			AStarPosition h = jump(node.derive(dx, 0), node);
			if (h != null)
				return new AStarPosition(node);
			AStarPosition v = jump(node.derive(0, dy), node);
			if (v != null)
				return new AStarPosition(node);
			} else if (dx == 0) { // vertical, dx = 0, dy = 1 or -1
				if ((walkable(temp.setPosition(x + coordinateScaling, y + dy), obsList) && !walkable(temp.setPosition(x + coordinateScaling, y), obsList)) ||
						(walkable(temp.setPosition(x - coordinateScaling, y + dy), obsList) && !walkable(temp.setPosition(x - coordinateScaling, y), obsList))) {
					return new AStarPosition(node);
				}
		} else { // horizontal, dx = 1 or -1, dy = 0
			if ((walkable(temp.setPosition(x + dx, y + coordinateScaling), obsList) && !walkable(temp.setPosition(x, y + coordinateScaling), obsList)) ||
					(walkable(temp.setPosition(x + dx, y - coordinateScaling), obsList) && !walkable(temp.setPosition(x, y - coordinateScaling), obsList))) {
				return new AStarPosition(node);
			}
		}
		this.jumpNode = node;
//		repaint();
		repaint();
		try {
			Thread.sleep(ITERATION_TIME);
		} catch (InterruptedException e) {
			
		}

		return jump(node.derive(dx, dy), node);
	}
	
	private boolean walkable(AStarPosition node, List<IObstacle> obsList) {
		if(node.x < 0 || node.y < 0 || node.x > 750 || node.y > 750) {
			return false;
		}
		return !AbstractObstacle.isInsideObstacleList(obsList, node);
	}

	
	
	
}
