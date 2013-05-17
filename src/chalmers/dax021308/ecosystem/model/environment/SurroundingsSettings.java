/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.environment;

import java.awt.Dimension;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.util.shape.IShape;

/**
 * @author Loanne Berggren
 * Dimension, Obstacles, shape of the world
 */
public class SurroundingsSettings {

	private static Dimension gridDimension;
	private List<IObstacle> obstacles;
	private static IShape worldShape;
	private double OBSTACLE_SAFETY_DISTANCE;

	/**
	 * 
	 * @param d		Dimension of the world.
	 * @param s		Shape of the world.
	 * @param o		List of obstacles.
	 */
	public SurroundingsSettings(SurroundingsSettings surr, double obstacleSafetyDistance){
		this.obstacles = surr.obstacles;
		OBSTACLE_SAFETY_DISTANCE = obstacleSafetyDistance;
	}
	
	public SurroundingsSettings(){}
	
	/**
	 * @return the gridDimension
	 */
	public Dimension getGridDimension() {
		return SurroundingsSettings.gridDimension;
	}
	/**
	 * @param gridDimension the gridDimension to set
	 */
	public void setGridDimension(Dimension gridDimension) {
		SurroundingsSettings.gridDimension = gridDimension;
	}
	/**
	 * @return the obstacles
	 */
	public List<IObstacle> getObstacles() {
		return obstacles;
	}
	/**
	 * @param obstacles the obstacles to set
	 */
	public void setObstacles(List<IObstacle> obstacles) {
		this.obstacles = obstacles;
	}
	/**
	 * @return the worldShape
	 */
	public IShape getWorldShape() {
		return SurroundingsSettings.worldShape;
	}
	/**
	 * @param worldShape the worldShape to set
	 */
	public void setWorldShape(IShape worldShape) {
		SurroundingsSettings.worldShape = worldShape;
	}

	public void setObstacleSafetyDistance(double d) {
		OBSTACLE_SAFETY_DISTANCE = d;
	}

	public double getObstacleSafetyDistance() {
		return OBSTACLE_SAFETY_DISTANCE;
	}
}
