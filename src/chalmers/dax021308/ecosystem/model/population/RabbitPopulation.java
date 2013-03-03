package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.agent.RabbitAgent;
import chalmers.dax021308.ecosystem.model.util.Gender;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class RabbitPopulation implements IPopulation {

	private List<IAgent> rabbits;
	private String name = "Rabbits";
	private List<IPopulation> preys;
	private List<IPopulation> predators;
	private Dimension worldSize;

	public RabbitPopulation(int popSize, Dimension d) {
		rabbits = new LinkedList<IAgent>();
		preys = new LinkedList<IPopulation>();
		predators = new LinkedList<IPopulation>();
		worldSize = d;

		for (int i = 0; i < popSize; i++) {
			Gender g = Gender.MALE;
			Color c = Color.black;

			if (i % 2 == 0) {
				g = Gender.FEMALE;
				c = Color.lightGray;
			}

			rabbits.add(new RabbitAgent(new Position(d.getWidth()
					* Math.random(), d.getHeight() * Math.random()), name, c,
					20, 20, new Vector(Math.random()*2, Math.random()*2), g));
		}
	}

	
	/**
	 * Clone constructed. Creates a copy of the parameter one.
	 * <p>
	 * Will be here until {@link RabbitPopulation} extends {@link AbstractPopulation} and can use {@link AbstractPopulation#clonePopulation()}
	 * @author Erik Ramqvist
	 * @param rabbitPopulation
	 */
	public RabbitPopulation(RabbitPopulation original) {
		this.worldSize = (Dimension) original.worldSize.clone();
		this.name = original.name;
		preys = new ArrayList<IPopulation>();
		predators = new ArrayList<IPopulation>();
		rabbits = new ArrayList<IAgent>(original.rabbits.size());
		for(IAgent a : original.rabbits) {
			try {
				rabbits.add(a.cloneAgent());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update() {
		List<IAgent> newPopulation = new LinkedList<IAgent>();

		for (IAgent a : rabbits) {
			a.calculateNextPosition(predators, preys, getNeutralPopulations(), worldSize);
			newPopulation.addAll(a.reproduce(null, Integer.MAX_VALUE));
		}
		rabbits.addAll(newPopulation);
	}

	@Override
	public double calculateFitness(IAgent agent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public List<IAgent> getAgents() {
		return rabbits;
	}

	@Override
	public List<IPopulation> getPredators() {
		return predators;
	}

	@Override
	public List<IPopulation> getPreys() {
		return preys;
	}

	@Override
	public void addPredator(IPopulation predator) {
		predators.add(predator);
	}

	@Override
	public void addPrey(IPopulation prey) {
		preys.add(prey);
	}

	@Override
	public void addNeutralPopulation(IPopulation neutral) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<IPopulation> getNeutralPopulations() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public IPopulation clonePopulation() {
		return new RabbitPopulation(this) {
			@Override
			public double calculateFitness(IAgent agent) {
				return 0;
			}
		};
	}


	@Override
	public void removeAgentsFromRemoveList() {
		
	}


	@Override
	public void addToRemoveList(IAgent a) {
		
	}


	@Override
	public void updatePositions() {
		
	}
}
