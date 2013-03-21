package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * 
 * @author Henrik Its purpose is simply to hunt down the SimpleAgent (or any
 *         other agent) in a simple way
 */
public class SimplePredatorAgent extends AbstractAgent {

	public SimplePredatorAgent(String name, Position p, Color c, int width,
			int height, Vector velocity, double maxSpeed,
			double maxAcceleration, double visionRange) {
		super(name, p, c, width, height, velocity, maxSpeed, visionRange, maxAcceleration);
	}

	@Override
	public List<IAgent> reproduce(IAgent agent, int populationSize, List<IObstacle> obstacles) {
		return new LinkedList<IAgent>();
	}

	/**
	 * @author Sebastian/Henrik
	 */
	private Vector getPreyForce() {
		/* 
		 * "Prey Force" is AT THE MOMENT defined as the sum of the vectors pointing away from 
		 * all the preys in vision, weighted by the inverse of the distance to the preys, 
		 * then normalized to have unit norm. 
		 * Can be interpreted as the average sum of forces that the agent feels, weighted
		 * by how close the source of the force is.
		 */
		Vector preyForce = new Vector(0, 0);
		int nVisiblePreys = 0;
		for (IAgent a : preyNeighbours) {
			Position p = a.getPosition();
			double distance = getPosition().getDistance(p);
			if (distance <= visionRange) {
				/*
				 * Create a vector that points towards the prey.
				 */
				Vector newForce = new Vector(p, getPosition());
				
				/*
				 * Add this vector to the prey force, with proportion to how close the prey is.
				 * Closer preys will affect the force more than those far away. 
				 */
				double norm = newForce.getNorm();
				preyForce.add(newForce.multiply(1/(norm*distance)));
				nVisiblePreys++;
			 }
		}

		if (nVisiblePreys == 0) { //No preys near --> Be unaffected
			preyForce.setVector(0,0);
		} else { //Else set the force depending on visible preys and normalize it to maxAcceleration.
			double norm = preyForce.getNorm();
			preyForce.multiply(maxAcceleration/norm);
		}
		return preyForce;
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral, Dimension dim, IShape shape, List<IObstacle> obstacles) {
		
		updateNeighbourList(neutral, preys, predators);
		
		Vector preyForce = getPreyForce();
		Vector separationForce = mutualInteractionForce();
		//Vector separationForce = new Vector();
		Vector environmentForce = getEnvironmentForce(dim, shape);
		
		/*
		 * Sum the forces from walls, predators and neutral to form the acceleration force.
		 * If the acceleration exceeds maximum acceleration --> scale it to maxAcceleration,
		 * but keep the correct direction of the acceleration.
		 */
		Vector acceleration = environmentForce.multiply(100).add(preyForce).add(separationForce.multiply(10));
		double accelerationNorm = acceleration.getNorm();
		if(accelerationNorm > maxAcceleration){
			acceleration.multiply(maxAcceleration/accelerationNorm); 
		}
		
		/*
		 * The new velocity is then just:
		 * v(t+dt) = (v(t)+a(t+1)*dt)*decay, where dt = 1 in this case.
		 * There is a decay that says if they are not affected by any force,
		 * they will eventually stop.
		 * If speed exceeds maxSpeed --> scale it to maxSpeed, but keep the correct direction.
		 */
		Vector newVelocity = this.getVelocity().add(acceleration).multiply(VELOCITY_DECAY);
		double speed = newVelocity.getNorm();
		if(speed > maxSpeed){
			newVelocity.multiply(maxSpeed/speed); 
		}
		
		this.setVelocity(newVelocity);
		nextPosition = Position.positionPlusVector(position,velocity);
	}
	
}
