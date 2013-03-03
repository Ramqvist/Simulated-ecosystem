package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.agent.RabbitAgent;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class RabbitPopulation extends AbstractPopulation {


	public RabbitPopulation(int popSize, Dimension d, String name, Color color,double maxSpeed, double visionRange, double maxAcceleration) {
		super(name, d);
		agents = new ArrayList<IAgent>(popSize);

		for (int i = 0; i < popSize; i++) {
//			Gender g = Gender.MALE;
//			Color c = Color.black;
//
//			if (i % 2 == 0) {
//				g = Gender.FEMALE;
//				c = Color.lightGray;
//			}
			RabbitAgent r = new RabbitAgent(new Position(d.getWidth()
					* Math.random(), d.getHeight() * Math.random()), name, color,
					20, 20, new Vector(Math.random()*2, Math.random()*2), null,
					maxSpeed, visionRange, maxAcceleration);
			agents.add(r);
			EcoWorld.worldGrid.add(r);
		}
	}

	@Override
	public void update() {
		super.update();
	}


	@Override
	public double calculateFitness(IAgent agent) {
		// TODO Auto-generated method stub
		return 0;
	}
}
