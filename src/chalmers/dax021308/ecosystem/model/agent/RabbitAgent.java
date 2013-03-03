package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Gender;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class RabbitAgent extends AbstractAgent {

	public RabbitAgent(Position position, String name, Color color, int width,
			int height, Vector velocity, Gender gender) {
		super(name, position, color, width, height, velocity, 0, 0, 0);

		super.gender = gender;
	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize) {
		List<IAgent> kids = new LinkedList<IAgent>();
		if (Math.random() < 0.0001 && gender == Gender.FEMALE) {
			if (Math.random() > 0.5) {
				kids.add(new RabbitAgent(new Position(position), name, Color.black, width, height, new Vector(velocity), Gender.MALE));
			} else {
				kids.add(new RabbitAgent(new Position(position), name, Color.lightGray, width, height, new Vector(velocity), Gender.FEMALE));
			}
		}
		return kids;
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral, Dimension dim) {

		Position oldPosition = position;
		changeDirection();
		double length = velocity.getNorm();
		position.addVector(velocity.add(getEnvironmentForce(dim)));
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
