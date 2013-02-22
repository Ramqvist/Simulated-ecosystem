package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;
/**
 * 
 * @author Henrik
 * Its purpose is simply to hunt down the SimpleAgent (or any other agent) in a simple way
 */
public class SimplePreyAgent extends AbstractAgent{

	private double maxSpeed;
	private double visionRange;
	private double maxAcceleration;

	public SimplePreyAgent(String name, Position p, Color c, int width,
			int height, Vector velocity) {
		super(name, p, c, width, height, velocity);
		this.maxSpeed = maxSpeed;
		this.maxAcceleration = maxAcceleration;
		this.visionRange = visionRange;

	}

	@Override
	public void updatePosition(List<IPopulation> predators,
			List<IPopulation> preys, Dimension dim) {
		setNewVelocity(predators, preys, dim);
		getPosition().addVector(getVelocity());
	}

	@Override
	public List<IAgent> reproduce(IAgent agent) {
		// TODO Auto-generated method stub
		System.err.println("ERROR IN REPRODUCE METHOD, METHOD UNDEFINED");
		return null;
	}

	private void setNewVelocity(List<IPopulation> predators,
			List<IPopulation> preys, Dimension dim) {
		
		
	}

}
