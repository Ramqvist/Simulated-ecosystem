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
	protected int capacity = Integer.MAX_VALUE;
	protected List<IPopulation> preys;
	protected List<IPopulation> predators;
	protected List<IPopulation> neutral;
	protected List<IAgent> removeList;

	protected Color color = Color.BLACK;	// Standard color for population.
	protected boolean groupBehaviour;
	private String name;
	
	
 	public AbstractPopulation() {
		preys = new ArrayList<IPopulation>();
		predators = new ArrayList<IPopulation>();
		neutral = new ArrayList<IPopulation>();
		removeList = new ArrayList<IAgent>();
	}
	
	public AbstractPopulation(String name, Dimension gridDimension) {
		this();
		this.name = name;
		this.gridDimension = gridDimension;
		this.groupBehaviour = true;
	}

	public AbstractPopulation(String name, Dimension gridDimension, int capacity, boolean groupBehaviour) {
		this(name, gridDimension);
		this.capacity = capacity;
		this.groupBehaviour = groupBehaviour;
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
	
	/*@Override
	public void update() {
		for (IAgent a : agents) {
			a.calculateNextPosition(predators, preys, neutral, gridDimension);
		}
	}*/

	@Override
	public void update() {
		for (IAgent a : agents) {
			// Remove dead agents.
			
			a.calculateNextPosition(predators, preys, neutral, gridDimension);
			if(a.getEnergy()<=0){
				agents.remove(a);
			}
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

	public static IPopulation createFromFile(String input) {
		String[] inputArray = input.split(";");
		String name = inputArray[0];
		Dimension dim = new Dimension(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2]));
		int cap = Integer.parseInt(inputArray[3]);
		return new AbstractPopulation(name, dim, cap, true) {
			@Override
			public double calculateFitness(IAgent agent) {
				return 0;
			}
		};
	}
	
	@Override
	public String toBinaryString() {
		StringBuffer sb = new StringBuffer();
		sb.append(name);
		sb.append(';');
		sb.append(gridDimension.width);
		sb.append(';');
		sb.append(gridDimension.height);
		sb.append(';');
		sb.append(capacity);
		return null;
	}
	
	@Override
	public void updatePositions() {
		List<IAgent> kids = new ArrayList<IAgent>();
		int populationSize = agents.size();
		for (IAgent a : agents) {
			a.updatePosition();
			List<IAgent> spawn = a.reproduce(null,populationSize);
			if (spawn != null) {
				kids.addAll(spawn);
			}
		}
		if (kids != null)
			agents.addAll(kids);
	}
	
	/**
	 * Clears out the agents in the removeList.
	 * <p>
	 * Warning! Use only when no other thread is iterating of the agentlist.
	 */
	public void removeAgentsFromRemoveList() {
		IAgent a;
		for(int i = 0 ; i < removeList.size() ; i++)  {
			a = removeList.get(i);
			agents.remove(a);
		}
		removeList.clear();
	}


	@Override
	public synchronized void addToRemoveList(IAgent a) {
		removeList.add(a);
	}

	@Override
	public void setColor(Color color){
		this.color = color;
	}
	
	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.population.IPopulation#getColor()
	 */
	@Override
	public Color getColor() {
		return color;
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.population.IPopulation#getSize()
	 */
	public int getSize() {
		if (agents != null)
			return agents.size();
		else
			return 0;
	}
}
