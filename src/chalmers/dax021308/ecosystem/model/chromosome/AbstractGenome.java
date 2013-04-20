package chalmers.dax021308.ecosystem.model.chromosome;

/**
 * 
 * @author Loanne Berggren
 *
 * @param <E> Type of genes
 */
public abstract class AbstractGenome<E extends Enum<E>> implements IGenome<E> {
	protected IChromosome chromosome;

	protected AbstractGenome() {}
	
	/*
	 * Used privately only.
	 */
	protected AbstractGenome(IChromosome chromosome) {
		this.chromosome = chromosome;
	}
	

	/**
	 * Crosses this genome with other genome. The new genome might also be mutated.
	 * The original genomes are unchanged.
	 * <p>
	 * @param other
	 * @return The baby genome.
	 */
	@Override
	public IGenome<E> mateWithMutation(final IGenome<E> other){
			IChromosome nChrom = chromosome.crossChromosomes((IChromosome)other.getChromosomes());
			nChrom.mutateChromosome();
			return this.genomeFactory(nChrom);
	}


	/**
	 * <p>
	 * @return 	A mutated version of this genome. 
	 * 			The original genome is unchanged.
	 */
	@Override
	public IGenome<E> onlyMutate(){
		IChromosome nChrom = (IChromosome) this.chromosome.clone();
		nChrom.mutateChromosome();
		return this.genomeFactory(nChrom);
	}
	
	/**
	 * 
	 * @param gene
	 * @return		True if this is set. False if it is not.
	 */
	@Override
	public abstract boolean isGeneSet(E gene);
	

	/**
	 * @return {@inheritDoc}
	 */
	@Override
	public Object getChromosomes(){
		return this.chromosome;
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
	 * {@inheritDoc}
	 */
	@Override
	public double getMutationProbability() {
		return this.chromosome.getMutationProbabilty();
	}
	
	/**
	 * 
	 * @param gene	The gene to set.
	 * @param allele	True if gene should be set, otherwise false.
	 */
	@Override
	public abstract void setGene(E gene, boolean allele) ;

	/**
	 * 
	 * @param chromosome
	 * @return
	 */
	protected abstract IGenome<E> genomeFactory(IChromosome chromosome);
	
	@Override
	public abstract boolean equals(Object other);
	
}

