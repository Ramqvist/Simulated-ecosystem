package chalmers.dax021308.ecosystem.model.util;

import java.awt.Dimension;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;


/**
 * Position class.
 * 
 * @author Henrik, for shortest path Erik Ramqvist
 * 
 */

public class Position {
	protected double x;
	protected double y;

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
	
	@Override
	public String toString() {
		return "(" + this.x + "," + this.y + ")";
	}

	/**
	 * Gets the shortest path between start and end.
	 * <p> 
	 * Uses the algorithm Jump Point Search by Daniel Harabor 2011
	 * <p>
	 * Source: <a href="http://www.nicta.com.au/pub?doc=4856"> link </a>
	 * <p>
	 * Returns null if the end position can't be reached.
	 * 
	 * @param startPos
	 * @param endPos
	 * @return
	 */
	public static List<Position> getShortestPath(Position startPos, Position endPos , List<IObstacle> obsList, IShape simShape, Dimension simDimension) {
		JPSPathfinder jps = new JPSPathfinder(obsList, simShape, simDimension);
		return jps.getShortestPath(startPos, endPos);
	}

}
