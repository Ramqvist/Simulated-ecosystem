package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.IAgent;

public abstract class AbstractPopulation implements IPopulation {
	protected List<IAgent> agents;
	private Dimension gridDimension;
	private List<IPopulation> preys;
	private List<IPopulation> predators;
	private String name;

	public AbstractPopulation() {
		preys = new ArrayList<IPopulation>();
		predators = new ArrayList<IPopulation>();
	}

	public AbstractPopulation(Dimension gridDimension) {
		this();
		this.gridDimension = gridDimension;
	}

	@Override
	public void update() {
		for (IAgent a : agents) {
			a.updatePosition(predators, preys, gridDimension);
		}
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<IAgent> getAgents() {
		return agents;
	}

	@Override
	public List<IPopulation> getPredators() {
		return predators;
	}

	@Override
	public void addPredator(IPopulation predator) {
		predators.add(predator);
	}

	@Override
	public List<IPopulation> getPreys() {
		return preys;
	}

	@Override
	public void addPrey(IPopulation prey) {
		preys.add(prey);
	}

}
