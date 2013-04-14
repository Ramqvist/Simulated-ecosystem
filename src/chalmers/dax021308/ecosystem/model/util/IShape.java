package chalmers.dax021308.ecosystem.model.util;

import java.awt.Dimension;

/**
 * 
 * @author Henrik
 * 
 */
public interface IShape {

	public Position getXWallLeft(Dimension dim, Position p);

	public Position getXWallRight(Dimension dim, Position p);

	public Position getYWallBottom(Dimension dim, Position p);

	public Position getYWallTop(Dimension dim, Position p);

	/**
	 * Creates a random position inside the shape
	 * 
	 * @param dim
	 *            The size of the shape
	 * @return returns the random position created
	 */
	public Position getRandomPosition(Dimension dim);

	/**
	 * Gets the 'name' of the shape, the String name it is defined as in the
	 * EcoWorld class
	 * 
	 * @return The String name for the shape
	 */
	public String getShape();

	/**
	 * Checks whether or not the position lies inside the shape of specified
	 * dimension
	 * 
	 * @param dim
	 *            The dimension to be specified
	 * @param p
	 *            The position to be checked
	 * @return returns True if the position lies inside the shape of specified
	 *         dimension, otherwise returns False
	 */
	public boolean isInside(Dimension dim, Position p);
	
}