package chalmers.dax021308.ecosystem.model.environment;

import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class EllipticalObstacle extends AbstractObstacle{
	
	private double width;
	private double height;
	
	public EllipticalObstacle(double width, double height, Position position){
		this.position = position;
		this.width = width;
		this.height = height;
	}
	@Override
	public Position closestBoundary(Position p) {
		
		
		return p;
	}

}
