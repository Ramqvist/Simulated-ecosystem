package chalmers.dax021308.ecosystem.model;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;

import chalmers.dax021308.ecosystem.model.util.Gender;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;
/**
 * 
 * @author Henrik
 * Its purpose is simply to hunt down the SimpleAgent (or any other agent) in a simple way
 */
public class SimplePreyAgent extends AbstractAgent{

	public SimplePreyAgent(String name, Position p, Color c, int width,
			int height, Vector velocity) {
		super(name, p, c, width, height, velocity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void updatePosition(List<IPopulation> predators,
			List<IPopulation> preys, Dimension dim) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public List<IAgent> reproduce(IAgent agent) {
		// TODO Auto-generated method stub
		return null;
	}
}
