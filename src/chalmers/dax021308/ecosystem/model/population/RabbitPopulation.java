package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.agent.RabbitAgent;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.WorldGrid;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class RabbitPopulation extends AbstractPopulation {

	/**
	 * 
	 * @param name
	 * @param d
	 * @param popSize
	 * @param color
	 * @param maxSpeed
	 * @param maxAcceleration
	 * @param visionRange
	 */
	public RabbitPopulation(String name, Dimension d, int popSize, Color color,
			double maxSpeed, double maxAcceleration, double visionRange,
			IShape shape) {
		super(name, d, shape);
		agents = new ArrayList<IAgent>(popSize);

		for (int i = 0; i < popSize; i++) {
			// Gender g = Gender.MALE;
			// Color c = Color.black;
			//
			// if (i % 2 == 0) {
			// g = Gender.FEMALE;
			// c = Color.lightGray;
			// }
			Position randPos = shape.getRandomPosition(gridDimension);
			RabbitAgent r = new RabbitAgent(randPos, name,
					color, 10, 10, new Vector(Math.random() * 2,
							Math.random() * 2), null, maxSpeed, visionRange,
					maxAcceleration);
			agents.add(r);
			WorldGrid.getInstance().add(r);
		}
	}

	@Override
	public double calculateFitness(IAgent agent) {
		// TODO Auto-generated method stub
		return 0;
	}
}
