package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.agent.DeerAgent;
import chalmers.dax021308.ecosystem.model.agent.WolfAgent;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * 
 * @author Henrik
 * 
 */
public class WolfPopulation extends AbstractPopulation {
	
	private double maxSpeed;
	private double visionRange;

	public WolfPopulation(String name, Dimension gridDimension,
			int initPopulationSize, Color color, double maxSpeed,
			double maxAcceleration, double visionRange) {
		super(name, gridDimension);
		
		this.visionRange = visionRange;
		agents = initializePopulation(initPopulationSize, gridDimension, color,
				maxSpeed, maxAcceleration, visionRange);
	}

	private List<IAgent> initializePopulation(int populationSize,
			Dimension gridDimension, Color color, double maxSpeed,
			double maxAcceleration, double visionRange) {
		
		List<IAgent> newAgents = new ArrayList<IAgent>(populationSize);
		addNeutralPopulation(this);
		
		for (int i = 0; i < populationSize; i++) {
			Position randPos = new Position(gridDimension.getWidth()
					* Math.random(), gridDimension.getHeight() * Math.random());
			Vector velocity = new Vector(maxSpeed, maxSpeed);
			
			//Create a random vector (uniformly) inside a circle with radius maxSpeed.
			while (velocity.getNorm() > maxSpeed) {
				velocity.setVector(-maxSpeed + Math.random() * 2 * maxSpeed,
						-maxSpeed + Math.random() * 2 * maxSpeed);
			}
			IAgent a = new WolfAgent("Big Hungry", randPos, color, 10,
					20, velocity, maxSpeed, maxAcceleration, visionRange);
			newAgents.add(a);
		}
		return newAgents;
	}

	@Override
	public void update() {
		super.update();
		int size = agents.size();
		WolfAgent a;
		for(int i=0; i<size; i++){
			a = (WolfAgent) agents.get(i);
			if(a.getEnergy()<=0){
				agents.remove(i);
				i--;
				size--;
			}
		}
	}
	@Override
	public double calculateFitness(IAgent agent) {
		// TODO Auto-generated method stub
		return 0;
	}
}
