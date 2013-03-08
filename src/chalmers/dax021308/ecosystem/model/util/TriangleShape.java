package chalmers.dax021308.ecosystem.model.util;

import java.awt.Dimension;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;

public class TriangleShape implements IShape {
	private final String name = EcoWorld.SHAPE_TRIANGLE;

	@Override
	public String getShape() {
		return name;
	}

	@Override
	public Position getXWallLeft(Dimension dim, Position p) {
		double angle = Math.atan(dim.getWidth() / 2 / dim.getHeight());
		double xPos = p.getY() / Math.tan(angle);
		return new Position(xPos, p.getY());
	}

	@Override
	public Position getXWallRight(Dimension dim, Position p) {
		double angle = Math.atan(dim.getWidth() / 2 / dim.getHeight());
		double xPos = p.getY() / Math.tan(angle);
		return new Position(dim.getWidth() - xPos / 2, p.getY());
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
		double yPos = midAdjustedX * Math.tan(angle);
		return new Position(p.getX(), yPos);
	}

	@Override
	public Position getRandomPosition(Dimension dim) {
		// Doing some magic with Barycentric positions to calculate the random
		// position inside the triangle. Basically it goes a random distance
		// towards both corners, but makes sure we don't end outside the
		// triangle
		double r = Math.random();
		double s = Math.random();
		Vector top = new Vector(dim.getWidth() / 2, dim.getHeight());
		Vector right = new Vector(dim.getWidth(), 0);
		if (r + s >= 1) {
			r = 1 - r;
			s = 1 - s;
		}
		return new Position().addVector(top.multiply(r)).addVector(
				right.multiply(s));
	}

}
