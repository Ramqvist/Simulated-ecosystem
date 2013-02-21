package chalmers.dax021308.ecosystem.model;

import java.util.List;

/**
 * Describes the general configuration of an arbitrary population.
 * 
 * @author Albin
 */
public interface IPopulation {
	
	/**
	 * Handles things that should be updated regularly. 
	 */
	public void update();
	
	/**
	 * @param agent - The IAgent for which the fitness will be calculated.
	 * @return The fitness for the provided IAgent.
	 */
	public double calculateFitness(IAgent agent);
	
	/**
	 * @return The name of the population.
	 */
	public String getName();
	
	/**
	 * @return The whole population of IAgents.
	 */
	public List<IAgent> getAgents();
	
	/**
	 * @return The predators that prey on a population. 
	 */
	public List<IPopulation> getPredators();
	
	/**
	 * @return The preys that a predator population eats.
	 */
	public List<IPopulation> getPreys();
}
