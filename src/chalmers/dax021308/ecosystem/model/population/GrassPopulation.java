package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
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

	public GrassPopulation(Dimension gridDimension, int initPopulationSize,
			Color color, double maxSpeed, double maxAcceleration,
			double visionRange) {
		super(gridDimension);
		agents = initializePopulation(initPopulationSize, gridDimension, color,
				maxSpeed);
	}

	private List<IAgent> initializePopulation(int populationSize,
			Dimension gridDimension, Color color, double maxSpeed) {
		List<IAgent> newAgents = new ArrayList<IAgent>(populationSize);
		for (int i = 0; i < populationSize; i++) {
			Position randPos = new Position(gridDimension.getWidth()
					* Math.random(), gridDimension.getHeight() * Math.random());
			Vector velocity = new Vector(maxSpeed, maxSpeed);
			IAgent a = new GrassAgent("Grass", randPos, color, 5, 5, velocity,
					maxSpeed);
			newAgents.add(a);
		}
		return newAgents;

	}

	@Override
	public void update() {
		//TODO Fix proper call once reproduce is working
		for (IAgent a : agents) a.reproduce(null);

	}

	@Override
	public double calculateFitness(IAgent agent) {
		// TODO Auto-generated method stub
		return 0;
	}

}
