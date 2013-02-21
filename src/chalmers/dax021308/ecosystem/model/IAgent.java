package chalmers.dax021308.ecosystem.model;

import java.awt.Color;
import java.util.List;

import chalmers.dax021308.ecosystem.model.util.Position;


/**
 * IAgent describes an arbitrary single individual. 
 * @author Albin
 */
public interface IAgent {
	
	/**
	 * @return The position of the IAgent.
	 */
	public Position getPosition();
	
	/**
	 * Updates the position of the IAgent.
	 */
	public void updatePosition();
	
	/**
	 * @return The name of the IAgent.
	 */
	public String getName();
	
	/**
	 * @return The color of the IAgent.
	 */
	public Color getColor();
	
	/**
	 * @return The width of the IAgent.
	 */
	public int getWidth();
	
	/**
	 * @return The height of the IAgent.
	 */
	public int getHeight();
	
	/**
	 * @return The fitness of the IAgent.
	 */
	public double getFitness();
	
	/**
	 * @param fitness - The new fitness.
	 */
	public void setFitness(double fitness);
	
	/**
	 * @return The speed of the IAgent.
	 */
	public double getSpeed();
	
	/**
	 * @param speed - The new speed.
	 */
	public void setSpeed(double speed);
	
	/**
	 * Tries to create one or more new IAgents, with data from the two provided IAgents.
	 * @param agent - The IAgent which this IAgent will reproduce with.
	 * @return A list of IAgents if the reproduction was successful, otherwise null.
	 */
	public List<IAgent> reproduce(IAgent agent);
	
	/**
	 * @return The gender of the IAgent specified by some enum. Returns -1 if genderless.
	 */
	public int getGender();
}
