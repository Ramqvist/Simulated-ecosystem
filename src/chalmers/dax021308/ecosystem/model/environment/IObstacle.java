package chalmers.dax021308.ecosystem.model.environment;

import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

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
}
