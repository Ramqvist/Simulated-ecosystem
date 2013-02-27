package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * 
 * @author Henrik
 * 
 */
public class GrassAgent extends AbstractAgent {
	private int reproduceDelay = 0;

	public GrassAgent(String name, Position pos, Color color, int width,
			int height) {
		super(name, pos, color, width, height);
	}

	public GrassAgent(String name, Position pos, Color color, int width,
			int height, Vector velocity, double maxSpeed) {
		super(name, pos, color, width, height, velocity, maxSpeed, 0, 0);
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
		if (reproduceDelay == 50) {
			Position pos = new Position(getPosition().getX() + 1, getPosition()
					.getY() + 1);
			IAgent a = new GrassAgent("Grass", pos, color, 5, 5, velocity,
					maxSpeed);
			reproduceDelay = 0;
			List<IAgent> spawn = new ArrayList<IAgent>();
			spawn.add(a);
			return spawn;
		} else {
			//reproduceDelay++; uncomment once it works
			return null;
		}

	}

}
