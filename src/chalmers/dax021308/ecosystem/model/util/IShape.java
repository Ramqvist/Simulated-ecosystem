package chalmers.dax021308.ecosystem.model.util;

import java.awt.Dimension;

/**
 * 
 * @author Henrik
 * 
 */
public interface IShape {
	public String getShape();

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
}