package chalmers.dax021308.ecosystem.model.genetics.newV;


/**
 * 
 * @author Loanne Berggren
 *
 * @param <G> Type of genes
 */
public interface NewIGenome<E extends Enum<E>, G> {
	
	/**
	 * Mutation probability specifies the probability that a gene mutates.
	 * @return Mutation Probability
	 */
	public abstract double getMutationProbability();
	
	/**
	 * Mutation probability specifies the probability that a gene mutates.
	 * Set to 0 if no mutation should occur.
	 * @param mutationProbability
	 */
	public abstract void setMutationProbability(double mutationProbability);

	/**
	 * 
	 * @param gene
	 * @return		True if this is set. False if it is not.
	 */
	//public abstract boolean hasGeneAllele(E geneType);	
	
	/**
	 * 
	 * @param gene
	 * @return		
	 */
	public abstract Object getGeneCurrentValue(E geneType);	
	
	
	/**
	 * 
	 * @param geneType
	 * @return		
	 */
	public abstract G getGene(E geneType);	
	
	/**
	 * 
	 * @param gene	The gene to set.
	 * @param value	
	 */
	public abstract void setGene(E geneType, G value);

	
	/**
	 * 
	 * @return The chromosomes
	 */
	public abstract Object getAllGenes();
	
	/**
	 * 
	 * @return Number of genes.
	 */
	public abstract int numberOfGenes();

	/**
	 * Crosses this genome with other genome. The new genome might also be mutated.
	 * The original genomes are unchanged.
	 * <p>
	 * @param other
	 * @return The baby genome.
	 * @see #setMutationProbability(double)
	 */
	//public abstract NewIGenome<E,N> mateWithMutation(final NewIGenome<E,N> other);


	/**
	 * <p>
	 * @return 	A mutated version of this genome. 
	 * 			The original genome is unchanged.
	 */
	public abstract NewIGenome<E,G> onlyMutate();

	@Override
	public boolean equals(Object other);
}

