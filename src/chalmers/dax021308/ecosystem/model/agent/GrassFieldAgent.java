package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.SurroundingsSettings;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.population.settings.GrassFieldSettings;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * Grass field, lowest part of the food chain. Instead of dying and getting
 * removed like the normal grassAgent, it stores a limited amount of energy
 * which decreases when another agent eats it.
 * 
 * @author Henrik
 */
public class GrassFieldAgent extends AbstractAgent {

	private double REPRODUCTION_RATE = GrassFieldSettings.instance.reproduction_rate.value;
	private double MAX_ENERGY = GrassFieldSettings.instance.max_energy.value;
	private List<GrassSeed> seeds;

	public GrassFieldAgent(String name, Position pos, Color color, int width,
			int height, Vector velocity, double maxSpeed, int capacity) {
		super(name, pos, color, width, height, velocity, maxSpeed, 0, 0);
		this.capacity = capacity;
		energy = MAX_ENERGY / 2;
		seeds = new ArrayList<GrassSeed>();
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral,
			SurroundingsSettings surroundings) {
		// Do nothing, grass shouldn't move!
	}

	@Override
	public void updatePosition() {
		// Do nothing? Shouldn't get old or anything
	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize,
			SurroundingsSettings surroundings) {
		for (int i = 0; i < seeds.size(); i++) {
			if (seeds.get(i).isBlooming()) {
				energy += seeds.get(i).getEnergy();
				if (energy > MAX_ENERGY)
					energy = MAX_ENERGY;
				seeds.remove(i--);
			}
		}
		if (Math.random() < REPRODUCTION_RATE) {
			double energyProportion = (double) energy / (double) MAX_ENERGY;
			double newEnergy = energy * energyProportion
					* (1 - energyProportion) * 0.1;
			seeds.add(new GrassSeed(newEnergy));
		}
		int red = (int) (150.0 - 150.0 * (((double) energy) / MAX_ENERGY));
		int green = (int) (55.0 + 200.0 * (((double) energy) / MAX_ENERGY));
		this.color = new Color(red, green, 0);
		return null;

	}

	@Override
	public synchronized boolean tryConsumeAgent() {
		// Let the current agent be eaten if it has enough energy
		if (energy >= 8) {
			energy -= 8;
			return true;
		}
		return false;
	}

	@Override
	public boolean isLookingTasty(IAgent agent) {
		double distance = agent.getPosition().getDistance(position)
				- (width + height) / 2;
		// If the agent has enough food for three agents, then it looks tasty!
		if (energy >= 24) {
			return distance <= agent.getVisionRange();
		}
		return false;
	}

	@Override
	public double impactForcesBy() {
		// if it has a low amount of food it should negatively impact the agents
		// who wants to eat it
		return MAX_ENERGY / 2 - energy / 100;
	}

	private class GrassSeed {
		private int lifeLength = 0;
		private double energy;
		private int TIME_TO_BLOOM = 100;// (int) GrassFieldSettings.instance.timeToBloom.value;

		private GrassSeed(double energy) {
			this.energy = energy;
		}

		private double getEnergy() {
			return energy;
		}

		private boolean isBlooming() {
			return lifeLength++ > TIME_TO_BLOOM;
		}
	}
}
