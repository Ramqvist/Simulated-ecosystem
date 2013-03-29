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
public class GrassFieldAgent extends AbstractAgent {
	private static final double REPRODUCTION_RATE = 0.003;
	int delay = 0;
	private static final int MAX_ENERGY = 500;

	public GrassFieldAgent(String name, Position pos, Color color, int width,
			int height, Vector velocity, double maxSpeed, int capacity) {
		super(name, pos, color, width, height, velocity, maxSpeed, 0, 0);
		this.capacity = capacity;
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
		
		if(energy++ > MAX_ENERGY)
			energy = MAX_ENERGY;
		if (delay++ == 10) {
			color = color.darker();
		}
	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize,
			Dimension gridDimension, IShape shape) {
		double popSize = (double) populationSize;
		double cap = (double) capacity;
		if (Math.random() < REPRODUCTION_RATE * (1.0 - popSize / cap)) {
			if ((energy+=100) > MAX_ENERGY)
				energy = MAX_ENERGY;
			color = color.darker();
		}
		return null;

	}
	
	/**
	 * Try to consume this agent
	 * <p>
	 * Return true if consumed, otherwise false.
	 * <p>
	 * Thread-safe
	 */
	@Override
	public synchronized boolean consumeAgent() {
		if (energy>0) {
			color = color.brighter();
			energy -= 100;
			return true;
		}
		return false;
	}
	
}
