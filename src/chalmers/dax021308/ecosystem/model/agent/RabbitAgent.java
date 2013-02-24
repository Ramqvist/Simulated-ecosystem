package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.LinkedList;
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
	private Vector oldVelocity;
	private int delay;
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
		oldVelocity = velocity;
		delay = 0;
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
		List<IAgent> kids = new LinkedList<IAgent>();
		if (selectedFemale != null && position.getDistance(selectedFemale.getPosition()) < 10) {
			for (int i = 0; i < (int)(Math.random() * 5); i++) {
				if (i % 2 == 0) {
					kids.add(new RabbitAgent(position, name, color, width, height, velocity, gender));
				} else {
					kids.add(new RabbitAgent(selectedFemale.getPosition(), selectedFemale.getName(), selectedFemale.getColor(), 
							selectedFemale.getWidth(), selectedFemale.getHeight(), selectedFemale.getVelocity(), selectedFemale.getGender()));
				}
			}
			oldVelocity = velocity;
			velocity.setX(0);
			velocity.setY(0);
			delay = 20;
			selectedFemale = null;
		}
		return kids;
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
			if (delay == 0) {
				velocity = oldVelocity;
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
			} else {
				delay--;
			}
//			System.out.println(delay);
		} else if (gender == Gender.FEMALE) {
			changeDirection();
			avoidBorder(dim);
			position.addVector(velocity);
		}
	}
	
	private void changeDirection() {
		//Calculate random direction
		int dir = (int)(Math.random() * 2);
		switch (dir) {
		case 0:
			velocity = velocity.rotate(0.1);
			break;
		case 1:
			velocity = velocity.rotate(-0.1);
			break;
		}
	}
	
	//Make the rabbit switch direction when at a wall
	private void avoidBorder(Dimension dim) {
		double tempX = velocity.getX();
		double tempY = velocity.getY();
		
		if (position.getX() + velocity.getX() < 0 || position.getX() + velocity.getX() + width > dim.getWidth()) {
			tempX *= -1;
		}
		
		if (position.getY() + velocity.getY() < 0 || position.getY() + velocity.getY() + height > dim.getHeight()) {
			tempY *= -1;
		}
		velocity.setX(tempX);
		velocity.setY(tempY);
	}
}





