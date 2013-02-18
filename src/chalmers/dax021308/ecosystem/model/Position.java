package chalmers.dax021308.ecosystem.model;

import java.awt.Point;

/**
 * Position class.
 * 
 * @author Henrik
 * 
 */
@SuppressWarnings("serial")
public class Position extends Point {

	public Position() {
		this(0, 0);
	}

	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public double getDistance(Position p) {
		double dX = p.getX() - x;
		double dY = p.getY() - y;
		return Math.sqrt(dX * dX + dY * dY);
	}

	public boolean equals(Object o) {
		if (o instanceof Position) {
			return ((Position) o).getX() == x && ((Position) o).getY() == y;
		} else {
			return false;
		}
	}
}
