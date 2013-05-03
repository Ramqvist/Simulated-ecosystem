package chalmers.dax021308.ecosystem.model.genetics.newV;

/**
 * 
 * @author Loanne Berggren
 *
 * @param <T> Type of genes
 */
public abstract class GenericGenome<E extends Enum<E>, T extends IGene> implements IGenome<E,T>{
	protected IChromosome<E,T> chromosome;

	protected GenericGenome() {}
	
	/**
	 * Creates a new Genome.
	 * Use {@link #setGene(IGenes, boolean)} to change gene value.
	 * @param chromosome
	 */
	public GenericGenome(IChromosome<E,T> chromosome) {
		this.chromosome = chromosome;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getMutationProbability() {
		return this.chromosome.getMutationProbabilty();
	}
	

	@Override
	public void setMutationProbability(double mutationProbability) {
		this.chromosome.setMutationProbabilty(mutationProbability);
	}
	
	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IGenome#getGeneValue(chalmers.dax021308.ecosystem.model.genetics.IGenes)
	 */
	@Override
	public Object getGeneCurrentValue(E geneType) {
		return this.chromosome.getGeneCurrentValue(geneType);
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IGenome#setGene(chalmers.dax021308.ecosystem.model.genetics.IGenes, int)
	 */
	@Override
	public void setGene(E geneType, T gene) {
		this.chromosome.addGene(geneType, gene);
	}

	/**
	 * @return {@inheritDoc}
	 */
	public Object getAllGenes(){
		return this.chromosome.getGenes();
	}

	/**
	 * 
	 * @return Number of genes.
	 */
	@Override
	public int numberOfGenes(){
		return this.chromosome.getNumberOfGenes();
	}
	
	/**
	 * <p>
	 * @return 	A mutated version of this genome. 
	 * 			The original genome is unchanged hopefully.
	 */
	@Override
	public abstract IGenome<E,T> onlyMutate();
	
	/*@Override
	public IGenome<E,T> onlyMutate(){
		IChromosome<E,T> nChrom = (IChromosome<E,T>) this.chromosome.clone();
		nChrom.mutateChromosome();
		return new GenericGenome<E,T>(nChrom);
	}*/

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IntAndAbs.IGenomeGeneric#getGene(java.lang.Enum)
	 */
	@Override
	public T getGene(E geneType){
		return this.chromosome.getGene(geneType);
	}

}

