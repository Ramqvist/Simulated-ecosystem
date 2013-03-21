package chalmers.dax021308.ecosystem.model.environment;

import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld.OnFinishListener;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.DoublePair;
import chalmers.dax021308.ecosystem.model.util.Position;

/**
 * 
 * @author Henrik Class for handling circular environments
 * 
 * TODO: Remove class?
 */
public class CircleEnvironment extends EnvironmentScheduler {
	// Class probably uselss
	// TODO: Figure out a _good_ way of handling circles, not like this
	private DoublePair<Integer>[][] field;

	/**
	 * 
	 * @param populations
	 *            The populations to be used in the environment
	 * @param obstacles
	 *            The obstacles to be used in the environment
	 * @param listener
	 *            The listener to this instance
	 * @param height
	 *            The height of the environment
	 * @param width
	 *            The width of the environment
	 */
	public CircleEnvironment(List<IPopulation> populations,
			List<IObstacle> obstacles, OnFinishListener listener, int height,
			int width, int numThreads) {
		super(populations, obstacles, listener, height, width, numThreads);
	}

	private void createCircle(int height, int width) {
		field = new DoublePair[height][width];
		for (int i = 0; i < field.length; i++) {
			for (int j = 0; j < field[i].length; j++) {
				// Creating a square, will be a circle once everything else
				// works!
				// TODO: create circle instead of square
				field[i][j] = new DoublePair<Integer>(0, width, 0, height);
			}
		}

	}

	private Position createPosition() {
		// Find a random Y position, look at the width at that specific
		// row and find a random spot in that row
		double yPos = Math.random() * field.length;
		int rowNr = (int) yPos;
		int middle = field[rowNr].length / 2;
		double xPos = Math.random()
				* (field[rowNr][middle].getRight() - field[rowNr][middle]
						.getLeft()) + field[rowNr][middle].getLeft();

		return new Position(xPos, yPos);
	}

}