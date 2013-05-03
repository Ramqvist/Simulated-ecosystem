package chalmers.dax021308.ecosystem.model.genetics.newV;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import chalmers.dax021308.ecosystem.model.genetics.GeneralGeneTypes;

public class Chromosome implements IChromosome<GeneralGeneTypes, IGene> {

	protected Map<GeneralGeneTypes, IGene> chromosomeMap;
	protected double mutationProbability;
	
	public Chromosome() {
		this.chromosomeMap = makeNewMap();
		this.mutationProbability = 0.1;
	}
	
	private Chromosome(Map<GeneralGeneTypes,IGene> chromosome, double mutProb) {
		this.chromosomeMap = makeNewMap(chromosome);
		this.mutationProbability = mutProb;
	}
	
	private Map<GeneralGeneTypes, IGene> makeNewMap(){
		return new EnumMap<GeneralGeneTypes, IGene>(GeneralGeneTypes.class);
	}
	
	private Map<GeneralGeneTypes, IGene> makeNewMap(Map<GeneralGeneTypes,IGene> chromosome){
		return new EnumMap<GeneralGeneTypes, IGene>(chromosome);
	}
	
	@Override
	public IChromosome<GeneralGeneTypes, IGene> getCopy(){
		return new Chromosome(this.chromosomeMap, this.mutationProbability);
	}

	@Override
	public double getMutationProbabilty() {
		return this.mutationProbability;
	}

	@Override
	public void setMutationProbabilty(double mutationProbability) {
		this.mutationProbability = mutationProbability;
		
	}


	@Override
	public int getNumberOfGenes() {
		return this.chromosomeMap.size();
	}


	@Override
	public void addGene(GeneralGeneTypes geneType, IGene gene) {
		this.chromosomeMap.put(geneType, gene);
		
	}

	@Override
	public Object getGenes() {
		return makeNewMap(this.chromosomeMap);
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#getGeneNames()
	 */
	@Override
	public List<GeneralGeneTypes> getGeneNames() {
		return new ArrayList<GeneralGeneTypes>(this.chromosomeMap.keySet());
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#getGeneCurrentValue(java.lang.Object)
	 */
	@Override
	public Object getGeneCurrentValue(GeneralGeneTypes geneType) {
		return this.chromosomeMap.get(geneType).getCurrentValue();
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#setCurrentValue(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void setCurrentValue(GeneralGeneTypes geneType, Object currentValue) {
		this.chromosomeMap.get(geneType).setCurrentValue(currentValue);
		
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#mutateChromosome()
	 */
	@Override
	public void mutateChromosome() {
		for(IGene g : this.chromosomeMap.values()){
			g.mutate();
		}
		
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#getGene(java.lang.Object)
	 */
	@Override
	public IGene getGene(GeneralGeneTypes geneType) {
		return (IGene) this.chromosomeMap.get(geneType);
	}

}
