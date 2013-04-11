package chalmers.dax021308.ecosystem.model.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;


import chalmers.dax021308.ecosystem.model.environment.obstacle.AbstractObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;


/**
 * Position class.
 * 
 * @author Henrik, for shortest path Erik Ramqvist
 * 
 */

public class Position {
	private static final double ASTAR_HASHSET_THRESHOLD = 45;
	private static final int HEURISTIC_UPSAMPLE = 10;
	private double x;
	private double y;

	public Position() {
		this(0, 0);
	}

	public Position(double x, double y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Clone a position. Returning a new Position with the value of this one.
	 * @return
	 */
	public Position(Position p) {
		this.x = p.x;
		this.y = p.y;
	}

	public double getDistance(Position p) {
		double dX = p.x - x;
		double dY = p.y - y;
		return Math.sqrt(dX * dX + dY * dY);
	}

	public double getY() {
		return y;
	}

	public double getX() {
		return x;
	}
	

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public void setPosition(Position p) {
		setPosition(p.x, p.y);
	}

	public Position setPosition(double x, double y) {
		this.x = x;
		this.y = y;
		return Position.this;
	}
	
	/**
	 * Adds a vector to current position.
	 * @param v vector to add.
	 */
	public Position addVector(Vector v){
		this.x += v.getX();
		this.y += v.getY();
		return this;
	}

	/**
	 * Adds a vector to a position and returns the new position.
	 * @param p the position
	 * @param v the vector
	 * @return a position p+v.
	 */
	public static Position positionPlusVector(Position p, Vector v) {
		return new Position(p.x+v.getX(),p.y+v.getY());
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(x);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(y);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Position other = (Position) obj;
		if (Double.doubleToLongBits(x) != Double.doubleToLongBits(other.x))
			return false;
		if (Double.doubleToLongBits(y) != Double.doubleToLongBits(other.y))
			return false;
		return true;
	}
	
	/**
	 * Optimized version of A* with dynamic datastructure based on distance to target.
	 * Uses
	 * @param startPos
	 * @param endPos
	 * @return
	 */
	public static List<Position> getShortestPath(Position startPos, Position endPos , List<IObstacle> obsList/*, IShape simShape*/) {
		double distance = startPos.getDistance(endPos);
		if(distance > ASTAR_HASHSET_THRESHOLD) {
			return getShortestPathHashSet(startPos, endPos);
		} else {
			return getShortestPathPriorityQueue(startPos, endPos, obsList);
		}
	}
	

	public static List<Position> getShortestPathPriorityQueueLinkedList(Position startPos, Position endPos) {
		AStarPosition start = new AStarPosition(startPos.getX(), startPos.getY());
		AStarPosition goal = new AStarPosition(endPos.getX(), endPos.getY());
		start.g_score = 0;
		start.f_score = heuristic_manhattan_distance(start, goal)*2;
		List<AStarPosition> closedSet = new ArrayList<AStarPosition>();
		PriorityQueue<AStarPosition> openSet = new PriorityQueue<AStarPosition>(100, new AStarPositionComparator());
		openSet.add(start);
		AStarPosition current = start;//the node in openset having the lowest f_score[] value
		while(!openSet.isEmpty()) {
			current = openSet.poll();
			closedSet.add(current);
			if(current.equals(goal)) {
				return reconstructPath(current);
			}
			for(AStarPosition neighbour : getNeighbours(current)) {
				double tentative_g_score = current.g_score + current.getDistance(neighbour);
				if(closedSet.contains(neighbour)) {
					if(tentative_g_score >= neighbour.g_score) {
						continue;
					}
				}
				if(!openSet.contains(neighbour) || tentative_g_score < neighbour.g_score) {
					neighbour.came_from = current;
					neighbour.g_score = tentative_g_score;
					neighbour.f_score = neighbour.g_score + heuristic_manhattan_distance(goal, neighbour)*2;
					if(!openSet.contains(neighbour)) {
						openSet.add(neighbour);
					}
				}
			}
		}	
		//Failure to find path.
		return Collections.emptyList();
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
	public static List<Position> getShortestPathPriorityQueue(Position startPos, Position endPos, List<IObstacle> obsList/*, IShape simShape*/) {
		AStarPosition start = new AStarPosition(startPos.getX(), startPos.getY());
		AStarPosition goal = new AStarPosition(endPos.getX(), endPos.getY());
		start.g_score = 0;
		start.f_score = heuristic_manhattan_distance(start, goal)*HEURISTIC_UPSAMPLE;
		Set<AStarPosition> closedSet = new HashSet<AStarPosition>();
		PriorityQueue<AStarPosition> openSet = new PriorityQueue<AStarPosition>(100, new AStarPositionComparator());
		openSet.add(start);
		AStarPosition current = start;//the node in openset having the lowest f_score[] value
		while(!openSet.isEmpty()) {
//			Log.v("openset" + openSet);
//			Log.v("closedSet" + closedSet);
////			Log.v("g_score" + g_score);
////			Log.v("f_score" + f_score);
//			Log.v("current" + current);
//			Log.v("--------");
			current = openSet.poll();
			closedSet.add(current);
			if(current.equals(goal)) {
				return reconstructPath(current);
			}
			for(AStarPosition neighbour : getNeighbours(current, obsList)) {
				double tentative_g_score = current.g_score + current.getDistance(neighbour);
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
	public static List<Position> getShortestPathHashSet(Position startPos,
			Position endPos /* , List<IObstacle> obsList, IShape simShape */) {
		AStarPosition start = new AStarPosition(startPos.getX(), startPos.getY());
		AStarPosition goal = new AStarPosition(endPos.getX(), endPos.getY());
		Set<AStarPosition> closedSet = new HashSet<AStarPosition>();
		Set<AStarPosition> openSet = new HashSet<AStarPosition>();
		openSet.add(start);
		start.g_score = 0.0;
		start.f_score = heuristic_manhattan_distance(start, goal);
		AStarPosition current = start;// the node in openset having the lowest
									// f_score[] value
		double lowScore = Integer.MAX_VALUE;
		while (!openSet.isEmpty()) {
			// Get the AStarPosition with the lowest estimated distance to target.
			for (AStarPosition n : openSet) {
				double score = n.f_score + heuristic_manhattan_distance(n, goal);
				if (score < lowScore) {
					current = n;
					lowScore = score;
				}
			}
			// Log.v("openset" + openSet);
			// Log.v("closedSet" + closedSet);
			// Log.v("g_score" + g_score);
			// Log.v("f_score" + f_score);
			// Log.v("current" + current);
			// Log.v("--------");
			if (current.equals(goal)) {
				return reconstructPath(current);
			}
			openSet.remove(current);
			closedSet.add(current);
			for (AStarPosition neighbour : getNeighbours(current)) {
				double tentative_g_score = current.g_score + current.getDistance(neighbour);
				if (closedSet.contains(neighbour)) {
					if (tentative_g_score >= neighbour.g_score) {
						continue;
					}
				}
				if (!openSet.contains(neighbour) || tentative_g_score < neighbour.g_score) {
					neighbour.came_from = current;
					neighbour.g_score = tentative_g_score;
					neighbour.f_score = neighbour.g_score + heuristic_manhattan_distance(goal, neighbour);
					if (!openSet.contains(neighbour)) {
						openSet.add(neighbour);
					}
				}
			}
		}
		// Failure to find path.
		return Collections.emptyList();
	}

	
	@SuppressWarnings("unused")
	private static double heuristic_vector_distance(AStarPosition start, AStarPosition goal /*, List<IObstacle> obsList, IShape simShape*/) {
	    return Math.sqrt(Math.pow(Math.abs(goal.getX() - start.getX()), 2) + Math.pow(Math.abs(goal.getY() - start.getY()), 2));
	}
	
    public static double heuristic_manhattan_distance(AStarPosition a, AStarPosition b /*, List<IObstacle> obsList, IShape simShape*/){
        return Math.abs(a.getX() - b.getX()) + Math.abs(a.getY() - b.getY());
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
	

	/**
	 * Gets the neighbours of one AStarPosition. 
	 * <p>
	 * 8 direction around the given position.
	 * <p>
	 * Ignores obstacles and shape.
	 * 
	 * @param p1
	 * @param obsList
	 * @param shape
	 * @return
	 */
	public static List<AStarPosition> getNeighbours(AStarPosition p /*, List<IObstacle> obsList, IShape shape*/) {
		List<AStarPosition> neighbours = new ArrayList<AStarPosition>(8);
		//TODO: Check here if positions is not inside obstacle. And inside simulation dimension.
		neighbours.add(new AStarPosition(p.getX(), 	p.getY()+1));
		neighbours.add(new AStarPosition(p.getX()+1, p.getY()));
		neighbours.add(new AStarPosition(p.getX()-1, p.getY()));
		neighbours.add(new AStarPosition(p.getX(), 	p.getY()-1));
		neighbours.add(new AStarPosition(p.getX()+1, p.getY()-1));
		neighbours.add(new AStarPosition(p.getX()-1, p.getY()+1));
		neighbours.add(new AStarPosition(p.getX()+1, p.getY()+1));
		neighbours.add(new AStarPosition(p.getX()-1, p.getY()-1));
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
	
	
	@Override
	public String toString(){
		return "("+this.x+","+this.y+")";
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
	 * Internal Position class for use with A* shortest path algorithm.
	 * 
	 * @author Erik
	 *
	 */
	private static class AStarPosition extends Position {
		double g_score;
		double f_score;
		AStarPosition came_from;
		public double getGF() {
			return g_score+f_score;
		}
		public AStarPosition(double x, double y) {
			super(x,y);
		}
		
		@Override
		public String toString() {
			return "G: " + g_score + " F: " + f_score + " " + super.toString();
		}
	}

}
