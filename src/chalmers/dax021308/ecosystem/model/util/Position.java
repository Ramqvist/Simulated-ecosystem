package chalmers.dax021308.ecosystem.model.util;

/**
 * Position class.
 * 
 * @author Henrik
 * 
 */

public class Position {
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

	public void setPosition(Position p) {
		setPosition(p.x, p.y);
	}

	public void setPosition(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Adds a vector to current position.
	 * @param v vector to add.
	 */
	public void addVector(Vector v){
		this.x += v.getX();
		this.y += v.getY();
	}

	public boolean equals(Object o) {
		if (o instanceof Position) {
			return ((Position) o).x == x && ((Position) o).y == y;
		} else {
			return false;
		}
	}
}
