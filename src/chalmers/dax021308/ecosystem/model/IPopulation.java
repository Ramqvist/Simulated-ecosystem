package chalmers.dax021308.ecosystem.model;

/**
 * Describes the general configuration of an arbitrary population.
 * 
 * @author Albin Bramstång
 */
public interface IPopulation {
	
	/**
	 * Handles everything that should change on each tick.
	 */
	public void update();
//	Maturity
//	Fitness
//	List of agents
//	List of predators that prey on this population
//	List of preys this population preys on
}
