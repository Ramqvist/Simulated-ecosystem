package chalmers.dax021308.ecosystem.model.util;

import java.awt.Dimension;

/**
 * 
 * @author Henrik
 * 
 */
public class SquareShape implements IShape {

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

}