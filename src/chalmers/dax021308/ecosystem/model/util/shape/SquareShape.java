package chalmers.dax021308.ecosystem.model.util.shape;

import java.awt.Dimension;

import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.util.Position;

/**
 * 
 * @author Henrik
 * 
 */
public class SquareShape implements IShape {
	private final String name = SimulationSettings.SHAPE_SQUARE;

	@Override
	public Position getXWallLeft(Dimension dim, Position p) {
		return new Position(0, p.getY());
	}

	@Override
	public Position getXWallRight(Dimension dim, Position p) {
		return new Position(dim.getWidth(), p.getY());
	}

	@Override
	public Position getYWallBottom(Dimension dim, Position p) {
		return new Position(p.getX(), 0);
	}

	@Override
	public Position getYWallTop(Dimension dim, Position p) {
		return new Position(p.getX(), dim.getHeight());
	}

	@Override
	public Position getRandomPosition(Dimension dim) {
		return new Position(dim.getWidth() * Math.random(), dim.getHeight()
				* Math.random());
	}

	@Override
	public String getShape() {
		return name;
	}
	
	@Override
	public boolean isInside(Dimension dim, Position p) {
		// If the position lies inside the edges of the square, then it's inside
		// the shape
		return p.getX() > 0 && p.getX() < dim.getWidth() && p.getY() > 0
				&& p.getY() < dim.getHeight();
	}
	
	@Override
	public String toString() {
		return name;
	}
}