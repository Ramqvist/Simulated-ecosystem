package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.agent.DeerAgent;
import chalmers.dax021308.ecosystem.model.agent.WolfAgent;
import chalmers.dax021308.ecosystem.model.chromosome.DeerGenome;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Stat;
import chalmers.dax021308.ecosystem.model.util.Vector;
import chalmers.dax021308.ecosystem.model.util.shape.IShape;

/**
 * 
 * @author Sebastian
 * 
 */
public class DeerPopulation extends AbstractPopulation {

	public DeerPopulation(List<IAgent> agentList) {
		agents = agentList;
	}

	public DeerPopulation(String name, Dimension gridDimension,
			int initPopulationSize, Color color, double maxSpeed,
			double maxAcceleration, double visionRange, boolean groupBehaviour, IShape shape, List<IObstacle> obstacles) {
		
		super(name, gridDimension, shape, obstacles, color);
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
			Position randPos = getRandomPosition();
			Vector velocity = new Vector(maxSpeed, maxSpeed);

			// Create a random vector (uniformly) inside a circle with radius
			// maxSpeed.
			while (velocity.getNorm() > maxSpeed) {
				velocity.setVector(-maxSpeed + Math.random() * 2 * maxSpeed,
						-maxSpeed + Math.random() * 2 * maxSpeed);
			}
			IAgent a = new DeerAgent("Deer", randPos,
					color, 5, 10, velocity, maxSpeed, maxAcceleration,
					visionRange, groupBehaviour, new DeerGenome());

			newAgents.add(a);
		}
		return newAgents;
	}
	
	public double stottingProportion(){
		double stottingCount = 0;
		DeerAgent da = null;
		for(IAgent a: agents) {
			if(a instanceof DeerAgent) {
				da = (DeerAgent) a;
			} 
			if(da.isAStottingDeer()){
				stottingCount++;
			}
		}
		return stottingCount/((double)agents.size());
	}
	
	public double agentsGroupingProportion(){
		double groupCount = 0;
		DeerAgent da = null;
		for(IAgent a: agents) {
			if(a instanceof DeerAgent) {
				da = (DeerAgent) a;
			} 
			if(da.isAGroupingDeer()){
				groupCount++;
			}
		}
		return groupCount/((double)agents.size());
	}
	
	@Override
	public void updatePositions() {
		super.updatePositions();
		interestingPropertyProportion = agentsGroupingProportion();
	}
}
