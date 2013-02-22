package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;

import chalmers.dax021308.ecosystem.model.util.Gender;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;
/**
 * 
 * @author Henrik
 * Abstract class for handling the dummy methods
 */
public abstract class AbstractAgent implements IAgent {
	private Position position;
	private String name;
	private Color color;
	private int width;
	private int height;
	private Vector velocity;
	private Gender gender;
	private double fitness;
	protected final static double WALL_CONSTANT = 4;
	
	public AbstractAgent(String name, Position p, Color c, int width, int height, Vector velocity){
		this.name = name;
		position = p;
		color = c;
		this.width = width;
		this.height = height;
		this.velocity = velocity;
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
	public Vector getVelocity() {
		return velocity;
	}

	@Override
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;

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
	public Gender getGender() {
		return gender;
	}

}
