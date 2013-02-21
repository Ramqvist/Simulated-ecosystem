package chalmers.dax021308.ecosystem.model;

import java.util.List;

import chalmers.dax021308.ecosystem.model.EcoWorld.OnFinishListener;

/**
 * SquareEnvironment Class. Represents a environment in the shape of a square.
 * 
 * @author Henrik
 * 
 */
public class SquareEnvironment implements IEnvironment {
	// Should maybe use an abstract class Environment where suitable instance
	// variables can be declared
	private List<IPopulation> populations;
	private List<IObstacle> obstacles;
	private OnFinishListener mListener;
	private int height;
	private int width;

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
		this.populations = populations;
		this.obstacles = obstacles;
		this.mListener = listener;
		this.height = height;
		this.width = width;
	}

	@Override
	/**
	 * Run method, called for each tick of the timer
	 * Updates each population and then informs EcoWorld once it's finished
	 */
	public void run() {
		for (int i = 0; i < populations.size(); i++)
			populations.get(i).update();

		// Callback function called to inform EcoWorld that the current update
		// is run
		mListener.onFinish(populations, obstacles);
	}

	@Override
	public boolean isFree(Position p) {

		// for each population, check if any agent is currently occupying
		// position p
		/*
		 * Needs some kind of lookup method in IPopulation to function easily.
		 * Or some kind of getPosition for each agent or something similar for
		 * (int i = 0; i < populations.size(); i++) if
		 * (populations.get(i).occupies(p)) return false;
		 */

		// for each obstacle, check if the position p lies inside any obstacle
		for (int i = 0; i < obstacles.size(); i++)
			if (obstacles.get(i).insideObstacle(p))
				return false;

		// If there is neither a population nor an obstacle at position p, then
		// it is free
		return true;
	}
}
