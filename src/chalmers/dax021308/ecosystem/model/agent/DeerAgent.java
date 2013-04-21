package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.chromosome.DeerGenes;
import chalmers.dax021308.ecosystem.model.chromosome.IGenome;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.ForceCalculator;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;
import chalmers.dax021308.ecosystem.model.util.shape.IShape;

/**
 * A basic implementation of the IAgent interface.
 * 
 * @author Albin
 */
public class DeerAgent extends AbstractAgent {

	private static final int MAX_ENERGY = 1000;
	private static final int MAX_LIFE_LENGTH = Integer.MAX_VALUE;
	private boolean hungry = true;
	private static final double REPRODUCTION_RATE = 0.1;
	private boolean willFocusPreys = false;
	private static final int DIGESTION_TIME = 10;
	private int digesting = 0;
	private double STOTTING_RANGE = 10;
	private double STOTTING_LENGTH = 8;
	private double STOTTING_COOLDOWN = 50;
	private double stottingDuration = STOTTING_LENGTH;
	private double stottingCoolDown = 0;
	private boolean isAStottingDeer = false;
	private boolean isStotting = false;
	private Vector stottingVector = new Vector();
	private boolean alone;
	private IGenome<DeerGenes> genome;

	public DeerAgent(String name, Position p, Color c, int width, int height,
			Vector velocity, double maxSpeed, double maxAcceleration,
			double visionRange, boolean groupBehaviour, IGenome<DeerGenes> genome) {

		super(name, p, c, width, height, velocity, maxSpeed, visionRange,
				maxAcceleration);
		this.genome = genome;
		this.groupBehaviour = this.genome.isGeneSet(DeerGenes.GROUPING);
		if(this.groupBehaviour) {
			this.color = Color.magenta;
		} else {
			this.color = Color.blue;
		}
		this.energy = MAX_ENERGY;
		this.groupBehaviour = groupBehaviour;

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
				IAgent child = new DeerAgent(name, pos, color, width, height,
						new Vector(velocity), maxSpeed, maxAcceleration,
						visionRange, groupBehaviour, genome.onlyMutate());
				
				spawn.add(child);

			}
			return spawn;
		}
	}

	/**
	 * Calculates the next position of the agent depending on the forces that
	 * affects it. Note: The next position is not set until updatePosition() is
	 * called.
	 * 
	 * @author Sebbe
	 */
	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral,
			Dimension gridDimension, IShape shape, List<IObstacle> obstacles) {

		updateNeighbourList(neutral, preys, predators);
		Vector predatorForce = getPredatorForce();
		alone = predatorForce.isNullVector();
		if (digesting > 0 && alone) {
			digesting--;
		} else {
			Vector mutualInteractionForce = new Vector();
			Vector forwardThrust = new Vector();
			Vector arrayalForce = new Vector();
			if (groupBehaviour) {
				mutualInteractionForce = ForceCalculator
						.mutualInteractionForce(neutralNeighbours, this);
				forwardThrust = ForceCalculator.forwardThrust(velocity);
				arrayalForce = ForceCalculator.arrayalForce(velocity,
						neutralNeighbours, this);
			}

			Vector environmentForce = ForceCalculator.getEnvironmentForce(
					gridDimension, shape, position);
			Vector obstacleForce = ForceCalculator.getObstacleForce(obstacles,
					position);

			/*
			 * Sum the forces from walls, predators and neutral to form the
			 * acceleration force. If the acceleration exceeds maximum
			 * acceleration --> scale it to maxAcceleration, but keep the
			 * correct direction of the acceleration.
			 */
			Vector acceleration;
			if (isAStottingDeer && isStotting) {
				acceleration = predatorForce;
			} else {
				acceleration = predatorForce.multiply(5)
						.add(mutualInteractionForce).add(forwardThrust)
						.add(arrayalForce);
				// if (alone) {
				Vector preyForce = ForceCalculator.getPreyForce(willFocusPreys,
						focusedPrey, this, preyNeighbours, visionRange,
						maxAcceleration);
				acceleration.add(preyForce.multiply(5 * (1 - energy
						/ MAX_ENERGY)));
			}
			// }
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
			newVelocity.multiply(VELOCITY_DECAY);
			double speed = newVelocity.getNorm();
			if (speed > maxSpeed) {
				newVelocity.multiply(maxSpeed / speed);
			}
			// if (alone) {
			// newVelocity.multiply(0.9);
			// }
			this.setVelocity(newVelocity);

			/* Reusing the same position object, for less heap allocations. */
			// if (reUsedPosition == null) {
			nextPosition = Position.positionPlusVector(position, velocity);
			// } else {
			// nextPosition.setPosition(reUsedPosition.setPosition(position.getX()
			// + velocity.x, position.getY() + velocity.y));
			// }
		}
	}

	/**
	 * "Predator Force" is defined as the sum of the vectors pointing away from
	 * all the predators in vision, weighted by the inverse of the distance to
	 * the predators, then normalized to have unit norm. Can be interpreted as
	 * the average sum of forces that the agent feels, weighted by how close the
	 * source of the force is.
	 * 
	 * @author Sebbe
	 */
	private Vector getPredatorForce() {
		Vector predatorForce = new Vector();
		if (isAStottingDeer && isStotting) {
			stottingDuration--;
			if (stottingDuration <= 0) {
				isStotting = false;
			}
			return stottingVector;
		} else {
			boolean predatorClose = false;
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

					if (isAStottingDeer && distance <= STOTTING_RANGE) {
						predatorClose = true;
					}

					/*
					 * Add this vector to the predator force, with proportion to
					 * how close the predator is. Closer predators will affect
					 * the force more than those far away.
					 */
					double norm = newForce.getNorm();
					predatorForce.add(newForce.multiply(1 / (norm * distance)));
				}
			}

			double norm = predatorForce.getNorm();
			if (norm > 0) {
				// If there are predators near set the force depending on
				// visible predators and normalize it to maxAcceleration.
				predatorForce.multiply(maxAcceleration / norm);
			}

			if (isAStottingDeer && stottingCoolDown <= 0 && predatorClose) {
				isStotting = true;
				stottingCoolDown = STOTTING_COOLDOWN;
				stottingDuration = STOTTING_LENGTH;
				double newX = 0;
				double newY = 0;
				if (Math.random() < 0.5) {
					newX = 1;
					newY = -predatorForce.getX() / predatorForce.getY();
				} else {
					newY = 1;
					newX = -predatorForce.getY() / predatorForce.getX();
				}
				stottingVector.setVector(newX, newY);
				stottingVector.multiply(predatorForce.getNorm()
						/ stottingVector.getNorm());
				stottingVector.add(predatorForce.multiply(-0.5));
				return stottingVector;
			}

		}
		return predatorForce;
	}

	// This also decreases the deer's energy.
	@Override
	public void updatePosition() {
		super.updatePosition();
		this.energy--;
		stottingCoolDown--;
		if (energy == 0 || lifeLength > MAX_LIFE_LENGTH)
			isAlive = false;
	}

	@Override
	public void eat() {
		hungry = false;
		energy = MAX_ENERGY;
		digesting = DIGESTION_TIME;
	}
	
	public boolean isAStottingDeer(){
		return isAStottingDeer;
	}
	
	public boolean isAGroupingDeer() {
		return groupBehaviour;
	}
}
