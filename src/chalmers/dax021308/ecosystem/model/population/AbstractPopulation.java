package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.DeerAgent;
import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.WorldGrid;
import chalmers.dax021308.ecosystem.model.util.CircleShape;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.SquareShape;
import chalmers.dax021308.ecosystem.model.util.Stat;

/**
 * 
 * @author Henrik Abstract class for handling the dummy methods
 */
public abstract class AbstractPopulation implements IPopulation {
	protected List<IAgent> agents;
	protected Dimension gridDimension;
	protected int capacity = Integer.MAX_VALUE;
	protected List<IPopulation> preys;
	protected List<IPopulation> predators;
	protected List<IPopulation> neutral;
	protected WorldGrid wg;
	protected IShape shape;
	/**
	 * Remove list for this Population.
	 * <p>
	 * Use the method {@link IPopulation#addToRemoveList(IAgent)} for adding to
	 * this list! And not removeList.add()!
	 * <p>
	 * This method is used to synchronize the adding of agents, i.e. several
	 * threads can add at the same time.
	 */
	protected List<IAgent> removeList;

	protected Color color = Color.BLACK; // Standard color for population.

	protected List<Integer> lifeLengths;
	protected boolean groupBehaviour;
	private String name;

	public AbstractPopulation() {
		preys = new ArrayList<IPopulation>();
		predators = new ArrayList<IPopulation>();
		neutral = new ArrayList<IPopulation>();
		removeList = new ArrayList<IAgent>();
		lifeLengths = new LinkedList<Integer>();
		wg = WorldGrid.getInstance();
	}

	public AbstractPopulation(String name, Dimension gridDimension, IShape shape) {
		this();
		this.name = name;
		this.gridDimension = gridDimension;
		this.groupBehaviour = true;

		this.shape = shape;

	}

	public AbstractPopulation(String name, Dimension gridDimension,
			int capacity, boolean groupBehaviour, IShape shape) {
		this(name, gridDimension, shape);
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
		this.color = original.color;
		this.groupBehaviour = original.groupBehaviour;
		this.name = original.name;
		this.shape = original.shape;
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

	/**
	 * Override if you use linked-list as agentList! (Default is ArrayList.)
	 * <P>
	 * Update the whole population, same as update(0, agents.size())M
	 * 
	 * @param fromPos
	 * @param toPos
	 */
	@Override
	public void update() {
		update(0, agents.size());
	}

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
	public void updateFirstHalf() {
		int agentSize = agents.size();
		int halfStart = agentSize / 2;
		update(0, halfStart);
	}

	@Override
	public void updateSecondHalf() {
		int agentSize = agents.size();
		int halfStart = agentSize / 2;
		update(halfStart, agentSize);
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

	/**
	 * Clones the given list with {@link IPopulation#clonePopulation()} method.
	 * <p>
	 * 
	 * @deprecated Use clonePopulationListWithRecycledList, instead to reduce
	 *             unnecessary heap-allocations.
	 */
	@Deprecated
	public static List<IPopulation> clonePopulationList(
			List<IPopulation> original) {
		List<IPopulation> list = new ArrayList<IPopulation>(original.size());
		for (IPopulation p : original) {
			list.add(p.clonePopulation());
		}
		return list;
	}

	/**
	 * Copies the information from the source to the recycled one.
	 * 
	 * @param recycled
	 * @param source
	 */
	public static void clonePopulationListWithRecycledList(
			List<IPopulation> recycled, List<IPopulation> source) {
		if (recycled == null) {
			throw new NullPointerException("recycled list is null");
		}
		int i = 0;
		for (IPopulation p : source) {
			recycled.set(i, p);
			i++;
		}
	}

	public static IPopulation createFromFile(String input) {
		String[] inputArray = input.split(";");
		String name = inputArray[0];
		Dimension dim = new Dimension(Integer.parseInt(inputArray[1]),
				Integer.parseInt(inputArray[2]));
		int cap = Integer.parseInt(inputArray[3]);
		String shapeModel = inputArray[4];
		IShape shape = null;
		if (shapeModel == EcoWorld.SHAPE_SQUARE) {
			shape = new SquareShape();
		} else if (shapeModel == EcoWorld.SHAPE_CIRCLE) {
			shape = new CircleShape();
		}
		if (shape == null)
			throw new IllegalArgumentException("Illegal Shape from file.");

		return new AbstractPopulation(name, dim, cap, true, shape) {
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
		sb.append(';');
		sb.append(shape.getShape());
		return null;
	}

	@Override
	public void updatePositions() {
		List<IAgent> kids = new ArrayList<IAgent>();
		int populationSize = agents.size();
		for (IAgent a : agents) {
			a.updatePosition();
			List<IAgent> spawn = a.reproduce(null, populationSize);
			if (spawn != null) {
				kids.addAll(spawn);
			}
		}
		if (kids != null) {
			agents.addAll(kids);
			wg.addAll(kids);
		}

		// System.out.println(name + " life length: mean = " +
		// Stat.mean(lifeLengths) +
		// " | variance = " + Stat.sampleVariance(lifeLengths) +
		// " | sample size = " + lifeLengths.size());
	}

	/**
	 * Clears out the agents in the removeList.
	 * <p>
	 * Warning! Use only when no other thread is iterating of the agentlist.
	 */
	public void removeAgentsFromRemoveList() {
		IAgent a;
		DeerAgent da;
		for (int i = 0; i < removeList.size(); i++) {
			a = removeList.get(i);
			lifeLengths.add(a.getLifeLength());
				
			agents.remove(a);
		}
		removeList.clear();
	}

	@Override
	public synchronized void addToRemoveList(IAgent a) {
		removeList.add(a);
	}

	@Override
	public void setColor(Color color) {
		this.color = color;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see chalmers.dax021308.ecosystem.model.population.IPopulation#getColor()
	 */
	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public String toString() {
		return "Population name: " + name + " NumAgents:" + agents.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see chalmers.dax021308.ecosystem.model.population.IPopulation#getSize()
	 */
	public int getSize() {
		if (agents != null)
			return agents.size();
		else
			return 0;
	}

	@Override
	public double getComputationalFactor() {
		return 1;
	}
}
