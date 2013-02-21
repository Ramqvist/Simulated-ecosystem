package chalmers.dax021308.ecosystem.model;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

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
	private double speed;
	
	public SimpleAgent(String name, Position p, Color c, int width, int height, double speed) {
		this.name = name;
		position = p;
		color = c;
		this.width = width;
		this.height = height;
		this.speed = speed;
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
	public double getSpeed() {
		return speed;
	}

	@Override
	public void setSpeed(double speed) {
		this.speed = speed;
	}

	@Override
	public List<IAgent> reproduce(IAgent agent) {
		return new LinkedList<IAgent>();
	}

	@Override
	public int getGender() {
		return -1;
	}

	@Override
	public void updatePosition() {
		// TODO Auto-generated method stub
		
	}
}
