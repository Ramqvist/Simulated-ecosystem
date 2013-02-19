package chalmers.dax021308.ecosystem.model;

import java.awt.Color;


/**
 * IAgent describes an arbitrary single individual. 
 * 
 * @author Albin Bramstång
 */
public interface IAgent {
	
	public Position getPosition();
	public void setPosition(Position position);
	public String getName();
	public Color getColor();
	public int getWidth();
	public int getHeight();
	public double getFitness();
	public void setFitness(double fitness);
	public double getSpeed();
	public void setSpeed(double speed);
	
//	Mutation state
//	Probability to reproduce
}
