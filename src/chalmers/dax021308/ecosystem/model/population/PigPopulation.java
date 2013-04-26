package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.agent.PigAgent;
import chalmers.dax021308.ecosystem.model.environment.SurroundingsSettings;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * 
 * @author orignal Deer Population by Sebastian �nerud
 * 
 */
public class PigPopulation extends AbstractPopulation {


	public PigPopulation(String name, int initPopulationSize, Color color, 
			double maxSpeed, double maxAcceleration, double visionRange, 
			boolean groupBehaviour, SurroundingsSettings surroundings) {
		
		super(name, color, surroundings);
		this.groupBehaviour = groupBehaviour;
		agents = initializePopulation(initPopulationSize, surroundings.getGridDimension(), color,
				maxSpeed, maxAcceleration, visionRange);
	}

	private List<IAgent> initializePopulation(int populationSize,
			Dimension gridDimension, Color color, double maxSpeed,
			double maxAcceleration, double visionRange) {
		List<IAgent> newAgents = new ArrayList<IAgent>(populationSize);
		addNeutralPopulation(this);
		for (int i = 0; i < populationSize; i++) {
			Position randPos = getRandomPosition();
			Vector velocity = new Vector(maxSpeed, maxSpeed);

			// Create a random vector (uniformly) inside a circle with radius
			// maxSpeed.
			while (velocity.getNorm() > maxSpeed) {
				velocity.setVector(-maxSpeed + Math.random() * 2 * maxSpeed,
						-maxSpeed + Math.random() * 2 * maxSpeed);
			}
			IAgent a = new PigAgent("Deer", randPos,
					color, 5, 10, velocity, maxSpeed, maxAcceleration,
					visionRange, groupBehaviour);
			newAgents.add(a);
		}
		return newAgents;
	}
}
