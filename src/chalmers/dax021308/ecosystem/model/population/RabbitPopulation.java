package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
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
			Vector velocity = new Vector(1, 1);

			if (i % 2 == 0) {
				g = Gender.FEMALE;
				c = Color.lightGray;
//				velocity = new Vector(1, 1);
			}

			rabbits.add(new RabbitAgent(new Position(d.getWidth()
					* Math.random(), d.getHeight() * Math.random()), name, c,
					20, 20, velocity, g));
		}
	}

	@Override
	public void update() {
		List<IAgent> newPopulation = new LinkedList<IAgent>();

		for (IAgent a : rabbits) {
			a.updatePosition(predators, preys, getNeutralPopulations(),
					worldSize);

			List<IAgent> kids = a.reproduce(null);
			if (kids.size() > 0) {
				newPopulation.addAll(kids);
			}
		}
		newPopulation.addAll(rabbits);
		rabbits = newPopulation;
		System.out.println("Size: "+rabbits.size());
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
}
