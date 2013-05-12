package chalmers.dax021308.ecosystem.model.genetics;

import java.util.Map;


/**
 *
 * @author Loanne Berggren
 *
 * @param <G> Type of genes
 */
public interface IGenome<E extends Enum<E>, G> {

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
	public abstract Map<E,G> getAllGenes();

	/**
	 *
	 * @return Number of genes.
	 */
	public abstract int numberOfGenes();

	/**
	 * <p>
	 * @return 	A mutated version of this genome.
	 * 			The original genome is unchanged.
	 */
	public abstract IGenome<E,G> onlyMutate();

	@Override
	public boolean equals(Object other);

	public IGenome<E, G> getCopy();
}

