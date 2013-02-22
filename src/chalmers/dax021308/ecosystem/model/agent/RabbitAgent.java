package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Gender;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class RabbitAgent implements IAgent {
	
	private Position position;
	private String name;
	private Color color;
	private int width;
	private int height;
	private double fitness;
	private Vector velocity;
	private Gender gender;
	private IAgent selectedFemale = null;
	
	public RabbitAgent(Position position, String name, Color color, int width, int height, Vector velocity, Gender gender) {
		this.position = position;
		this.name = name;
		this.color = color;
		this.width = width;
		this.height = height;
		this.velocity = velocity;
		this.gender = gender;
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
		return fitness;
	}

	@Override
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public List<IAgent> reproduce(IAgent agent) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Gender getGender() {
		return gender;
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
	public void updatePosition(List<IPopulation> predators,
			List<IPopulation> preys, Dimension dim) {
		
		if (gender == Gender.MALE) {
			//Locate the closest female
			double minDistance = Double.MAX_VALUE; 
			
			for (IPopulation p : preys) {
				for (IAgent a : p.getAgents()) {
					if (a.getGender() == Gender.FEMALE) {
						if (position.getDistance(a.getPosition()) < minDistance) {
							minDistance = position.getDistance(a.getPosition());
							selectedFemale = a;
						}
					}
				}
			}
			
			if (selectedFemale != null) {
				Vector unitVector = new Vector(selectedFemale.getPosition(), position).toUnitVector();
				velocity = unitVector.multiply(velocity.getNorm());
			}
			position.addVector(velocity);
		} else if (gender == Gender.FEMALE) {
			//Calculate random position
			double tempX = velocity.getX();
			double tempY = velocity.getY();
			
			if (position.getX() + velocity.getX() < 0 || position.getX() + velocity.getX() > dim.getWidth()) {
				tempX *= -1;
			}
			
			if (position.getY() + velocity.getY() < 0 || position.getY() + velocity.getY() > dim.getHeight()) {
				tempY *= -1;
			}
			velocity = new Vector(tempX, tempY);
			position.addVector(velocity);
		}
	}
}





