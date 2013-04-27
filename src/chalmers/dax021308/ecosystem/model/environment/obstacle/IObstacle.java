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
	
	public boolean isInObstacle(Position p, double safetyDistance);
	
	public boolean isCloseTo(Position p, double interactionRange);
	
	public double getWidth();
	
	public double getHeight();
	
	public void setHeight(double height);
	
	public void setWidth(double width);
	
	public Position getPosition();
	
	public void moveObstacle(double x, double y);

	public void setColor(Color c);
	
	public void setPosition(Position p);
	
	public Color getColor();
	
	public double getAngle();
	
	public void setAngle(double angle);

	public String toBinaryString();

	public boolean isInsidePath(Position start, Position end);
	
	public IObstacle scale(double x, double y);
	
	public Position toObstacleCoordinates(Position p);
	
	public Position fromObstacleCoordinates(Position p);
	
}
