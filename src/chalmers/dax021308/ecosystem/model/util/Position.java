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

	public boolean equals(Object o) {
		if (o instanceof Position) {
			return ((Position) o).x == x && ((Position) o).y == y;
		} else {
			return false;
		}
	}
}
