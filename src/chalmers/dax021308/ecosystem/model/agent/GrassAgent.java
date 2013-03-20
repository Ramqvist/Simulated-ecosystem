package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * Simple grass, lowest part of the food chain
 * 
 * @author Henrik
 * 
 */
public class GrassAgent extends AbstractAgent {
	private final Dimension gridDimension;
	private final IShape shape;
	private static final double REPRODUCTION_RATE = 0.002;

	public GrassAgent(String name, Position pos, Color color, int width,
			int height, Vector velocity, double maxSpeed,
			Dimension gridDimension, IShape shape) {
		super(name, pos, color, width, height, velocity, maxSpeed, 0, 0);
		this.gridDimension = gridDimension;
		this.shape = shape;
		MAX_LIFE_LENGTH = 2500;
	}

	public GrassAgent(String name, Position pos, Color color, int width,
			int height, Vector velocity, double maxSpeed,
			Dimension gridDimension, int capacity, IShape shape) {
		this(name, pos, color, width, height, velocity, maxSpeed,
				gridDimension, shape);
		this.capacity = capacity;
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral, Dimension dim,
			IShape shape) {
		// Do nothing, grass shouldn't move!
	}

	@Override
	public void updatePosition() {
		// Grass shouldn't move, so just make it one iteration older
		lifeLength++;
	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize) {
		double popSize = (double) populationSize;
		double cap = (double) capacity;
		if (Math.random() < REPRODUCTION_RATE * (1.0 - popSize / cap)) {
			List<IAgent> spawn = new ArrayList<IAgent>();
			IAgent a = new GrassAgent(name,
					getSpawnPosition(), color, 5, 5,
					velocity, maxSpeed, gridDimension, capacity, shape);
			spawn.add(a);
			return spawn;
		}
		return null;

	}

	/**
	 * Finds a new position close to the current position
	 * 
	 * @return The position found
	 */
	private Position getSpawnPosition() {
		return shape.getRandomPosition(gridDimension);
	}

	/**
	 * Checks if a Position is legit, i.e it is inside the view
	 * 
	 * @param pos
	 *            the Position to check
	 * @return True if pos lies inside the view, otherwise returns false
	 */
	private boolean legitPos(Position pos) {
		return (pos.getX() > 0 && pos.getX() < gridDimension.getWidth()
				&& pos.getY() > 0 && pos.getY() < gridDimension.getHeight());
	}
}
