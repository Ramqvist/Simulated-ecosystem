package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;
/**
 * 
 * @author Henrik
 *
 */
public class GrassAgent extends AbstractAgent{

	

	public GrassAgent(String name, Position pos, Color color, int width, int height) {
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
		//Do nothing, grass shouldn't move!
	}

	@Override
	public List<IAgent> reproduce(IAgent agent) {
		// TODO Auto-generated method stub
		return null;
	}

}
