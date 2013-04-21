package chalmers.dax021308.ecosystem.model.chromosome;

/**
 * 
 * @author Loanne Berggren
 *
 * @param <E> Type of genes
 */
public interface IGenome<E extends Enum<E>> {
	/**
	 * Crosses this genome with other genome. The new genome might also be mutated.
	 * The original genomes are unchanged.
	 * <p>
	 * @param other
	 * @return The baby genome.
	 */
	public abstract IGenome<E> mateWithMutation(final IGenome<E> other);


	/**
	 * <p>
	 * @return 	A mutated version of this genome. 
	 * 			The original genome is unchanged.
	 */
	public abstract IGenome<E> onlyMutate();
	
	/**
	 * 
	 * @param gene
	 * @return		True if this is set. False if it is not.
	 */
	public abstract boolean isGeneSet(E gene);
	
	/**
	 * 
	 * @return The chromosomes
	 */
	public abstract Object getChromosomes();

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
	public abstract void setGene(E gene, boolean allele);
	
	/**
	 * Mutation probability specifies the probability that a gene mutates.
	 * @return Mutation Probability
	 */
	public abstract double getMutationProbability();

}

