package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.SurroundingsSettings;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Stat;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * Simple grass, lowest part of the food chain
 * 
 * @author Henrik
 */
public class GrassAgent extends AbstractAgent {

	private static final double REPRODUCTION_RATE = 0.01;
	private static final int MAX_LIFE_LENGTH = 5000;
	private static final double SPAWNING_STD = 150;

	public GrassAgent(String name, Position pos, Color color, int width,
			int height, Vector velocity, double maxSpeed, int capacity) {
		super(name, pos, color, width, height, velocity, maxSpeed, 0, 0);
		this.capacity = capacity;
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral, SurroundingsSettings surroundings) {
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
			SurroundingsSettings surroundings) {
		double popSize = (double) populationSize;
		double cap = (double) capacity;
		if (Math.random() < REPRODUCTION_RATE * (1.0 - popSize / cap)) {
			List<IAgent> spawn = new ArrayList<IAgent>();
			Position p = getSpawnPosition(surroundings);
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
	private Position getSpawnPosition(SurroundingsSettings surroundings) {
		// create a random position which lies within __ pixels of the
		// current position, and if it lies inside the shape and not inside any
		// obstacle, return it.
		Position p;
		boolean validPos;
		do {
			p = Position.positionPlusVector(position, Stat.getNormallyDistributedVector(SPAWNING_STD));
			validPos = surroundings.getWorldShape().isInside(surroundings.getGridDimension(), p);
			for (IObstacle o : surroundings.getObstacles()) {
				if (o.isInObstacle(p, OBSTACLE_SAFETY_DISTANCE)) {
					validPos = false;
				}
			}
		} while (!validPos);
		return p;
	}

}
