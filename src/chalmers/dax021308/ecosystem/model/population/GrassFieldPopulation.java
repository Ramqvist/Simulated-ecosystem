package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.GrassAgent;
import chalmers.dax021308.ecosystem.model.agent.GrassFieldAgent;
import chalmers.dax021308.ecosystem.model.agent.GrassPatch;
import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * The population for the grass, the lowest part of the food chain
 * 
 * @author Henrik
 * 
 */
public class GrassFieldPopulation extends AbstractPopulation {
	private List<GrassPatch> grass;

	public GrassFieldPopulation(String name, Dimension gridDimension,
			int initPopulationSize, Color color, double maxSpeed,
			double maxAcceleration, double visionRange, int capacity,
			IShape shape) {
		super(name, gridDimension, shape);
		grass = new ArrayList<GrassPatch>();
		this.color = color;
		agents = initializePopulation(initPopulationSize, gridDimension, color,
				maxSpeed, capacity);
	}

	private List<IAgent> initializePopulation(int populationSize,
			Dimension gridDimension, Color color, double maxSpeed, int capacity) {
		int nrOfPatches = 10;
		for (int i = 0; i < nrOfPatches; i++) {
			grass.add(new GrassPatch(shape.getRandomPosition(gridDimension),
					gridDimension, capacity, name, color));
		}
		List<IAgent> newAgents = new ArrayList<IAgent>(populationSize * 100);
		for (int i = 0; i < populationSize/10; i++) {
			int rdm = (int) (Math.random() * 10);
			IAgent ab = grass.get(rdm).createGrass(populationSize,
					gridDimension, shape);
			Position randPos = shape.getRandomPosition(gridDimension);
			Vector velocity = new Vector(maxSpeed, maxSpeed);
			IAgent a = new GrassFieldAgent(getName(), randPos, color, 50, 50,
					velocity, maxSpeed, capacity);
			newAgents.add(a);
		}
		return newAgents;
	}

	@Override
	public void updatePositions(){
		List<IAgent> kids = new ArrayList<IAgent>();
		int populationSize = agents.size();
		for (IAgent a : agents) {
			a.updatePosition();
			List<IAgent> spawn = a.reproduce(null, populationSize,
					gridDimension, shape);
			if (spawn != null) {
				kids.addAll(spawn);
			}
		}
		if (kids != null) {
			agents.addAll(kids);
			wg.addAll(kids);
		}

	}
	@Override
	public double calculateFitness(IAgent agent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getComputationalFactor() {
		return 25;
	}
	
	@Override
	public void update(int fromPos, int toPos) {
		IAgent a;
		for (int i = fromPos; i < toPos; i++) {
			a = agents.get(i);
			a.calculateNextPosition(predators, preys, neutral, gridDimension,
					shape);
			if (a.isItTimeToDie()) {
				addToRemoveList(a);
			}
		}
	}
	
	@Override
	public synchronized void addToRemoveList(IAgent a) {
		//Try do nothing, just let the food be there
	}

}
