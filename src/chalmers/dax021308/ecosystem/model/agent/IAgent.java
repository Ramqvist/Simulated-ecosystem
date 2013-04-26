package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.SurroundingsSettings;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Gender;
import chalmers.dax021308.ecosystem.model.util.Vector;
import chalmers.dax021308.ecosystem.model.util.Position;

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
	 * @param p
	 *            - The new Position.
	 */
	public void setPosition(Position p);

	// TODO (Surroundings) update javadoc
	/**
	 * Calculates the new position of the IAgent to which it will move when
	 * calling updatePosition().
	 * 
	 * @param predators
	 *            The predators who hunt this agent
	 * @param preys
	 *            The preys this agents hunts
	 * @param neutral
	 *            The 'neutral' agents, i.e. the agents of the same trophic
	 *            level
	 * @param dim
	 *            The dimension of the environment
	 * @param shape
	 *            The shape of the environment
	 * @param obstacles
	 *            The obstacles inside the environment
	 */
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral,
			SurroundingsSettings surroundings);

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

	// TODO (Surroundings) update javadoc
	/**
	 * Tries to create one or more new IAgents, with data from the two provided
	 * IAgents.
	 * 
	 * @param agent
	 *            - The IAgent which this IAgent will reproduce with.
	 * @param shape
	 *            - The shape of the environment the agent reproduces in
	 * @param gridDimension
	 *            - The dimension of the environment the agent reproduces in
	 * @return A list of IAgents if the reproduction was successful, otherwise
	 *         null.
	 */
	public List<IAgent> reproduce(IAgent agent, int populationSize,
			SurroundingsSettings surroundings);

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
	 * Checks if this agent has any food available and is desirable to hunt
	 * 
	 * @param agent
	 *            The agent looking for food
	 * @param visionRange
	 *            The visionrange of the agent looking for food
	 * @return True if the agent is desirable to hunt and should affect the
	 *         preyforce, otherwise false
	 */
	public boolean isLookingTasty(IAgent agent, double visionRange);

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

	/**
	 * Returns a number, influenced by the amount of food, which should be
	 * multiplied with preyforce to see how it influences it
	 * 
	 * @return 1 if unspecified, otherwise a positive or negative number
	 *         depending on the amount of food available in this agent
	 */
	public double impactForcesBy();
}
