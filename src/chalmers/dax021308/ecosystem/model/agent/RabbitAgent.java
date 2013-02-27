package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Gender;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class RabbitAgent extends AbstractAgent {

	private Vector oldVelocity;
	private int delay;
	private IAgent selectedFemale = null;

	public RabbitAgent(Position position, String name, Color color, int width,
			int height, Vector velocity, Gender gender) {
		super(name, position, color, width, height, velocity, 0, 0, 0);

		super.gender = gender;
		oldVelocity = velocity;
		delay = 0;
	}

	@Override
	public List<IAgent> reproduce(IAgent agent) {
		List<IAgent> kids = new LinkedList<IAgent>();
		if (Math.random() < 0.001 && gender == Gender.FEMALE) {
			if (Math.random() > 0.5) {
				kids.add(new RabbitAgent(position, name, Color.black, width, height, velocity, Gender.MALE));
			} else {
				kids.add(new RabbitAgent(position, name, Color.red, width, height, velocity, gender));
			}
		}
		return kids;
//		if (selectedFemale != null
//				&& position.getDistance(selectedFemale.getPosition()) < 10) {
//			for (int i = 0; i < (int) (Math.random() * 5); i++) {
//				if (i % 2 == 0) {
//					kids.add(new RabbitAgent(position, name, color, width,
//							height, velocity, gender));
//				} else {
//					kids.add(new RabbitAgent(selectedFemale.getPosition(),
//							selectedFemale.getName(),
//							selectedFemale.getColor(), selectedFemale
//									.getWidth(), selectedFemale.getHeight(),
//							selectedFemale.getVelocity(), selectedFemale
//									.getGender()));
//				}
//			}
//			oldVelocity = velocity;
//			velocity.setX(0);
//			velocity.setY(0);
//			delay = 20;
//			selectedFemale = null;
//		}
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral, Dimension dim) {

		if (gender == Gender.MALE) {
			if (delay == 0) {
				velocity = oldVelocity;
				// Locate the closest female
				double minDistance = Double.MAX_VALUE;

				for (IPopulation p : preys) {
					for (IAgent a : p.getAgents()) {
						if (a.getGender() == Gender.FEMALE) {
							if (position.getDistance(a.getPosition()) < minDistance) {
								minDistance = position.getDistance(a
										.getPosition());
								selectedFemale = a;
							}
						}
					}
				}

				if (selectedFemale != null) {
					Vector unitVector = new Vector(
							selectedFemale.getPosition(), position)
							.toUnitVector();
					velocity = unitVector.multiply(velocity.getNorm());
				}
				position.addVector(velocity);
			} else {
				delay--;
			}
		} else if (gender == Gender.FEMALE) {
			changeDirection();
			position.addVector(velocity.add(getEnvironmentForce(dim)));
		}
	}

	private void changeDirection() {
		// Calculate random direction
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
