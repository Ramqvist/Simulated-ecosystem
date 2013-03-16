package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.agent.WolfAgentGrid;
import chalmers.dax021308.ecosystem.model.environment.WorldGrid;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * 
 * @author Henrik
 * 
 */
public class WolfPopulationGrid extends AbstractPopulation {
	
	private double maxSpeed;
	private double visionRange;

	public WolfPopulationGrid(String name, Dimension gridDimension,
			int initPopulationSize, Color color, double maxSpeed,
			double maxAcceleration, double visionRange, boolean groupBehaviour, IShape shape) {
		
		super(name, gridDimension, shape, null);
		
		this.visionRange = visionRange;
		this.groupBehaviour = groupBehaviour;
		agents = initializePopulation(initPopulationSize, gridDimension, color,
				maxSpeed, maxAcceleration, visionRange);
	}

	private List<IAgent> initializePopulation(int populationSize,
			Dimension gridDimension, Color color, double maxSpeed,
			double maxAcceleration, double visionRange) {
		
		List<IAgent> newAgents = new ArrayList<IAgent>(populationSize);
		addNeutralPopulation(this);
		
		for (int i = 0; i < populationSize; i++) {
			Position randPos = shape.getRandomPosition(gridDimension);
			Vector velocity = new Vector(maxSpeed, maxSpeed);
			
			//Create a random vector (uniformly) inside a circle with radius maxSpeed.
			while (velocity.getNorm() > maxSpeed) {
				velocity.setVector(-maxSpeed + Math.random() * 2 * maxSpeed,
						-maxSpeed + Math.random() * 2 * maxSpeed);
			}
			IAgent a = new WolfAgentGrid("Big Hungry", randPos, color, 10,
					20, velocity, maxSpeed, maxAcceleration, visionRange, groupBehaviour);
			newAgents.add(a);
			wg.add(a);
		}
		setColor(color);
		return newAgents;
	}

	@Override
	public double calculateFitness(IAgent agent) {
		// TODO Auto-generated method stub
		return 0;
	}
}
