package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.IAgent;

/**
 * 
 * @author Henrik
 * Abstract class for handling the dummy methods
 */
public abstract class AbstractPopulation implements IPopulation {
	protected List<IAgent> agents;
	protected Dimension gridDimension;
	private List<IPopulation> preys;
	private List<IPopulation> predators;
	private List<IPopulation> neutral;
	private String name;

	public AbstractPopulation() {
		preys = new ArrayList<IPopulation>();
		predators = new ArrayList<IPopulation>();
		neutral = new ArrayList<IPopulation>();
	}

	public AbstractPopulation(Dimension gridDimension) {
		this();
		this.gridDimension = gridDimension;
	}

	/**
	 * Clone constructor.
	 * <p>
	 * Use only for cloning.
	 * 
	 * @param original
	 *            the AbstractPopulation to clone
	 */
	public AbstractPopulation(AbstractPopulation original) {
		this.gridDimension = original.gridDimension;
		this.name = original.name;
		preys = new ArrayList<IPopulation>();
		predators = new ArrayList<IPopulation>();
		neutral = new ArrayList<IPopulation>();
		agents = new ArrayList<IAgent>();
		for (IAgent a : original.agents) {
			try {
				agents.add(a.cloneAgent());
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void update() {
		for (IAgent a : agents) {
			a.calculateNextPosition(predators, preys, neutral, gridDimension);
		}
		for (IAgent a : agents) {
			a.updatePosition();
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

	@Override
	public List<IPopulation> getNeutralPopulations() {
		return neutral;
	}

	@Override
	public void addNeutralPopulation(IPopulation neutral) {
		this.neutral.add(neutral);
	}

	@Override
	public IPopulation clonePopulation() {
		return new AbstractPopulation(this) {
			@Override
			public double calculateFitness(IAgent agent) {
				return 0;
			}
		};
	}
}
