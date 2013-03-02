package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import sun.font.CreatedFontTracker;

import chalmers.dax021308.ecosystem.model.agent.DeerAgent;
import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.agent.PigAgent;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * A filthy but smart animal. 
 * <p>
 * I tried to keep allocation of new objects at a minimum.
 * <p>
 * Uses {@link PigAgent} as agent.
 * Coded this for practice.
 * @author Erik Ramqvist
 *
 */
public class PigPopulation extends AbstractPopulation {

	private Color color;
	private double visionRange;
	private double maxSpeed;
	private Random ran;
	private final int pigWidth  = 10;
	private final int pigHeight = 25;
	private double maxAcceleration;

	public PigPopulation(String name, Dimension gridDimension,int initPopulationSize, Color color, double maxSpeed,double maxAcceleration, double visionRange) {
		super(name, gridDimension);
		this.color = color;
		this.visionRange = visionRange;
		this.maxSpeed = maxSpeed;
		this.maxAcceleration = maxAcceleration;
		this.gridDimension = gridDimension;
		ran = new Random();
		createInitialPopulation(initPopulationSize);
	}
	
	private void createInitialPopulation(int initPopulationSize) {
		this.agents = new ArrayList<IAgent>((int) (initPopulationSize + (initPopulationSize * 0.2) ));
		for(int i = 0 ; i < initPopulationSize; i++) {

			// Create a random vector (uniformly) inside a circle with radius
			// maxSpeed.
			Position pos = new Position(ran.nextInt(gridDimension.width), ran.nextInt(gridDimension.height));
			Vector velocity = new Vector(maxSpeed, maxSpeed);
			while (velocity.getNorm() > maxSpeed) {
				velocity.setVector(-maxSpeed + ran.nextDouble() * 2 * maxSpeed,
						-maxSpeed + ran.nextDouble() * 2 * maxSpeed);
			}
		IAgent newPig = new PigAgent("Pig - " + i, pos , color, pigWidth, pigHeight,velocity, maxSpeed, visionRange, maxAcceleration, ran);
			agents.add(newPig);
		}
	}

	@Override
	public double calculateFitness(IAgent agent) {
		return 0;
	}

	@Override
	public void update() {
		int populationSize = agents.size();
		PigAgent a;
		for (int i = 0; i < populationSize; i++) {
			agents.get(i).calculateNextPosition(predators, preys, neutral,gridDimension);
		}
		List<IAgent> kids = new ArrayList<IAgent>();
		populationSize = agents.size();
		for (int i = 0; i < populationSize; i++) {
			a = (PigAgent) agents.get(i);
			a.updatePosition();
			IAgent spawn = a.reproduceOne(populationSize);
			if (spawn != null) {
				kids.add(spawn);
			}
		}
		if (kids != null && !kids.isEmpty()) {
			agents.addAll(kids);
		}
	}

}