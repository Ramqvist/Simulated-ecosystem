package chalmers.dax021308.ecosystem.model;

/**
 * IObstacle interface.
 * 
 * @author Henrik
 * 
 */
public interface IObstacle {
	
	/**
	 * Checks if the current obstacle occupies Position p
	 * 
	 * @param p
	 *            the Position to check for
	 * @return returns true if the Position p lies inside the obstacle,
	 *         otherwise false
	 */
	public boolean insideObstacle(Position p);
	
	/**
	 * 
	 * @return returns the type of the Obstacle 
	 */
	public String getObstacleType();
}
