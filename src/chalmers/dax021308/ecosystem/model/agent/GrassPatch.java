package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import chalmers.dax021308.ecosystem.model.util.CircleShape;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * A patch of grass
 * 
 * @author Henrik
 * 
 */
public class GrassPatch {
	private List<IAgent> grassPatches;
	private Position pos;
	private Dimension size;
	private int capacity;
	private static final double REPRODUCTION_RATE = 0.003;
	private IShape shape;
	private String name;
	private Color color;
	private Vector grassVelocity;

	public GrassPatch(Position pos, Dimension size, int capacity, String name,
			Color color) {
		grassPatches = new ArrayList<IAgent>();
		this.pos = pos;
		this.size = size;
		this.capacity = capacity;
		this.name = name;
		this.color = color;
		shape = new CircleShape();
	}

	public IAgent createGrass(int populationSize, Dimension gridDimension,
			IShape shape) {
		IAgent grass = new GrassAgent(name, getSpawnPosition(gridDimension, shape),
				color, 5, 5, grassVelocity, 2.0, capacity);
		grassPatches.add(grass);
		return grass;
	}

	public List<IAgent> update(int populationSize, Dimension gridDimension,
			IShape shape) {
		List<IAgent> spawn = new ArrayList<IAgent>();
		for (int i = 0; i < grassPatches.size(); i++) {
			double popSize = (double) populationSize;
			double cap = (double) capacity;
			if (Math.random() < REPRODUCTION_RATE * (1.0 - popSize / cap)) {
				IAgent straw = createGrass(populationSize, gridDimension, shape);
				spawn.add(straw);
			}
		}
		return spawn;
	}

	/**
	 * Finds a new position close to the current position
	 * 
	 * @param gridDimension
	 *            TODO
	 * 
	 * @return The position found
	 */
	private Position getSpawnPosition(Dimension gridDimension, IShape shape) {
		Position p;
		do {
			p = shape.getRandomPosition(size);
			double x = p.getX() - size.getWidth();
			double y = p.getY() - size.getHeight();
			Vector v = new Vector(x, y);
			p = Position.positionPlusVector(pos, v);
		} while (!shape.isInside(gridDimension, p));
		return p;
	}
}
