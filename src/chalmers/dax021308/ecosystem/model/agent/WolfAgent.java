package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.chromosome.IGenome;
import chalmers.dax021308.ecosystem.model.chromosome.WolfGenes;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.ForceCalculator;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;
import chalmers.dax021308.ecosystem.model.util.shape.IShape;

/**
 * Hunts other agents at lower trophic levels.
 * @author Henrik 
 */
public class WolfAgent extends AbstractAgent {

	private boolean hungry = true;
	private boolean willFocusPreys = true;
	private static final int MAX_ENERGY = 1200;
	private static final int MAX_LIFE_LENGTH = Integer.MAX_VALUE;
	private static final double REPRODUCTION_RATE = 0.0005;
	private static final int DIGESTION_TIME = 50;
	private static final int PREY_FORCE_WEIGHT = 10;
	private static final int SPRINT_INTERVAL = 20;
	private static final int MAX_SPRINT_DURATION = 5;
	private static final int SPRINT_BOOST = 3;
	private static final int EXHAUSTED_DURATION = 5;
	private static final double EXHAUST_FACTOR = 0.4;
	private int digesting = 0;
	private IGenome<WolfGenes> genome;
	private int preyForceFactor = 0;
	private boolean sprinting = false;
	private int sprintIntervalTimer = SPRINT_INTERVAL;
	private int sprintDurationTimer = MAX_SPRINT_DURATION;
	private int exhaustedTimer = EXHAUSTED_DURATION;
	private boolean exhausted = false;
	

	public WolfAgent(String name, Position position, Color color, int width,
			int height, Vector velocity, double maxSpeed,
			double maxAcceleration, double visionRange, boolean groupBehaviour,
			IGenome<WolfGenes> genome) {
		
		super(name, position, color, width, height, velocity, maxSpeed,
				visionRange, maxAcceleration);
		this.energy = MAX_ENERGY;
		this.genome = genome;
		this.groupBehaviour = this.genome.isGeneSet(WolfGenes.GROUPING);
//		this.groupBehaviour = groupBehaviour;
//		this.groupBehaviour = false;
		if(this.groupBehaviour){
			this.color = Color.RED;
		} else {
			this.color = Color.ORANGE;
		}
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral,
			Dimension gridDimension, IShape shape, List<IObstacle> obstacles) {
		if (digesting > 0) {
			digesting--;
		} else {
			updateNeighbourList(neutral, preys, predators);
			Vector preyForce = ForceCalculator.getPreyForce(willFocusPreys, focusedPrey, this,
					preyNeighbours, visionRange, maxAcceleration);
			Vector mutualInteractionForce = new Vector();
			Vector forwardThrust = new Vector();
			Vector arrayalForce = new Vector();
			if (groupBehaviour) {
				mutualInteractionForce = ForceCalculator.mutualInteractionForce(
						neutralNeighbours, this);
				forwardThrust = ForceCalculator.forwardThrust(velocity);
				arrayalForce = ForceCalculator.arrayalForce(velocity, neutralNeighbours,
						this);
			}
			Vector environmentForce = ForceCalculator.getEnvironmentForce(gridDimension, shape,
					position);
			Vector obstacleForce = ForceCalculator.getObstacleForce(obstacles, position);

			/*
			 * Sum the forces from walls, predators and neutral to form the
			 * acceleration force. If the acceleration exceeds maximum
			 * acceleration --> scale it to maxAcceleration, but keep the
			 * correct direction of the acceleration.
			 */

			preyForceFactor = hungry ? PREY_FORCE_WEIGHT : 0;
			
			Vector acceleration = preyForce.multiply(preyForceFactor)
					.add(mutualInteractionForce)
					.add(forwardThrust)
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

			if (sprinting && sprintDurationTimer > 0) {
				newVelocity.multiply(SPRINT_BOOST);
				sprintDurationTimer--;
			} else if (sprintDurationTimer == 0) {
				sprinting = false;
				sprintDurationTimer = MAX_SPRINT_DURATION;
				sprintIntervalTimer = SPRINT_INTERVAL;
				exhausted = true;
			}
			
			if (exhausted && exhaustedTimer > 0) {
				newVelocity.multiply(EXHAUST_FACTOR);
				exhaustedTimer--;
			} else if (exhaustedTimer == 0) {
				exhaustedTimer = EXHAUSTED_DURATION;
				exhausted = false;
			}
			
			this.setVelocity(newVelocity);

			/* Reusing the same position object, for less heap allocations. */
			// if (reUsedPosition == null) {
			nextPosition = Position.positionPlusVector(position, velocity);
			// } else {
			// nextPosition.setPosition(reUsedPosition.setPosition(position.getX()
			// + velocity.x, position.getY() + velocity.y);
			// }
		}
	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize,
			List<IObstacle> obstacles, IShape shape, Dimension gridDimension) {
		if (hungry)
			return null;
		else {
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
						visionRange, groupBehaviour, genome.onlyMutate());
				spawn.add(child);
			}
			return spawn;
		}
	}

	// This also decreases the wolfs energy.
	@Override
	public void updatePosition() {
		super.updatePosition();
		this.energy--;
		if (energy == 0 || lifeLength > MAX_LIFE_LENGTH) {
			isAlive = false;
		} else if (energy <= MAX_ENERGY * 0.6) {
			hungry = true;
		}
		
		if (!exhausted) { //Should also depend on whether a prey has been focused.
			if (sprintIntervalTimer-- == 0) {
				sprinting = true;
			}
		}
	}

	@Override
	public void eat() {
		hungry = false;
		energy = MAX_ENERGY;
		digesting = DIGESTION_TIME;
	}

	public boolean isAGroupingWolf() {
		return groupBehaviour;
	}

}
