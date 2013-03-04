package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.GrassAgent;
import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * 
 * @author Henrik
 * 
 */
public class GrassPopulation extends AbstractPopulation {
	int reproduceDelay = 0;

	public GrassPopulation(String name, Dimension gridDimension,
			int initPopulationSize, Color color, double maxSpeed,
			double maxAcceleration, double visionRange, int capacity) {
		super(name, gridDimension);
		agents = initializePopulation(initPopulationSize, gridDimension, color,
				maxSpeed, capacity);
	}

	private List<IAgent> initializePopulation(int populationSize,
			Dimension gridDimension, Color color, double maxSpeed, int capacity) {
		List<IAgent> newAgents = new ArrayList<IAgent>(populationSize * 100);
		for (int i = 0; i < populationSize; i++) {
			Position randPos = new Position(gridDimension.getWidth()
					* Math.random(), gridDimension.getHeight() * Math.random());
			Vector velocity = new Vector(maxSpeed, maxSpeed);
			IAgent a = new GrassAgent(getName(), randPos, color, 5, 5,
					velocity, maxSpeed, gridDimension, capacity);
			newAgents.add(a);
		}
		return newAgents;

	}

	@Override
	public double calculateFitness(IAgent agent) {
		// TODO Auto-generated method stub
		return 0;
	}

}
