package chalmers.dax021308.ecosystem.model.population;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.agent.WolfAgent;
import chalmers.dax021308.ecosystem.model.environment.SurroundingsSettings;
import chalmers.dax021308.ecosystem.model.genetics.GeneralGeneTypes;
import chalmers.dax021308.ecosystem.model.genetics.GeneticSettings;
import chalmers.dax021308.ecosystem.model.genetics.IGene;
import chalmers.dax021308.ecosystem.model.genetics.IGenome;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 *
 * @author Henrik
 *
 */
public class WolfPopulation extends AbstractPopulation {

	private double maxSpeed;
	private double visionRange;

	public WolfPopulation(String name, int initPopulationSize, Color color,
			double maxSpeed, double maxAcceleration, double visionRange,
			boolean groupBehaviour, SurroundingsSettings surroundings) {

		super(name, color, surroundings);

		this.visionRange = visionRange;
		this.groupBehaviour = groupBehaviour;
		agents = initializePopulation(initPopulationSize, SurroundingsSettings.getGridDimension(), color,
				maxSpeed, maxAcceleration, visionRange);
	}

	private List<IAgent> initializePopulation(int populationSize,
			Dimension gridDimension, Color color, double maxSpeed,
			double maxAcceleration, double visionRange) {

		List<IAgent> newAgents = new ArrayList<IAgent>(populationSize);
		addNeutralPopulation(this);

		for (int i = 0; i < populationSize; i++) {
			Position randPos = getRandomPosition();
			Vector velocity = new Vector(maxSpeed, maxSpeed);

			// Create a random vector (uniformly) inside a circle with radius
			// maxSpeed.
			while (velocity.getNorm() > maxSpeed) {
				velocity.setVector(-maxSpeed + Math.random() * 2 * maxSpeed,
						-maxSpeed + Math.random() * 2 * maxSpeed);
			}

			IGenome<GeneralGeneTypes, IGene> genome = GeneticSettings.predSettings.getGenome().getCopy();
			// TODO comment this during sim?
			genome.getGene(GeneralGeneTypes.ISGROUPING).isGeneActive();
			genome.getGene(GeneralGeneTypes.GROUPING_COHESION).setRandomStartValue(false);
			genome.getGene(GeneralGeneTypes.GROUPING_SEPARATION_FACTOR).setRandomStartValue(false);
			genome.getGene(GeneralGeneTypes.GROUPING_ARRAYAL_FORCE).setRandomStartValue(false);
			genome.getGene(GeneralGeneTypes.GROUPING_FORWARD_THRUST).setRandomStartValue(false);
			genome.getGene(GeneralGeneTypes.FOCUSPREY).setRandomStartValue(false);

			IAgent a = new WolfAgent("Wolf", randPos, color, 10, 20,
					velocity, maxSpeed, maxAcceleration, visionRange, genome);
			newAgents.add(a);
		}
		return newAgents;
	}

	public double agentsGroupingProportion(){
		double groupCount = 0;
		WolfAgent da = null;
		for(IAgent a: agents) {
			if(a instanceof WolfAgent) {
				da = (WolfAgent) a;
			}
			if(da.isAGroupingWolf()){
				groupCount++;
			}
		}
		return groupCount/((double)agents.size());
	}

	@Override
	public void updatePositions() {
		super.updatePositions();
		this.interestingPropertyProportion = agentsGroupingProportion();
	}

	/*
	 * @Override public void update() { super.update(); int size =
	 * agents.size(); WolfAgent a; for(int i=0; i<size; i++){ a = (WolfAgent)
	 * agents.get(i); if(a.getEnergy()<=0){ addToRemoveList(a); } } }
	 */
}
