package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * 
 * @author Henrik Its purpose is simply to hunt down the SimpleAgent (or any
 *         other agent) in a simple way
 */
public class SimplePreyAgent extends AbstractAgent {

	private double maxSpeed;
	private double visionRange;
	private double maxAcceleration;

	public SimplePreyAgent(String name, Position p, Color c, int width,
			int height, Vector velocity) {
		super(name, p, c, width, height, velocity);
		this.maxSpeed = maxSpeed;
		this.maxAcceleration = maxAcceleration;
		this.visionRange = visionRange;

	}

	@Override
	public void updatePosition(List<IPopulation> predators,
			List<IPopulation> preys, Dimension dim) {
		setNewVelocity(predators, preys, dim);
		getPosition().addVector(getVelocity());
	}

	@Override
	public List<IAgent> reproduce(IAgent agent) {
		// TODO Auto-generated method stub
		System.err.println("ERROR IN REPRODUCE METHOD, METHOD UNDEFINED");
		return null;
	}
	
	/**
	 * @author Sebastian/Henrik 
	 * @param predators
	 * @param preys
	 * @param dim
	 */
	private void setNewVelocity(List<IPopulation> predators,
			List<IPopulation> preys, Dimension dim) {

		Vector preyForce = new Vector(0, 0);
		int nVisiblePreys = 0;
		for (IPopulation pop : preys) {
			for (IAgent a : pop.getAgents()) {
				Position p = a.getPosition();
				double distance = getPosition().getDistance(p);
				if (distance <= visionRange) {
					Vector newForce = new Vector(this.getPosition(), p);
					preyForce.add(newForce.multiply(1 / this.getPosition().getDistance(p) + 0.0000001));
					nVisiblePreys++;
				}
			}
		}

		if (nVisiblePreys == 0) { // Be unaffacted
			preyForce.setVector(this.getVelocity());
		}else{
			preyForce.multiply(1 / (double) nVisiblePreys);
			double norm = preyForce.getNorm();
			preyForce.multiply(maxSpeed / norm);
		}
		
		/*
		 * The environment force is at the moment defined as 1/(distance to
		 * wall)^2.
		 */
		Vector environmentForce = new Vector(0, 0);
		Position xWallLeft = new Position(0, this.getPosition().getY());
		Position xWallRight = new Position(dim.getWidth(), this.getPosition()
				.getY());
		Position yWallBottom = new Position(this.getPosition().getX(), 0);
		Position yWallTop = new Position(this.getPosition().getX(),
				dim.getHeight());

		double scale = 10;
		double xWallLeftForce = 1 / Math.pow((1 / scale)
				* (this.getPosition().getDistance(xWallLeft) - 1), 2);
		double xWallRightForce = -1
				/ Math.pow(
						(1 / scale)
								* (this.getPosition().getDistance(xWallRight) - 1),
						2);
		double yWallBottomForce = 1 / Math.pow((1 / scale)
				* (this.getPosition().getDistance(yWallBottom) - 1), 2);
		double yWallTopForce = -1
				/ Math.pow(
						(1 / scale)
								* (this.getPosition().getDistance(yWallTop) - 1),
						2);

		double xForce = (xWallLeftForce + xWallRightForce);
		double yForce = (yWallBottomForce + yWallTopForce);

		environmentForce.setVector(xForce, yForce);
		// System.out.println("Environment: " + environmentForce.toString() +
		// " | predatorForce: " + predatorForce.toString());
		// Rescale the new velocity to not exceed maxSpeed
		Vector acceleration = environmentForce.add(preyForce);
		double accelerationNorm = acceleration.getNorm();
		if (accelerationNorm > maxAcceleration) {
			// Scales the norm of the vector back to maxSpeed
			acceleration.multiply(maxAcceleration / accelerationNorm);
		}

		Vector newVelocity = this.getVelocity().add(acceleration);
		double speed = newVelocity.getNorm();
		if (speed > maxAcceleration) {
			// Scales the norm of the vector back to maxSpeed
			newVelocity.multiply(maxSpeed / speed);
		}

		this.getVelocity().setVector(newVelocity);

	}
}
