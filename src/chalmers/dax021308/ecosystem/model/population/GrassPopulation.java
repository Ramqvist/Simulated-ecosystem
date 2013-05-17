package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.GrassAgent;
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
public class GrassPopulation extends AbstractPopulation {

	/**
	 * 
	 * @param name
	 * @param initPopulationSize
	 * @param color
	 * @param maxSpeed
	 * @param maxAcceleration
	 * @param visionRange
	 * @param capacity
	 * @param surroundings
	 */
	public GrassPopulation(String name, int initPopulationSize, Color color, 
			int capacity, SurroundingsSettings surroundings) {
		super(name, color, surroundings);
		agents = initializePopulation(initPopulationSize, surroundings.getGridDimension(), color,
				1, capacity);
	}

	private List<IAgent> initializePopulation(int populationSize,
			Dimension gridDimension, Color color, double maxSpeed, int capacity) {

		List<IAgent> newAgents = new ArrayList<IAgent>(populationSize * 100);
		for (int i = 0; i < populationSize; i++) {
			Position randPos = getRandomPosition();
			randPos.setY(randPos.getY()-9000);
			Vector velocity = new Vector(maxSpeed, maxSpeed);
			IAgent a = new GrassAgent(getName(), randPos, color, 5, 5,
					velocity, maxSpeed, capacity);
			newAgents.add(a);
		}
		return newAgents;
	}
	
	@Override
	public double getComputationalFactor() {
		return 25;
	}

}
