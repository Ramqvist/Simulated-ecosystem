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

	private Dimension gridDimension;
	private List<IObstacle> obstacles;
	private IShape worldShape;

	/**
	 * 
	 * @param d		Dimension of the world.
	 * @param s		Shape of the world.
	 * @param o		List of obstacles.
	 */
	public SurroundingsSettings(Dimension d, IShape s, List<IObstacle> o){
		this.gridDimension = d;
		this.obstacles = o;
		this.worldShape = s;
	}
	
	/**
	 * @return the gridDimension
	 */
	public Dimension getGridDimension() {
		return this.gridDimension;
	}
	/**
	 * @param gridDimension the gridDimension to set
	 */
	public void setGridDimension(Dimension gridDimension) {
		this.gridDimension = gridDimension;
	}
	/**
	 * @return the obstacles
	 */
	public List<IObstacle> getObstacles() {
		return this.obstacles;
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
		return this.worldShape;
	}
	/**
	 * @param worldShape the worldShape to set
	 */
	public void setWorldShape(IShape worldShape) {
		this.worldShape = worldShape;
	}
}
