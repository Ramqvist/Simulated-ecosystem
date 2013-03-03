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
		changeDirection();
		double length = velocity.getNorm();
		nextPosition = position.addVector(velocity.add(getEnvironmentForce(dim)));
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
}
