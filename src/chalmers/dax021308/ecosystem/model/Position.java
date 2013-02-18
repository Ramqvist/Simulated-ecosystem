package chalmers.dax021308.ecosystem.model;

import java.awt.Point;

/**
 * Position class.
 * 
 * @author Henrik
 * 
 */

public class Position {
	private int x;
	private int y;

	public Position() {
		this(0, 0);
	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public double getDistance(Position p) {
		double dX = p.x - x;
		double dY = p.y - y;
		return Math.sqrt(dX * dX + dY * dY);
	}

	public int getY() {
		return y;
	}

	public int getX() {
		return x;
	}

	public void setPosition(Position p) {
		setPosition(p.x, p.y);
	}

	public void setPosition(int x, int y) {
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
