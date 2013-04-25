package chalmers.dax021308.ecosystem.model.genetics;


/**
 * 
 * @author Loanne Berggren
 *
 * @param <G> Type of genes
 */
public interface IGenome<G extends IGenes> {
	/**
	 * Crosses this genome with other genome. The new genome might also be mutated.
	 * The original genomes are unchanged.
	 * <p>
	 * @param other
	 * @return The baby genome.
	 */
	public abstract IGenome<G> mateWithMutation(final IGenome<G> other);


	/**
	 * <p>
	 * @return 	A mutated version of this genome. 
	 * 			The original genome is unchanged.
	 */
	public abstract IGenome<G> onlyMutate();
	
	/**
	 * 
	 * @param gene
	 * @return		True if this is set. False if it is not.
	 */
	public abstract boolean isGeneSet(G gene);
	
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
	public abstract void setGene(G gene, boolean allele);
	
	/**
	 * Mutation probability specifies the probability that a gene mutates.
	 * @return Mutation Probability
	 */
	public abstract double getMutationProbability();


	/**
	 * @return
	 */
	int length();

}

