package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.IObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * 
 * @author Henrik Its purpose is simply to hunt down the SimpleAgent (or any
 *         other agent) in a simple way
 */
public class WolfAgent extends AbstractAgent {

	private boolean hungry = true;
	private static final int MAX_ENERGY = 1300;
	private static final double REPRODUCTION_RATE = 0.15;

	public WolfAgent(String name, Position p, Color c, int width, int height,
			Vector velocity, double maxSpeed, double maxAcceleration,
			double visionRange, boolean groupBehaviour) {
		super(name, p, c, width, height, velocity, maxSpeed, visionRange,
				maxAcceleration);

		this.energy = MAX_ENERGY;
		this.groupBehaviour = groupBehaviour;
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral,
			Dimension gridDimension, IShape shape, List<IObstacle> obstacles) {
		
		updateNeighbourList(neutral, preys, predators);
		
		Vector preyForce = getPreyForce();
		Vector mutualInteractionForce = new Vector();
		Vector forwardThrust = new Vector();
		Vector arrayalForce = new Vector();
		if (groupBehaviour) {
			mutualInteractionForce = mutualInteractionForce();
			forwardThrust = forwardThrust();
			arrayalForce = arrayalForce();
		}
		Vector environmentForce = getEnvironmentForce(gridDimension, shape);
		Vector obstacleForce = getObstacleForce(obstacles);

		/*
		 * Sum the forces from walls, predators and neutral to form the
		 * acceleration force. If the acceleration exceeds maximum acceleration
		 * --> scale it to maxAcceleration, but keep the correct direction of
		 * the acceleration.
		 */

		Vector randomForce = randomForce();
		Vector acceleration = environmentForce
				.add(obstacleForce)
				.add(preyForce.multiply(10))
				.add(mutualInteractionForce)
				.add(forwardThrust)
				.add(arrayalForce)
				.add(randomForce);
		double accelerationNorm = acceleration.getNorm();
		if (accelerationNorm > maxAcceleration) {
			acceleration.multiply(maxAcceleration / accelerationNorm);
		}

		/*
		 * The new velocity is then just: v(t+dt) = (v(t)+a(t+1)*dt)*decay,
		 * where dt = 1 in this case. There is a decay that says if they are not
		 * affected by any force, they will eventually stop. If speed exceeds
		 * maxSpeed --> scale it to maxSpeed, but keep the correct direction.
		 */
		Vector newVelocity = Vector
				.addVectors(this.getVelocity(), acceleration);
		double speed = newVelocity.getNorm();
		if (speed > maxSpeed) {
			newVelocity.multiply(maxSpeed / speed);
		}

		this.setVelocity(newVelocity);
		nextPosition = Position.positionPlusVector(position, velocity);
	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize) {
		if (hungry)
			return null;
		else {
			List<IAgent> spawn = new ArrayList<IAgent>();
			if (Math.random() < REPRODUCTION_RATE) {
				hungry = true;
				double xSign = Math.signum(-1 + 2 * Math.random());
				double ySign = Math.signum(-1 + 2 * Math.random());
				double newX = this.getPosition().getX() + xSign
						* (0.001 + 0.001 * Math.random());
				double newY = this.getPosition().getY() + ySign
						* (0.001 + 0.001 * Math.random());
				Position pos = new Position(newX, newY);
				IAgent child = new WolfAgent(name, pos, color, width, height,
						new Vector(velocity), maxSpeed, maxAcceleration,
						visionRange, groupBehaviour);
				spawn.add(child);
			} else {
				hungry = true;
			}
			return spawn;
		}
	}

	/**
	 * @author Sebastian/Henrik
	 */
	private Vector getPreyForce() {
		/*
		 * "Prey Force" is AT THE MOMENT defined as the sum of the vectors
		 * pointing away from all the preys in vision, weighted by the inverse
		 * of the distance to the preys, then normalized to have unit norm. Can
		 * be interpreted as the average sum of forces that the agent feels,
		 * weighted by how close the source of the force is.
		 */
		Vector preyForce = new Vector(0, 0);
		
		int preySize = preyNeighbours.size();
		for (int i = 0; i < preySize; i++) {
			IAgent a = preyNeighbours.get(i);
			Position p = a.getPosition();
			double distance = getPosition().getDistance(p);
			if (distance <= visionRange) {
				if (distance <= INTERACTION_RANGE - 5) {
					if (a.consumeAgent()) {
						hungry = false;
						energy = MAX_ENERGY;
					}
				} else {
					/*
					 * Create a vector that points towards the prey.
					 */
					Vector newForce = new Vector(p, getPosition());

					/*
					 * Add this vector to the prey force, with proportion to
					 * how close the prey is. Closer preys will affect the
					 * force more than those far away.
					 */
					double norm = newForce.getNorm();
					preyForce.add(newForce.multiply(1 / (norm * distance)));
				}
			}
		}

		double norm = preyForce.getNorm();
		if (norm != 0) { 
			preyForce.multiply(maxAcceleration / norm);
		}
		
		return preyForce;
	}

	/**
	 * This also decreases the wolfs energy.
	 */
	@Override
	public void updatePosition() {
		super.updatePosition();
		this.energy--;
	}
}
