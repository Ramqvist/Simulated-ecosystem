package chalmers.dax021308.ecosystem.model.genetics.newV;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import chalmers.dax021308.ecosystem.model.genetics.GeneralGeneTypes;

public class NewChromosome implements NewIChromosome<GeneralGeneTypes, IGene> {

	protected Map<GeneralGeneTypes, IGene> chromosomeMap;
	protected double mutationProbability;
	
	public NewChromosome() {
		this.chromosomeMap = makeNewMap();
		this.mutationProbability = 0.1;
	}
	
	private NewChromosome(Map<GeneralGeneTypes,IGene> chromosome, double mutProb) {
		this.chromosomeMap = chromosome;
		this.mutationProbability = mutProb;
	}
	
	private Map<GeneralGeneTypes, IGene> makeNewMap(){
		return new EnumMap<GeneralGeneTypes, IGene>(GeneralGeneTypes.class);
	}
	
	/*@Override
	public IChromosomeGeneric<GeneralGeneTypes,IGene> crossChromosomes(IChromosomeGeneric<GeneralGeneTypes,Double,GeneDouble> other) {
		throw new UnsupportedOperationException("crossChromosomes");
	}*/

	@Override
	public Object clone(){
		// TODO, don't know if it actually makes deep clone.
		return new NewChromosome(new EnumMap<GeneralGeneTypes,IGene>(chromosomeMap), this.mutationProbability);
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#getMutationProbabilty()
	 */
	@Override
	public double getMutationProbabilty() {
		return this.mutationProbability;
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#setMutationProbabilty(double)
	 */
	@Override
	public void setMutationProbabilty(double mutationProbability) {
		this.mutationProbability = mutationProbability;
		
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#getNumberOfGenes()
	 */
	@Override
	public int getNumberOfGenes() {
		return this.chromosomeMap.size();
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#addGene(java.lang.Object, java.lang.Object)
	 */
	@Override
	public void addGene(GeneralGeneTypes geneType, IGene gene) {
		this.chromosomeMap.put(geneType, gene);
		
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome#getGenes()
	 */
	@Override
	public Object getGenes() {
		// TODO Auto-generated method stub
		return null;
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
		return (IGene) this.chromosomeMap.get(geneType).clone();
	}

}
