package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.IObstacle;
import chalmers.dax021308.ecosystem.model.environment.WorldGrid;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * A basic implementation of the IAgent interface.
 * 
 * @author Albin
 */
public class DeerAgentGrid extends AbstractAgent {

	private static final int MAX_ENERGY = 1100;
	private boolean hungry = true;
	private static final double REPRODUCTION_RATE = 0.15;
	private WorldGrid wg;
	
	public DeerAgentGrid(String name, Position p, Color c, int width,
			int height, Vector velocity, double maxSpeed,
			double maxAcceleration, double visionRange, boolean groupBehaviour) {
		
		super(name, p, c, width, height, velocity, maxSpeed, visionRange,
				maxAcceleration);

		this.energy = MAX_ENERGY;
		this.groupBehaviour = groupBehaviour;
		wg = WorldGrid.getInstance();

	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize) {
		if (hungry)
			return null;
		else {
			List<IAgent> spawn = new ArrayList<IAgent>();
			if (Math.random() < REPRODUCTION_RATE) {
				hungry = true;
				double xSign = Math.signum(-1+2*Math.random());
				double ySign = Math.signum(-1+2*Math.random());
				double newX = this.getPosition().getX()+xSign*(1+5*Math.random());
				double newY = this.getPosition().getY()+ySign*(1+5*Math.random());
				Position pos = new Position(newX,newY);
				IAgent child = new DeerAgentGrid(name, pos, color, width, height, new Vector(velocity),
						maxSpeed, maxAcceleration, visionRange, groupBehaviour);
				spawn.add(child);
			} else {
				hungry = true;
			}
			return spawn;
		}
	}

	/**
	 * @author Sebbe
	 * Calculates the next position of the agent depending on
	 * the forces that affects it. Note: The next position is 
	 * not set until updatePosition() is called.
	 */
	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral,
			Dimension gridDimension, IShape shape, List<IObstacle> obstacles) {
		updateNeighbourList(neutral, preys, predators);
		
		Vector predatorForce = getPredatorForce(predators);
		Vector mutualInteractionForce = new Vector();
		Vector forwardThrust = new Vector();
		Vector arrayalForce = new Vector();
		if(groupBehaviour) {
			mutualInteractionForce = mutualInteractionForce();
			forwardThrust = forwardThrust();
			arrayalForce = arrayalForce();
		}
		
		Vector environmentForce = getEnvironmentForce(gridDimension, shape);
		Vector preyForce = getPreyForce(preys);

		/*
		 * Sum the forces from walls, predators and neutral to form the
		 * acceleration force. If the acceleration exceeds maximum acceleration
		 * --> scale it to maxAcceleration, but keep the correct direction of
		 * the acceleration.
		 */
		Vector randomForce = randomForce();
		Vector acceleration = environmentForce.multiply(100)
				.add(predatorForce.multiply(10))
				.add(mutualInteractionForce)
				.add(forwardThrust)
				.add(arrayalForce)
				.add(preyForce.multiply(8))
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
		Vector newVelocity = Vector.addVectors(this.getVelocity(), acceleration);
		newVelocity.multiply(VELOCITY_DECAY);
		double speed = newVelocity.getNorm();
		if (speed > maxSpeed) {
			newVelocity.multiply(maxSpeed / speed);
		}

		this.setVelocity(newVelocity);
		nextPosition = Position.positionPlusVector(position, velocity);
	}

	/**
	 * @author Henrik
	 * @param preys
	 *            The list of preys to eat
	 * @return returns The force the preys attracts the agent with
	 */
	private Vector getPreyForce(List<IPopulation> preys) {
		Vector preyForce = new Vector(0, 0);
		for (IPopulation pop : preys) {
			List<IAgent> agents = pop.getAgents();
			for (int i = 0; i < pop.getAgents().size(); i++) {
				IAgent a = agents.get(i);
				Position p = a.getPosition();
				double distance = getPosition().getDistance(p);
				if (distance <= visionRange) {
					if (distance <= INTERACTION_RANGE-5) {
						// Food found, let's eat it and make some reproducing
						// possible
						if(a.tryConsumeAgent()) {
							pop.addToRemoveList(a);
							hungry = false;
							this.energy = MAX_ENERGY;
						}
					} else {
						Vector newForce = new Vector(p, getPosition());
						double norm = newForce.getNorm();
						preyForce.add(newForce.multiply(1 / (norm * distance)));
					}
				}
			}
		}
		return preyForce;
	}

	/**
	 * @author Sebbe "Predator Force" is defined as the sum of the vectors
	 *         pointing away from all the predators in vision, weighted by the
	 *         inverse of the distance to the predators, then normalized to have
	 *         unit norm. Can be interpreted as the average sum of forces that
	 *         the agent feels, weighted by how close the source of the force
	 *         is.
	 */
	private Vector getPredatorForce(List<IPopulation> predators) {
		Vector predatorForce = new Vector(0, 0);
		int nVisiblePredators = 0;
		for (IPopulation pop : predators) {
			for (IAgent predator : pop.getAgents()) {
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
		/*
		 * Worldgrid has to be updated before super.update(), otherwise:
		 * this.position.equals(this.nextPosition) == true
		 */
		wg.updatePosition(this, this.position, this.nextPosition);
		super.updatePosition();
		this.energy--;
	}

}
