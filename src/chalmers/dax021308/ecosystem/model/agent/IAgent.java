package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Gender;
import chalmers.dax021308.ecosystem.model.util.Vector;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.shape.IShape;

/**
 * IAgent describes an arbitrary single individual.
 * 
 * @author Albin
 */
public interface IAgent extends Cloneable {

	/**
	 * @return The position of the IAgent.
	 */
	public Position getPosition();

	/**
	 * @param p - The new Position.
	 */
	public void setPosition(Position p);
	
	/**
	 * Calculates the new position of the IAgent to which it will move when
	 * calling updatePosition().
	 */
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral, Dimension dim,
			IShape shape, List<IObstacle> obstacles);

	/**
	 * Updates the position of the IAgent
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
	 * @param fitness
	 *            - The new fitness.
	 */
	public void setFitness(double fitness);

	/**
	 * @return The velocity of the IAgent.
	 */
	public Vector getVelocity();

	/**
	 * @param velocity
	 *            - The new velocity.
	 */
	public void setVelocity(Vector velocity);

	/**
	 * @return the life length of an agent.
	 */
	public int getLifeLength();

	/**
	 * Tries to create one or more new IAgents, with data from the two provided
	 * IAgents.
	 * 
	 * @param agent
	 *            - The IAgent which this IAgent will reproduce with.
	 * @param shape
	 *            TODO
	 * @param gridDimension
	 *            TODO
	 * @return A list of IAgents if the reproduction was successful, otherwise
	 *         null.
	 */
	public List<IAgent> reproduce(IAgent agent, int populationSize,
			List<IObstacle> obstacles, IShape shape, Dimension gridDimension);

	/**
	 * @return The gender of the IAgent specified by some enum. Returns null if
	 *         genderless.
	 */
	public Gender getGender();

	/**
	 * Clone an agent.
	 * 
	 * @return A reference to a new agent with the values of the calling one.
	 * @throws CloneNotSupportedException
	 */
	public IAgent cloneAgent() throws CloneNotSupportedException;

	/**
	 * Try to consume this agent.
	 * 
	 * @return True if consumed, otherwise false.
	 */
	public boolean tryConsumeAgent();

	public String toBinaryString();

	/**
	 * @return Trophic level of the agent.
	 */
	public int getTrophicLevel();

	/**
	 * Checks whether this agent is alive or not.
	 * 
	 * @return True if alive, otherwise false.
	 */
	public boolean isAlive();

	/**
	 * @return Max acceleration.
	 */
	public double getMaxAcceleration();

	/**
	 * @return Max Speed.
	 */
	public double getMaxSpeed();

	/**
	 * @return Vision range.
	 */
	public double getVisionRange();

	/**
	 * Tells the agent food has been found, and that it should eat
	 */
	public void eat();
}
