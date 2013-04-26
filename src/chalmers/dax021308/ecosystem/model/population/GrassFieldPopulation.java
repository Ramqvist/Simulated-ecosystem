package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.util.ArrayList;

import chalmers.dax021308.ecosystem.model.agent.GrassFieldAgent;
import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.environment.SurroundingsSettings;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * The population for the grass, the lowest part of the food chain
 * 
 * @author Henrik
 * 
 */
public class GrassFieldPopulation extends AbstractPopulation {

	public GrassFieldPopulation(String name, int initPopulationSize, 
			Color color, double maxSpeed, double maxAcceleration, 
			double visionRange, int capacity, SurroundingsSettings surroundings) {
		super(name, color, surroundings);
		agents = new ArrayList<IAgent>(initPopulationSize * 100);
		initializePopulation(initPopulationSize, capacity);
	}

	private void initializePopulation(int populationSize, int capacity) {
		
		for (int i = 0; i < populationSize; i++) {
			Position pos = getRandomPosition();
			Vector velocity = new Vector(1,1);
			IAgent a = new GrassFieldAgent(getName(), pos, color, 50, 50, velocity, 1, capacity);
			agents.add(a);
		}
	}

	@Override
	public void updatePositions() {
		for (IAgent a : agents)
			a.reproduce(null, agents.size(), surroundings);
	}

	@Override
	public void update(int fromPos, int toPos) {
		// Do nothing
	}

	@Override
	public void removeAgentsFromRemoveList() {
		// Do nothing, shouldn't remove when eating, overriding to increase
		// performance
	}

	@Override
	public synchronized void addToRemoveList(IAgent a) {
		// Try do nothing, just let the food be there
	}

}