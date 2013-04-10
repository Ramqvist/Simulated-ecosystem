package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * Simple grass, lowest part of the food chain
 * 
 * @author Henrik
 */
public class GrassAgent extends AbstractAgent {

	private static final double REPRODUCTION_RATE = 0.01;
	private static final int MAX_LIFE_LENGTH = 5000;

	public GrassAgent(String name, Position pos, Color color, int width,
			int height, Vector velocity, double maxSpeed, int capacity) {
		super(name, pos, color, width, height, velocity, maxSpeed, 0, 0);
		this.capacity = capacity;
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral, Dimension dim,
			IShape shape, List<IObstacle> obstacles) {
		// Do nothing, grass shouldn't move!
	}

	@Override
	public void updatePosition() {
		lifeLength++;
		if(lifeLength > MAX_LIFE_LENGTH)
			isAlive = false;
	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize,
			List<IObstacle> obstacles, IShape shape, Dimension gridDimension) {
		double popSize = (double) populationSize;
		double cap = (double) capacity;
		if (Math.random() < REPRODUCTION_RATE * (1.0 - popSize / cap)) {
			List<IAgent> spawn = new ArrayList<IAgent>();
			Position p = getSpawnPosition(gridDimension, shape, obstacles);
			IAgent a = new GrassAgent(name, p, color, 5, 5, velocity, maxSpeed,
					capacity);
			spawn.add(a);
			return spawn;
		}
		return null;

	}

	/**
	 * Finds a new position close to the current position, which lies inside the
	 * shape and outside all the obstacles
	 * 
	 * @return The position found
	 */
	private Position getSpawnPosition(Dimension gridDimension, IShape shape,
			List<IObstacle> obstacles) {
		// create a random position which lies within __ pixels of the
		// current position, and if it lies inside the shape and not inside any
		// obstacle, return it.
		Position p;
		Vector v = new Vector(Math.random() * 400, 0);
		boolean validPos;
		do {
			v.rotate(Math.random() * Math.PI * 2);
			p = Position.positionPlusVector(position, v);
			validPos = shape.isInside(gridDimension, p);
			for (IObstacle o : obstacles) {
				if (o.isInObstacle(p)) {
					validPos = false;
				}
			}
		} while (!validPos);
		return p;
	}
}
