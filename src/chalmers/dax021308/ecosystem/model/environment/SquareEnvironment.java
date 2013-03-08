package chalmers.dax021308.ecosystem.model.environment;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld.OnFinishListener;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.model.util.Position;

/**
 * SquareEnvironment Class. Represents a environment in the shape of a square.
 * <p>
 * Contains workers that execute the updates of the Population in parallel.
 * <p>
 * The workers is represented as a seperate thread in a {@link ExecutorService}.
 * 
 * @author Henrik, for concurrency: Erik Ramqvist
 * 
 */
public class SquareEnvironment extends AbstractEnvironment {

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
	public SquareEnvironment(List<IPopulation> populations,
			List<IObstacle> obstacles, OnFinishListener listener, int height,
			int width) {
		super(populations, obstacles, listener, height, width);
	}
}