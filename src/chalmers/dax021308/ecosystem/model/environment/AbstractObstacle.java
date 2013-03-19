package chalmers.dax021308.ecosystem.model.environment;

import chalmers.dax021308.ecosystem.model.util.Position;

public abstract class AbstractObstacle implements IObstacle {
	
	protected Position position;
	protected double width;
	protected double height;
	
	@Override
	public double getWidth() {
		return width;
	}
	@Override
	public double getHeight() {
		return height;
	}
	@Override
	public Position getPosition() {
		return new Position(position);
	}

}
