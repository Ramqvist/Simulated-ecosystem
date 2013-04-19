package chalmers.dax021308.ecosystem.model.chromosome;

/**
 * 
 * @author Loanne Berggren
 *
 */
public abstract class AbstractGenome<T> {
	
	// Standard Mutation propability. Override to get a specific value.
	private final double MUTATION_PROBABILITY = 0.01;
	
	/**
	 * Crosses this genome with other genome. The new genome might also be mutated.
	 * The original genomes are unchanged.
	 * @param other
	 * @return The baby genome.
	 */
	public abstract AbstractGenome<T> mateWithMutation(final AbstractGenome<T> other);
	
	/**
	 * 
	 * @return 	A mutated version of this genome. 
	 * 			The original genome is unchanged.
	 */
	public abstract AbstractGenome<T> onlyMutate();
	
	/**
	 * 
	 * @param gene
	 * @return		True if this is set. False if it is not.
	 */
	public abstract boolean isGeneSet(T gene);
	
	/**
	 * 
	 * @return
	 */
	public abstract Object getChromosome();
	
	/**
	 * 
	 * @return Number of genes.
	 */
	public abstract int numberOfGenes();
	
	/**
	 * 
	 * @param gene	The gene to set.
	 * @param allele	True if gene should be set, otherwise false.
	 */
	public abstract void setGene(T gene, boolean allele);
}

