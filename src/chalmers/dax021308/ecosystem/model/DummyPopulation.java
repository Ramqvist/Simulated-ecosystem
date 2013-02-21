package chalmers.dax021308.ecosystem.model;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

public class DummyPopulation implements IPopulation {
	
	private List<IAgent> agents;
	
	public DummyPopulation(List<IAgent> agentList){
		agents = agentList;
	}

	public DummyPopulation(Dimension gridDimension, Color color){
		agents = initializePopulation(100, gridDimension, color);
	}
	
	private List<IAgent> initializePopulation(int populationSize, Dimension gridDimension, Color color) {
		List<IAgent> newAgents = new ArrayList<IAgent>(populationSize);
		for(int i=0;i<populationSize;i++) {
			Position randPos = new Position(gridDimension.getWidth()*Math.random(), 
											gridDimension.getWidth()*Math.random());
			SimpleAgent a = new SimpleAgent("Big tasty", randPos, color, 10, 10, 1);
			newAgents.add(a);
		}
		return newAgents;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	@Override
	public double calculateFitness(IAgent agent) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<IAgent> getAgents() {
		return agents;
	}

	@Override
	public List<IAgent> getPredators() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<IAgent> getPreys() {
		// TODO Auto-generated method stub
		return null;
	}

}
