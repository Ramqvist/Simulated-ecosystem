package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.GrassAgent;
import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * The population for the grass, the lowest part of the food chain
 * 
 * @author Henrik
 * 
 */
public class GrassPopulation extends AbstractPopulation {

	public GrassPopulation(String name, Dimension gridDimension,
			int initPopulationSize, Color color, double maxSpeed,
			double maxAcceleration, double visionRange, int capacity, IShape shape, List<IObstacle> obstacles) {
		super(name, gridDimension, shape, obstacles);

		this.color = color;
		agents = initializePopulation(initPopulationSize, gridDimension, color,
				maxSpeed, capacity);
	}

	private List<IAgent> initializePopulation(int populationSize,
			Dimension gridDimension, Color color, double maxSpeed, int capacity) {

		List<IAgent> newAgents = new ArrayList<IAgent>(populationSize * 100);
		for (int i = 0; i < populationSize; i++) {
			Position randPos = getRandomPosition();
			Vector velocity = new Vector(maxSpeed, maxSpeed);
			IAgent a = new GrassAgent(getName(), randPos, color, 5, 5,
					velocity, maxSpeed, gridDimension, capacity, shape);
			newAgents.add(a);
		}
		return newAgents;
	}
	
	@Override
	public double getComputationalFactor() {
		return 25;
	}

}
