package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * @author Henrik Its purpose is to hunt down Agentsof lower trophic level in a
 *         simple way
 */
public class WolfAgent extends AbstractAgent {

	private boolean hungry = true;
	private boolean willFocusPreys = true;
	private static final int MAX_ENERGY = 800;
	private static final int MAX_LIFE_LENGTH = 3000;
	private static final double REPRODUCTION_RATE = 0.25;
	private static final int DIGESTION_TIME = 50;
	private int digesting = 0;

	public WolfAgent(String name, Position position, Color color, int width,
			int height, Vector velocity, double maxSpeed,
			double maxAcceleration, double visionRange, boolean groupBehaviour) {
		super(name, position, color, width, height, velocity, maxSpeed,
				visionRange, maxAcceleration);
		this.energy = MAX_ENERGY;
		this.groupBehaviour = groupBehaviour;
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral,
			Dimension gridDimension, IShape shape, List<IObstacle> obstacles) {
		if (digesting > 0) {
			digesting--;
		} else {
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
			 * acceleration force. If the acceleration exceeds maximum
			 * acceleration --> scale it to maxAcceleration, but keep the
			 * correct direction of the acceleration.
			 */

			Vector acceleration = preyForce.multiply(10)
					.add(mutualInteractionForce).add(forwardThrust)
					.add(arrayalForce);
			double accelerationNorm = acceleration.getNorm();
			if (accelerationNorm > maxAcceleration) {
				acceleration.multiply(maxAcceleration / accelerationNorm);
			}

			acceleration.add(environmentForce).add(obstacleForce);

			/*
			 * The new velocity is then just: v(t+dt) = (v(t)+a(t+1)*dt)*decay,
			 * where dt = 1 in this case. There is a decay that says if they are
			 * not affected by any force, they will eventually stop. If speed
			 * exceeds maxSpeed --> scale it to maxSpeed, but keep the correct
			 * direction.
			 */
			Vector newVelocity = Vector.addVectors(this.getVelocity(),
					acceleration);
			double speed = newVelocity.getNorm();
			if (speed > maxSpeed) {
				newVelocity.multiply(maxSpeed / speed);
			}

			this.setVelocity(newVelocity);
			/* Reusing the same position object, for less heap allocations. */
			if (reUsedPosition == null) {
				nextPosition = Position.positionPlusVector(position, velocity);
			} else {
				nextPosition = reUsedPosition.setPosition(position.getX()
						+ velocity.x, position.getY() + velocity.y);
			}
		}
	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize,
			List<IObstacle> obstacles, IShape shape, Dimension gridDimension) {
		if (hungry)
			return null;
		else {
			hungry = true;
			List<IAgent> spawn = new ArrayList<IAgent>();
			if (Math.random() < REPRODUCTION_RATE) {
				Position pos;
				do {
					double xSign = Math.signum(-1 + 2 * Math.random());
					double ySign = Math.signum(-1 + 2 * Math.random());
					double newX = this.getPosition().getX() + xSign
							* (0.001 + 0.001 * Math.random());
					double newY = this.getPosition().getY() + ySign
							* (0.001 + 0.001 * Math.random());
					pos = new Position(newX, newY);
				} while (!shape.isInside(gridDimension, pos));
				IAgent child = new WolfAgent(name, pos, color, width, height,
						new Vector(velocity), maxSpeed, maxAcceleration,
						visionRange, groupBehaviour);
				spawn.add(child);
			}
			return spawn;
		}
	}

	/**
	 * @author Sebastian/Henrik
	 */
	private Vector getPreyForce() {
		if (willFocusPreys && focusedPrey != null && focusedPrey.isAlive()) {
			Position p = focusedPrey.getPosition();
			double distance = getPosition().getDistance(p);
			if (distance <= EATING_RANGE) {
				if (focusedPrey.tryConsumeAgent()) {
					focusedPrey = null;
					hungry = false;
					energy = MAX_ENERGY;
					digesting = DIGESTION_TIME;
				}
			} else {
				return new Vector(focusedPrey.getPosition(), position);
			}
		}
		Vector preyForce = new Vector(0, 0);
		IAgent closestFocusPrey = null;
		int preySize = preyNeighbours.size();
		for (int i = 0; i < preySize; i++) {
			IAgent a = preyNeighbours.get(i);
			Position p = a.getPosition();
			double distance = getPosition().getDistance(p);
			if (distance <= visionRange) {

				if (distance <= EATING_RANGE) {
					if (a.tryConsumeAgent()) {
						hungry = false;
						energy = MAX_ENERGY;
						digesting = DIGESTION_TIME;
					}
				} else if (willFocusPreys && distance <= FOCUS_RANGE) {
					if (closestFocusPrey != null && a.isAlive()) {
						if (closestFocusPrey.getPosition().getDistance(
								this.position) > a.getPosition().getDistance(
								this.position)) {
							closestFocusPrey = a;
						}
					} else {
						closestFocusPrey = a;
					}
				} else if (closestFocusPrey == null) {
					/*
					 * Create a vector that points towards the prey.
					 */
					Vector newForce = new Vector(p, getPosition());

					/*
					 * Add this vector to the prey force, with proportion to how
					 * close the prey is. Closer preys will affect the force
					 * more than those far away.
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

		if (willFocusPreys && closestFocusPrey != null) {
			focusedPrey = closestFocusPrey;
			return new Vector(focusedPrey.getPosition(), position);
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
		if (energy == 0 || lifeLength > MAX_LIFE_LENGTH)
			isAlive = false;
	}
}
