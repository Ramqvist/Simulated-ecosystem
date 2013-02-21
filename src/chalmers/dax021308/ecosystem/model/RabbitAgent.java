package chalmers.dax021308.ecosystem.model;

import java.awt.Color;
import java.util.List;

import chalmers.dax021308.ecosystem.model.util.Gender;

public class RabbitAgent implements IAgent {
	
	private Position position;
	private String name;
	private Color color;
	private int width;
	private int height;
	private double fitness;
	private double speed;
	private Gender gender;
	
	public RabbitAgent(Position position, String name, Color color, int width, int height, double speed, Gender gender) {
		this.position = position;
		this.name = name;
		this.color = color;
		this.width = width;
		this.height = height;
		this.speed = speed;
		this.gender = gender;
	}
	
	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub

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
	public double getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
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
}
