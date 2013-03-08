package chalmers.dax021308.ecosystem.model.util;

import java.awt.Dimension;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;

/**
 * 
 * @author Henrik
 * 
 */
public class CircleShape implements IShape {
	private final String name = EcoWorld.SHAPE_CIRCLE;

	@Override
	public Position getXWallLeft(Dimension dim, Position p) {
		// Using trigonometry to get the X position where the current Y position
		// hits the side, only "magic" is converting the positions so they start
		// in the middle of the circle and then back.
		double radius = dim.getHeight() / 2;
		double angle = Math.asin((p.getY() - radius) / radius);
		double xPos = radius * Math.cos(angle);
		return new Position(radius - xPos, p.getY());
	}

	@Override
	public Position getXWallRight(Dimension dim, Position p) {
		// See getXWallLeft
		double radius = dim.getHeight() / 2;
		double angle = Math.asin((p.getY() - radius) / radius);
		double xPos = radius * Math.cos(angle);
		return new Position(radius + xPos, p.getY());
	}

	@Override
	public Position getYWallBottom(Dimension dim, Position p) {
		// See getXWallLeft
		double radius = dim.getHeight() / 2;
		double angle = Math.acos((p.getX() - radius) / radius);
		double yPos = radius * Math.sin(angle);
		return new Position(p.getX(), radius - yPos);
	}

	@Override
	public Position getYWallTop(Dimension dim, Position p) {
		// See getXWallLeft
		double radius = dim.getHeight() / 2;
		double angle = Math.acos((p.getX() - radius) / radius);
		double yPos = radius * Math.sin(angle);
		return new Position(p.getX(), radius + yPos);
	}

	@Override
	public Position getRandomPosition(Dimension dim) {
		// Create a random position by starting from the mid and then adding a
		// vector which length is specified by a random x and y position in a
		// random direction
		Position pos = new Position(dim.width / 2, dim.height / 2);
		Double angle = Math.random() * Math.PI * 2;
		double xPos = Math.cos(angle) * dim.width / 2 * Math.random();
		double yPos = Math.sin(angle) * dim.height / 2 * Math.random();
		pos.addVector(new Vector(xPos, yPos));
		return pos;
	}

	@Override
	public String getShape() {
		return name;
	}
}