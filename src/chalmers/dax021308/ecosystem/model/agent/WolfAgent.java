package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.SurroundingsSettings;
import chalmers.dax021308.ecosystem.model.genetics.IGenes;
import chalmers.dax021308.ecosystem.model.genetics.IGenome;
import chalmers.dax021308.ecosystem.model.genetics.WolfGenes;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.ForceCalculator;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * @author Henrik Its purpose is to hunt down Agents of lower trophic level in a
 *         simple way
 */
public class WolfAgent extends AbstractAgent {

	private boolean hungry = true;
	private boolean willFocusPreys = true;
	private static final int MAX_ENERGY = 1200;
	private static final int MAX_LIFE_LENGTH = Integer.MAX_VALUE;
	private static final double REPRODUCTION_RATE = 0.10;
	private static final int DIGESTION_TIME = 50;
	private int digesting = 0;
	private IGenome<IGenes> genome;
	
	public WolfAgent(String name, Position position, Color color, int width,
			int height, Vector velocity, double maxSpeed,
			double maxAcceleration, double visionRange, boolean groupBehaviour,
			IGenome<IGenes> genome) {
		
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
			SurroundingsSettings surroundings) {
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
				arrayalForce = ForceCalculator.arrayalForce(neutralNeighbours, this);
			}
			Vector environmentForce = ForceCalculator.getEnvironmentForce(surroundings.getGridDimension(), surroundings.getWorldShape(),
					position);
			Vector obstacleForce = ForceCalculator.getObstacleForce(surroundings.getObstacles(), position);

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
			SurroundingsSettings surroundings) {
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
				} while (!surroundings.getWorldShape().isInside(surroundings.getGridDimension(), pos));
				IAgent child = new WolfAgent(name, pos, color, width, height,
						new Vector(velocity), maxSpeed, maxAcceleration,
						visionRange, groupBehaviour, genome.onlyMutate());
				spawn.add(child);
			}
			return spawn;
		}
	}

	// This also decreases the deer's energy.
	@Override
	public void updatePosition() {
		super.updatePosition();
		this.energy--;
		if (energy == 0 || lifeLength > MAX_LIFE_LENGTH)
			isAlive = false;
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
