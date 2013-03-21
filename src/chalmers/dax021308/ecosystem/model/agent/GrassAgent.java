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
	private final IShape shape;
	private static final double REPRODUCTION_RATE = 0.003;

	public GrassAgent(String name, Position pos, Color color, int width,
			int height, Vector velocity, double maxSpeed, int capacity,
			IShape shape) {
		super(name, pos, color, width, height, velocity, maxSpeed, 0, 0);
		this.capacity = capacity;
		this.shape = shape;
		MAX_LIFE_LENGTH = 2500;
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
	public List<IAgent> reproduce(IAgent agent, int populationSize,
			Dimension gridDimension) {
		double popSize = (double) populationSize;
		double cap = (double) capacity;
		if (Math.random() < REPRODUCTION_RATE * (1.0 - popSize / cap)) {
			List<IAgent> spawn = new ArrayList<IAgent>();
			IAgent a = new GrassAgent(name, getSpawnPosition(gridDimension),
					color, 5, 5, velocity, maxSpeed, capacity, shape);
			spawn.add(a);
			return spawn;
		}
		return null;

	}

	/**
	 * Finds a new position close to the current position
	 * 
	 * @param gridDimension
	 *            TODO
	 * 
	 * @return The position found
	 */
	private Position getSpawnPosition(Dimension gridDimension) {
		// create a random position which lies within 50 pixels of the
		// current position, and if it lies inside the shape, return it.
		Position p;
		Vector v = new Vector(Math.random() * 50, 0);
		do {
			v.rotate(Math.random() * Math.PI * 2);
			p = Position.positionPlusVector(position, v);
		} while (!shape.isInside(gridDimension, p));
		return p;
	}
}
