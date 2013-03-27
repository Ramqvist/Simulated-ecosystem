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
 * A basic implementation of the IAgent interface.
 * @author Albin
 */
public class DeerAgent extends AbstractAgent {

	private static final int MAX_ENERGY = 1100;
	private boolean hungry = true;
	private static final double REPRODUCTION_RATE = 0.15;
	private boolean willFocusPreys = true;

	public DeerAgent(String name, Position p, Color c, int width, int height,
			Vector velocity, double maxSpeed, double maxAcceleration,
			double visionRange, boolean groupBehaviour) {

		super(name, p, c, width, height, velocity, maxSpeed, visionRange,
				maxAcceleration);

		this.energy = MAX_ENERGY;
		this.groupBehaviour = groupBehaviour;

	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize, List<IObstacle> obstacles) {
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
				IAgent child = new DeerAgent(name, pos, color, width, height,
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
	 * Calculates the next position of the agent depending on the
	 * forces that affects it. Note: The next position is not set until
	 * updatePosition() is called.
	 * @author Sebbe
	 */
	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral,
			Dimension gridDimension, IShape shape, List<IObstacle> obstacles) {

		updateNeighbourList(neutral, preys, predators);
		Vector predatorForce = getPredatorForce();
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
		Vector preyForce = getPreyForce();

		/*
		 * Sum the forces from walls, predators and neutral to form the
		 * acceleration force. If the acceleration exceeds maximum acceleration
		 * --> scale it to maxAcceleration, but keep the correct direction of
		 * the acceleration.
		 */
		Vector acceleration = predatorForce.multiply(5)
				.add(mutualInteractionForce)
				.add(forwardThrust)
				.add(arrayalForce)
				.add(preyForce.multiply(5 * (1 - energy / MAX_ENERGY)));
		double accelerationNorm = acceleration.getNorm();
		if (accelerationNorm > maxAcceleration) {
			acceleration.multiply(maxAcceleration / accelerationNorm);
		}

		acceleration.add(environmentForce).add(obstacleForce);
		
		/*
		 * The new velocity is then just: v(t+dt) = (v(t)+a(t+1)*dt)*decay,
		 * where dt = 1 in this case. There is a decay that says if they are not
		 * affected by any force, they will eventually stop. If speed exceeds
		 * maxSpeed --> scale it to maxSpeed, but keep the correct direction.
		 */
		Vector newVelocity = Vector
				.addVectors(this.getVelocity(), acceleration);
		newVelocity.multiply(VELOCITY_DECAY);
		double speed = newVelocity.getNorm();
		if (speed > maxSpeed) {
			newVelocity.multiply(maxSpeed / speed);
		}

		this.setVelocity(newVelocity);
		
		/* Reusing the same position object, for less heap allocations. */
		if(reUsedPosition == null) {
			nextPosition = Position.positionPlusVector(position, velocity);
		} else {
			nextPosition = reUsedPosition.setPosition(position.getX() + velocity.x, position.getY() + velocity.y);
		}
	}

	/**
	 * @return returns The force the preys attracts the agent with
	 * @author Henrik
	 */
	private Vector getPreyForce() {
		/*
		 * "Prey Force" is AT THE MOMENT defined as the sum of the vectors
		 * pointing away from all the preys in vision, weighted by the inverse
		 * of the distance to the preys, then normalized to have unit norm. Can
		 * be interpreted as the average sum of forces that the agent feels,
		 * weighted by how close the source of the force is.
		 */
		if(willFocusPreys && focusedPrey != null && focusedPrey.isAlive()) {
			Position p = focusedPrey.getPosition();
			double distance = getPosition().getDistance(p);
			if (distance <= EATING_RANGE) {
				if (focusedPrey.tryConsumeAgent()) {
					focusedPrey = null;
					hungry = false;
					energy = MAX_ENERGY;
				}
			} else {
				return new Vector(focusedPrey.getPosition(), getPosition());
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
					}
				} else if(willFocusPreys && distance <= FOCUS_RANGE){
					if(closestFocusPrey != null) {
						if(closestFocusPrey.getPosition().getDistance(this.position) > 
							a.getPosition().getDistance(this.position)) {
							closestFocusPrey = a;
						}
					} else {
						closestFocusPrey = a;
					}
				} else if (closestFocusPrey == null){
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
		
		if(willFocusPreys && closestFocusPrey != null){
			focusedPrey = closestFocusPrey;
			return new Vector(focusedPrey.getPosition(), getPosition());
		} 
		
		return preyForce;
	}

	/**
	 * "Predator Force" is defined as the sum of the vectors
	 * pointing away from all the predators in vision, weighted by the
	 * inverse of the distance to the predators, then normalized to have
	 * unit norm. Can be interpreted as the average sum of forces that
	 * the agent feels, weighted by how close the source of the force is.
	 * @author Sebbe
	 */
	private Vector getPredatorForce() {
		Vector predatorForce = new Vector(0, 0);
		int nVisiblePredators = 0;
		int predSize = predNeighbours.size();
		IAgent predator;
		for (int i = 0; i < predSize; i++) {
			predator = predNeighbours.get(i);
			Position p = predator.getPosition();
			double distance = getPosition().getDistance(p);
			if (distance <= visionRange) { // If predator is in vision range
											// for prey
				/*
				 * Create a vector that points away from the predator.
				 */
				Vector newForce = new Vector(this.getPosition(), p);

				/*
				 * Add this vector to the predator force, with proportion to
				 * how close the predator is. Closer predators will affect
				 * the force more than those far away.
				 */
				double norm = newForce.getNorm();
				predatorForce.add(newForce.multiply(1 / (norm * distance)));
				nVisiblePredators++;
			}
		}

		if (nVisiblePredators == 0) { // No predators near --> Be unaffected
			predatorForce.setVector(0, 0);
		} else { // Else set the force depending on visible predators and
					// normalize it to maxAcceleration.
			double norm = predatorForce.getNorm();
			predatorForce.multiply(maxAcceleration / norm);
		}

		return predatorForce;
	}

	/**
	 * This also decreases the deer's energy.
	 */
	@Override
	public void updatePosition() {
		super.updatePosition();
		this.energy--;
	}
}
