package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Gender;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * A basic implementation of the IAgent interface.
 * @author Albin
 */
public class SimplePreyAgent extends AbstractAgent {

	private double maxSpeed;
	private double visionRange;
	private double maxAcceleration;
	
	public SimplePreyAgent(String name, Position p, Color c, int width, int height, 
			Vector velocity, double maxSpeed, double maxAcceleration,double visionRange) {
		super(name, p, c, width, height, velocity);
		this.maxSpeed = maxSpeed;
		this.maxAcceleration = maxAcceleration;
		this.visionRange = visionRange;
	}
		@Override
	public List<IAgent> reproduce(IAgent agent) {
		return new LinkedList<IAgent>();
	}
		
	/**
	 * @author Sebbe
	 */
	@Override
	public void updatePosition(List<IPopulation> predators,
							   List<IPopulation> preys, List<IPopulation> neutral,Dimension gridDimension) {
		
		Vector predatorForce = getPredatorForce(predators);
		Vector separationForce = getSeparationForce(neutral);
		//Vector separationForce = new Vector();
		Vector environmentForce = getEnvironmentForce(gridDimension);
		
		/*
		 * Sum the forces from walls, predators and neutral to form the acceleration force.
		 * If the acceleration exceeds maximum acceleration --> scale it to maxAcceleration,
		 * but keep the correct direction of the acceleration.
		 */
		Vector acceleration = environmentForce.multiply(100).add(predatorForce).add(separationForce.multiply(10));
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
		getPosition().addVector(getVelocity());
	}
	
	/** 
	 * @author Sebbe
	 * "Predator Force" is defined as the sum of the vectors pointing away from all the predators in vision, weighted by
	 * the inverse of the distance to the predators, then normalized to have unit norm. 
	 * Can be interpreted as the average sum of forces that the agent feels, weighted
	 * by how close the source of the force is.
	 */
	private Vector getPredatorForce(List<IPopulation> predators){
		Vector predatorForce = new Vector(0,0);
		int nVisiblePredators = 0;
		for(IPopulation pop : predators) {
			for(IAgent predator : pop.getAgents()) {
				Position p = predator.getPosition();
				double distance = getPosition().getDistance(p);
				if(distance<=visionRange){ //If predator is in vision range for prey
					/*
					 * Create a vector that points away from the predator.
					 */
					Vector newForce = new Vector(this.getPosition(),p);
					
					/*
					 * Add this vector to the predator force, with proportion to how close the predator is.
					 * Closer predators will affect the force more than those far away. 
					 */
					double norm = newForce.getNorm();
					predatorForce.add(newForce.multiply(1/(norm*distance)));
					nVisiblePredators++;
				}
			}
		}
		
		if(nVisiblePredators==0){ //No predators near --> Be unaffected
			predatorForce.setVector(0,0);
		} else { //Else set the force depending on visible predators and normalize it to maxAcceleration.
			double norm = predatorForce.getNorm();
			predatorForce.multiply(maxAcceleration/norm);
		}	
		
		return predatorForce;
	}
	
	/**
	 * @author Sebbe
	 * The environment force is at the moment defined as 1/(wall-constant*(distance to wall)^2).
	 * The agents feel the forces from the wall directly to the left, right, top and bottom.
	 */
	private Vector getEnvironmentForce(Dimension dim){
		/*
		 * The positions below is just an orthogonal projection on to the walls.
		 */
		Position xWallLeft = new Position(0,this.getPosition().getY());
		Position xWallRight = new Position(dim.getWidth(),this.getPosition().getY());
		Position yWallBottom = new Position(this.getPosition().getX(),0);
		Position yWallTop = new Position(this.getPosition().getX(),dim.getHeight());
		
		/*
		 * There is a "-1" in the equation just to make it more unlikely that they actually make it to the wall,
		 * despite the force they feel (can be interpreted as they stop 1 pixel before the wall).
		 */
		Vector environmentForce = new Vector(0,0);
		double xWallLeftForce = 0;
		double xWallRightForce = 0;
		double yWallBottomForce = 0;
		double yWallTopForce = 0;
		
		/*
		 * Only interacts with walls that are closer than INTERACTION_RANGE.
		 */
		double leftWallDistance = this.getPosition().getDistance(xWallLeft);
		if(leftWallDistance<=INTERACTION_RANGE){
			xWallLeftForce = 1/Math.pow((leftWallDistance-1.0)/WALL_CONSTANT,2);
		}
		
		double rightWallDistance = this.getPosition().getDistance(xWallRight);
		if(rightWallDistance<=INTERACTION_RANGE){
			xWallRightForce = -1/Math.pow((rightWallDistance-1.0)/WALL_CONSTANT,2);
		}
		
		double bottomWallDistance = this.getPosition().getDistance(yWallBottom);
		if(bottomWallDistance<=INTERACTION_RANGE){
			yWallBottomForce = 1/Math.pow((bottomWallDistance-1.0)/WALL_CONSTANT,2);
		}
		
		double topWallDistance = this.getPosition().getDistance(yWallTop);
		if(topWallDistance<=INTERACTION_RANGE){
			yWallBottomForce = yWallTopForce = -1/Math.pow((topWallDistance-1.0)/WALL_CONSTANT,2);
		}
		
		/*
		 * Add the forces from left and right to form the total force from walls in x-axis.
		 * Add the forces from top and bottom to form the total force from walls in y-axis.
		 * Create a force vector of the forces.
		 */
		double xForce = (xWallLeftForce + xWallRightForce);
		double yForce = (yWallBottomForce + yWallTopForce);
		environmentForce.setVector(xForce, yForce);
		
		return environmentForce;
	}
	
	/**
	 * @author Sebbe
	 */
	private Vector getSeparationForce(List<IPopulation> neutral){
		Vector separationForce = new Vector(0,0);
		int nVisiblePredators = 0;
		for(IPopulation pop : neutral) {
			for(IAgent agent : pop.getAgents()) {
				if(agent != this) {
					Position p = agent.getPosition();
					double distance = getPosition().getDistance(p);
					if(distance<=INTERACTION_RANGE){ //If neutral is in vision range for prey
						/*
						 * Create a vector that points away from the neutral.
						 */
						Vector newForce = new Vector(this.getPosition(),p);
						
						/*
						 * Add this vector to the separation force, with proportion to how close the neutral agent is.
						 * Closer agents will affect the force more than those far away. 
						 */
						double norm = newForce.getNorm();
						separationForce.add(newForce.multiply(1/(norm*distance*distance)));
						nVisiblePredators++;
					}
				}
			}
		}
//		System.out.println(separationForce.getNorm());
		return separationForce;
		
	}
	
}



















