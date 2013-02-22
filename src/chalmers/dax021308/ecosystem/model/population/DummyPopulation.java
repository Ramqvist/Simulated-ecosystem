package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.agent.SimpleAgent;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * 
 * @author Sebastian
 *
 */
public class DummyPopulation implements IPopulation {
	
	private List<IAgent> agents;
	private Dimension gridDimension;
	private double maxSpeed;
	private double visionRange;
	private List<IPopulation> preys;
	private List<IPopulation> predators;
	
	public DummyPopulation(List<IAgent> agentList){
		agents = agentList;
	}

	public DummyPopulation(Dimension gridDimension, int initPopulationSize, Color color, 
			double maxSpeed, double maxAcceleration,double visionRange){
		this.gridDimension = gridDimension;
		this.visionRange = visionRange;
		agents = initializePopulation(initPopulationSize, gridDimension, color, maxSpeed, maxAcceleration, visionRange);
	}
	
	private List<IAgent> initializePopulation(int populationSize, Dimension gridDimension, 
											Color color, double maxSpeed, double maxAcceleration,double visionRange) {
		List<IAgent> newAgents = new ArrayList<IAgent>(populationSize);
		preys = new ArrayList<IPopulation>();
		predators = new ArrayList<IPopulation>();
		for(int i=0;i<populationSize;i++) {
			Position randPos = new Position(gridDimension.getWidth()*Math.random(), 
											gridDimension.getHeight()*Math.random());
			Vector velocity = new Vector(maxSpeed,maxSpeed);
			while(Math.sqrt(Math.pow(velocity.getX(),2)+Math.pow(velocity.getY(),2))>maxSpeed){
				velocity.setVector(-maxSpeed+Math.random()*2*maxSpeed, -maxSpeed+Math.random()*2*maxSpeed);
			}
			SimpleAgent a = new SimpleAgent("Big tasty", randPos, color, 10, 10, 
											velocity, maxSpeed, maxAcceleration,visionRange);
			newAgents.add(a);
		}
		return newAgents;
	}

	@Override
	public void update() {
		for(IAgent a: agents){
			a.updatePosition(predators, preys, gridDimension);
		}
	}
	
	@Override
	public double calculateFitness(IAgent agent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<IAgent> getAgents() {
		return agents;
	}

	@Override
	public List<IPopulation> getPredators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IPopulation> getPreys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addPredator(IPopulation predator) {
		this.predators.add(predator);
	}

	@Override
	public void addPrey(IPopulation prey) {
		this.preys.add(prey);
	}

}
