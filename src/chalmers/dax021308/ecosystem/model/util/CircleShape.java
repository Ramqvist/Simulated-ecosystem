package chalmers.dax021308.ecosystem.model.util;

import java.awt.Dimension;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;

/**
 * 
 * @author Henrik
 * 
 */
public class CircleShape implements IShape {
	private final String name = SimulationSettings.SHAPE_CIRCLE;

	@Override
	public Position getXWallLeft(Dimension dim, Position p) {
		// Using trigonometry to get the X position where the current Y position
		// hits the side, only "magic" is converting the positions so they
		// originate in the middle of the circle, and then back again.
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
		// Create a random position, and make sure it lies inside the circle.
		// Doesn't loop too many times, since only pi/4 of the square area is
		// outside the circle. Should only be used during creation of
		// agents and thus doesn't really impact the runtime speed.
		Position pos;
		do {
			pos = new Position(dim.getWidth() * Math.random(), dim.getHeight()
					* Math.random());
		} while ((Double.compare(pos.getX(), getXWallLeft(dim, pos).getX())
				+ Double.compare(getXWallRight(dim, pos).getX(), pos.getX())
				+ Double.compare(pos.getY(), getYWallBottom(dim, pos).getY()) + Double
					.compare(getYWallTop(dim, pos).getY(), pos.getY())) != 4);

		return pos;
	}

	@Override
	public String getShape() {
		return name;
	}

	@Override
	public boolean isInside(Dimension dim, Position p) {
		// If the distance between the position and the middle of the circle is
		// smaller than the radius, then it's inside the shape.
		double distance = p.getDistance(new Position(dim.getWidth() / 2, dim
				.getHeight() / 2));
		return distance < dim.getWidth() / 2;
	}
}