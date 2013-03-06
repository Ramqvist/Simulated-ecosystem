package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Gender;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class RabbitAgent extends AbstractAgent {

	private Position gridPosition;
	
	/**
	 * 
	 * @param position
	 * @param name
	 * @param color
	 * @param width
	 * @param height
	 * @param velocity
	 * @param gender
	 * @param maxSpeed
	 * @param visionRange
	 * @param maxAcceleration
	 */
	public RabbitAgent(Position position, String name, Color color, 
			int width, int height, Vector velocity, Gender gender,
			double maxSpeed, double visionRange, double maxAcceleration) {
		super(name, position, color, width, height, velocity, maxSpeed, visionRange, maxAcceleration);

		super.gender = gender;
		gridPosition = position;
	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize) {
		List<IAgent> kids = new ArrayList<IAgent>();
		if (Math.random() < 0.0001) {
			RabbitAgent r = new RabbitAgent(new Position(position), name, color, width, height, new Vector(velocity), null, maxSpeed, visionRange, maxAcceleration);
			kids.add(r);
			EcoWorld.worldGrid.add(r);
		}
		return kids;
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral, Dimension dim) {

		double length = velocity.getNorm();
		Vector neutralForce = getNeutralForce();
		changeDirection();
		nextPosition = position.addVector(velocity.add(getEnvironmentForce(dim)).add(neutralForce));
		
		if (nextPosition.getX() < 0) {
			nextPosition.setPosition(0, nextPosition.getY());
		} else if (nextPosition.getX() > dim.width) {
			nextPosition.setPosition(dim.width, nextPosition.getY());
		}
		
		if (nextPosition.getY() < 0) {
			nextPosition.setPosition(nextPosition.getX(), 0);
		} else if (nextPosition.getY() > dim.height) {
			nextPosition.setPosition(nextPosition.getX(), dim.height);
		}
		
		velocity = velocity.toUnitVector().multiply(length);
		if (EcoWorld.worldGrid.updatePosition(this, gridPosition, nextPosition)) {
			gridPosition = nextPosition;
		}
	}

	//Randomly changes direction
	private void changeDirection() {
		int dir = (int) (Math.random() * 2);
		switch (dir) {
		case 0:
			velocity = velocity.rotate(0.1);
			break;
		case 1:
			velocity = velocity.rotate(-0.1);
			break;
		}
	}
	
	private Vector getForceFromAgents(List<IAgent> agents) {
		Vector neutralForce = new Vector();
		for (int j = 0; j < agents.size(); j++) {
			if (this != agents.get(j)) {
				Position pos = agents.get(j).getPosition();
				double distance = position.getDistance(pos);
				if (distance <= visionRange && distance != 0) {
					Vector v = new Vector(position, pos);
					v.multiply(1 / (v.getNorm() * distance * distance));
					neutralForce.add(v);
				}
			}
		}
		if (neutralForce.getNorm() != 0) {
			neutralForce.multiply(maxAcceleration / neutralForce.getNorm());
		}
		return neutralForce;
	}
	
	private Vector getNeutralForce() {
		List<List<IAgent>> agents = EcoWorld.worldGrid.get(position, visionRange);
		Vector neutralForce = new Vector();
		for (int i = 0; i < agents.size(); i++) {
			neutralForce.add(getForceFromAgents(agents.get(i)));
		}
		return neutralForce;
	}
}


















