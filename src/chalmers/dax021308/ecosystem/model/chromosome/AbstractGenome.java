package chalmers.dax021308.ecosystem.model.chromosome;

/**
 * 
 * @author Loanne Berggren
 *
 */
public abstract class AbstractGenome<GENES> {
	
	private final double MUTATION_PROBABILITY = 0.01;
	
	public abstract AbstractGenome<GENES>mateWithMutation(AbstractGenome<GENES> other);
	
	/**
	 * 
	 * @return a mutated version of this genome. The original genome is unchanged.
	 */
	public abstract AbstractGenome<GENES> onlyMutate();
	
	public abstract boolean isGeneSet(GENES gene);
	
	public abstract Object getChromosome();
	
	public abstract int numberOfGenes();
	
	public abstract void setGene(GENES gene, boolean allele);
}
