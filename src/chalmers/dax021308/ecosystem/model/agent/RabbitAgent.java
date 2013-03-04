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

	public RabbitAgent(Position position, String name, Color color, 
			int width, int height, Vector velocity, Gender gender,
			double maxSpeed, double visionRange, double maxAcceleration) {
		super(name, position, color, width, height, velocity, maxSpeed, visionRange, maxAcceleration);

		super.gender = gender;
	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize) {
		List<IAgent> kids = new ArrayList<IAgent>();
		if (Math.random() < 0.0001) {
			kids.add(new RabbitAgent(new Position(position), name, color, width, height, new Vector(velocity), null, maxSpeed, visionRange, maxAcceleration));
		}
		return kids;
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral, Dimension dim) {

		Position oldPosition = position;
		double length = velocity.getNorm();
//		Vector neutralForce = getNeutralForce1(neutral);
		Vector neutralForce = getNeutralForce2(dim);
		changeDirection();
		nextPosition = position.addVector(velocity.add(getEnvironmentForce(dim)).add(neutralForce));
		velocity = velocity.toUnitVector().multiply(length);
		EcoWorld.worldGrid.updatePosition(this, oldPosition, position);
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
	
	//Without grid
	private Vector getNeutralForce1(List<IPopulation> neutral) {
		Vector neutralForce = new Vector();
		for (int i = 0; i < neutral.size(); i++) {
			List<IAgent> agents = neutral.get(i).getAgents();
			neutralForce.add(getForceFromAgents(agents));
		}
		return neutralForce;
	}
	
	private Vector getForceFromAgents(List<IAgent> agents) {
		Vector neutralForce = new Vector();
		for (int j = 0; j < agents.size(); j++) {
			if (this != agents.get(j)) {
				Position pos = agents.get(j).getPosition();
				double distance = position.getDistance(pos);
				if (distance <= visionRange && distance != 0) {
					Vector v = new Vector(position, pos);
					v.multiply(1 / (v.getNorm() * distance));
					neutralForce.add(v);
				}
			}
		}
		if (neutralForce.getNorm() != 0) {
			neutralForce.multiply(maxAcceleration / neutralForce.getNorm());
		}
		return neutralForce;
	}
	
	
	//With grid.
	private Vector getNeutralForce2(Dimension dim) {
		List<Position> positions = new ArrayList<Position>();
		int x1 = (int)(position.getX() - velocity.getNorm());
		int y1 = (int)(position.getY() - velocity.getNorm());
		int x2 = (int)(position.getX() + velocity.getNorm());
		int y2 = (int)(position.getY() + velocity.getNorm());
		int scale = EcoWorld.worldGrid.getScale();
		System.out.println("asdf");
		
		//Add scale??
		for (int x = x1; x <= x2; x+= scale) {
			for (int y = y1; y <= y2; y+= scale) {
				if (x >= 0 && x <= dim.width && y >= 0 && y <= dim.height) {
					Position p = new Position(x, y);
					if (position.getDistance(p) <= visionRange) {
						positions.add(p);
					}
				}
			}
		}
		
		List<List<IAgent>> agents = EcoWorld.worldGrid.get(positions);
		Vector neutralForce = new Vector();
		for (int i = 0; i < agents.size(); i++) {
			neutralForce.add(getForceFromAgents(agents.get(i)));
		}
		
		return neutralForce;
	}
}


















