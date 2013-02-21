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
	 * 
	 * @param agent
	 * @return
	 */
	public double calculateFitness(IAgent agent);
	
	/**
	 * @return The whole population of IAgents.
	 */
	public List<IAgent> getAgents();
	
	/**
	 * @return The predators that prey on a population. 
	 */
	public List<IAgent> getPredators();
	
	/**
	 * @return The preys that a predator population eats.
	 */
	public List<IAgent> getPreys();
}
