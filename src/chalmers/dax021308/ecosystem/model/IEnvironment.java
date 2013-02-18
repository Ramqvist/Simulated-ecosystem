package chalmers.dax021308.ecosystem.model;

/**
 * Environment Interface.
 * 
 * @author Henrik
 * 
 */
public interface IEnvironment extends Runnable {

	/**
	 * Looks through all populations and obstacles to see if there is any item
	 * in either list which occupies position p
	 * 
	 * @param p
	 *            The position to check for
	 * @return true if the position is free, otherwise false
	 */
	public boolean isFree(Position p);
}
