package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.DeerAgent;
import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.agent.WolfAgent;
import chalmers.dax021308.ecosystem.model.chromosome.WolfGenome;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;
import chalmers.dax021308.ecosystem.model.util.shape.IShape;

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
			double maxAcceleration, double visionRange, boolean groupBehaviour,
			IShape shape, List<IObstacle> obstacles) {

		super(name, gridDimension, shape, obstacles, color);

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
			Position randPos = getRandomPosition();
			Vector velocity = new Vector(maxSpeed, maxSpeed);

			// Create a random vector (uniformly) inside a circle with radius
			// maxSpeed.
			while (velocity.getNorm() > maxSpeed) {
				velocity.setVector(-maxSpeed + Math.random() * 2 * maxSpeed,
						-maxSpeed + Math.random() * 2 * maxSpeed);
			}
			IAgent a = new WolfAgent("Wolf", randPos, color, 10, 20,
					velocity, maxSpeed, maxAcceleration, visionRange,
					groupBehaviour, new WolfGenome());
			newAgents.add(a);
		}
		return newAgents;
	}
	
	public double agentsGroupingProportion(){
		double groupCount = 0;
		WolfAgent da = null;
		for(IAgent a: agents) {
			if(a instanceof WolfAgent) {
				da = (WolfAgent) a;
			} 
			if(da.isAGroupingWolf()){
				groupCount++;
			}
		}
		return groupCount/((double)agents.size());
	}
	
	@Override
	public void updatePositions() {
		super.updatePositions();
		this.interestingPropertyProportion = agentsGroupingProportion();
	}

	/*
	 * @Override public void update() { super.update(); int size =
	 * agents.size(); WolfAgent a; for(int i=0; i<size; i++){ a = (WolfAgent)
	 * agents.get(i); if(a.getEnergy()<=0){ addToRemoveList(a); } } }
	 */
}
