package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * Simple grass, lowest part of the food chain
 * 
 * @author Henrik
 * 
 */
public class GrassAgent extends AbstractAgent {
	private double reproduceDelay = 0;
	private final Dimension gridDimension;

	public GrassAgent(String name, Position pos, Color color, int width,
			int height, Vector velocity, double maxSpeed,
			Dimension gridDimension) {
		super(name, pos, color, width, height, velocity, maxSpeed, 0, 0);
		this.gridDimension = gridDimension;
	}

	@Override
	public void calculateNextPosition(List<IPopulation> predators,
			List<IPopulation> preys, List<IPopulation> neutral, Dimension dim) {
		// Do nothing, grass shouldn't move!
	}

	@Override
	public void updatePosition() {
		// Do nothing, grass shouldn't move!
	}

	@Override
	public List<IAgent> reproduce(IAgent agent) {
		double repr = Math.random() * 50000; // for whenever we want the spawns
												// to happen randomly
		if (repr < reproduceDelay) {

			List<IAgent> spawn = new ArrayList<IAgent>();
			Position pos = new Position(getPosition());
			Vector v = new Vector(5, 5);
			v.rotate(Math.random() * 2 * Math.PI)
					.add(getEnvironmentForce(gridDimension).multiply(100))
					.getNorm();
			pos.addVector(v);
			IAgent a = new GrassAgent(name, pos, color, 5, 5, velocity,
					maxSpeed, gridDimension);
			reproduceDelay = 0;
			spawn.add(a);
			return spawn;
		} else {
			reproduceDelay++;
			return null;
		}

	}
}
