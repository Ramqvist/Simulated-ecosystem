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
public class SimpleAgent implements IAgent {

	private Position position;
	private String name;
	private Color color;
	private int width; 
	private int height;
	private double finess;
	private double maxSpeed;
	private double visionRange;
	private Vector velocity;
	private double maxAcceleration;
	
	public SimpleAgent(String name, Position p, Color c, int width, int height, 
			Vector velocity, double maxSpeed, double maxAcceleration,double visionRange) {
		this.name = name;
		position = p;
		color = c;
		this.width = width;
		this.height = height;
		this.maxSpeed = maxSpeed;
		this.maxAcceleration = maxAcceleration;
		this.velocity = velocity;
		this.visionRange = visionRange;
	}
	
	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public double getFitness() {
		return finess;
	}

	@Override
	public void setFitness(double fitness) {
		this.finess = fitness;
	}

	@Override
	public Vector getVelocity() {
		return velocity;
	}

	@Override
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;
	}

	@Override
	public List<IAgent> reproduce(IAgent agent) {
		return new LinkedList<IAgent>();
	}

	@Override
	public Gender getGender() {
		return null;
	}

	/**
	 * @author Sebbe
	 */
	@Override
	public void updatePosition(List<IPopulation> predators,
							   List<IPopulation> preys, Dimension gridDimension) {
		setNewVelocity(predators, preys, gridDimension);
		position.addVector(velocity);
	}
	
	/**
	 * @author Sebbe
	 * 
	 */
	private void setNewVelocity(List<IPopulation> predators, List<IPopulation> preys, Dimension dim){
		/* 
		 * "Best velocity" is defined as the sum of the vectors pointing away from all the predators in vision, weighted by
		 * the distance to the predators, divided by the number of predators in vision. 
		 * Can be interpreted as the average sum of forces that the agent feels, weighted
		 * by how close the source of the force is.
		 * from the predators.
		 */
		Vector predatorForce = new Vector(0,0);
		int nVisiblePredators = 0;
		for(IPopulation pop : predators) {
			for(IAgent a : pop.getAgents()) {
				Position p = a.getPosition();
				double distance = position.getDistance(p);
				if(distance<=visionRange){
					Vector newForce = new Vector(this.position,p);
					predatorForce.add(newForce.multiply(1/this.position.getDistance(p)+0.0000001));
					nVisiblePredators++;
				}
			}
		}
		
		if(nVisiblePredators==0){ //Be unaffected
			predatorForce.setVector(this.velocity);
		} else { //Else set the force depending on visible predators.
			predatorForce.multiply(1/(double)nVisiblePredators);
			double norm = predatorForce.getNorm();
			predatorForce.multiply(maxSpeed/norm);
		}	
		
		/*
		 * The environment force is at the moment defined as 1/(distance to wall)^2.
		 */
		Vector environmentForce = new Vector(0,0);
		Position xWallLeft = new Position(0,this.position.getY());
		Position xWallRight = new Position(dim.getWidth(),this.position.getY());
		Position yWallBottom = new Position(this.position.getX(),0);
		Position yWallTop = new Position(this.position.getX(),dim.getHeight());
		
		double scale = 10;
		double xWallLeftForce = 1/Math.pow((1/scale)*(this.position.getDistance(xWallLeft)-1),2);
		double xWallRightForce = -1/Math.pow((1/scale)*(this.position.getDistance(xWallRight)-1),2);
		double yWallBottomForce = 1/Math.pow((1/scale)*(this.position.getDistance(yWallBottom)-1),2);
		double yWallTopForce = -1/Math.pow((1/scale)*(this.position.getDistance(yWallTop)-1),2);
		
		double xForce = (xWallLeftForce + xWallRightForce);
		double yForce = (yWallBottomForce + yWallTopForce);
		
		environmentForce.setVector(xForce, yForce);
		System.out.println("Environment: " + environmentForce.toString() + " | predatorForce: " + predatorForce.toString());
		//Rescale the new velocity to not exceed maxSpeed
		Vector acceleration = environmentForce.add(predatorForce);
		double accelerationNorm = acceleration.getNorm();
		if(accelerationNorm > maxAcceleration){
			//Scales the norm of the vector back to maxSpeed
			acceleration.multiply(maxAcceleration/accelerationNorm); 
		}
		
		Vector newVelocity = this.velocity.add(acceleration);
		double speed = newVelocity.getNorm();
		if(speed > maxAcceleration){
			//Scales the norm of the vector back to maxSpeed
			newVelocity.multiply(maxSpeed/speed); 
		}
		
		this.velocity.setVector(newVelocity);
	}
	
	
	
}



















