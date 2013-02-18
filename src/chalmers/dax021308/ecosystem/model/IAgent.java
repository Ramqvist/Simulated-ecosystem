package chalmers.dax021308.ecosystem.model;

import java.awt.Color;


/**
 * IAgent describes an arbitrary single individual. 
 * 
 * @author Albin Bramstång
 */
public interface IAgent {
	
//	public Position getPosition();
//	public void setPosition(Position);
	public String getName();
	public Color getColor();
	public int getWidth();
	public int getHeight();
//	Fitness
//	Mutation state
//	Probability to reproduce
}
