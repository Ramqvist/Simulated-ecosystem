package chalmers.dax021308.ecosystem.model.util;

import java.awt.Dimension;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;

public class TriangleShape implements IShape {
	private final String name = SimulationSettings.SHAPE_TRIANGLE;

	@Override
	public String getShape() {
		return name;
	}

	@Override
	public Position getXWallLeft(Dimension dim, Position p) {
		double angle = Math.atan(dim.getWidth() / 2 / dim.getHeight());
		double xPos = dim.getWidth()/2.0-(dim.getHeight()-p.getY())* Math.tan(angle);
		return new Position(xPos, p.getY());
	}

	@Override
	public Position getXWallRight(Dimension dim, Position p) {
		double angle = Math.atan(dim.getWidth() / 2 / dim.getHeight());
		double xPos = dim.getWidth()/2.0+(dim.getHeight()-p.getY())* Math.tan(angle);
		return new Position(xPos, p.getY());
	}

	@Override
	public Position getYWallBottom(Dimension dim, Position p) {
		return new Position(p.getX(), 0);
	}

	@Override
	public Position getYWallTop(Dimension dim, Position p) {
		double midAdjustedX = p.getX();
		if (p.getX() > dim.getWidth() / 2)
			midAdjustedX = dim.getWidth() - p.getX();
		double angle = Math.atan(dim.getWidth() / 2 / dim.getHeight());
		double yPos = midAdjustedX / Math.tan(angle);
		return new Position(p.getX(), yPos);
	}

	@Override
	public Position getRandomPosition(Dimension dim) {
		// Doing some magic with Barycentric positions to calculate the random
		// position inside the triangle. Basically it goes a random distance
		// towards both corners, but makes sure we don't end up outside the
		// triangle
		double topDirection = Math.random();
		double rightDirection = Math.random();
		Vector top = new Vector(dim.getWidth() / 2, dim.getHeight());
		Vector right = new Vector(dim.getWidth(), 0);
		if (topDirection + rightDirection >= 1) {
			topDirection = 1 - topDirection;
			rightDirection = 1 - rightDirection;
		}
		return new Position().addVector(top.multiply(topDirection)).addVector(
				right.multiply(rightDirection));
	}
	
	@Override
	public boolean isInside(Dimension dim, Position p) {
		// if the position is not above or below, or outside of the left or
		// right 'wall' of the triangle, then it lies inside the shape
		return p.getY() > 0 && p.getY() < dim.getHeight()
				&& p.getX() > getXWallLeft(dim, p).getX()
				&& p.getX() < getXWallRight(dim, p).getX();
	}

}
