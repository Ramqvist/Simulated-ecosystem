package chalmers.dax021308.ecosystem.model.environment.obstacle;

import java.awt.Color;

import chalmers.dax021308.ecosystem.model.util.Position;

/**
 * IObstacle interface.
 * 
 * @author Henrik
 * 
 */
public interface IObstacle {

	
	public Position closestBoundary(Position p);
	
	public boolean isInObstacle(Position p);
	
	public double getWidth();
	
	public double getHeight();
	
	public Position getPosition();
	
	public Color getColor();

	public String toBinaryString();
}
