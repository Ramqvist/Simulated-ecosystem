/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics;

import java.util.List;
import java.util.Map;


/**
 *
 * @author Loanne Berggren
 *
 */
public interface IChromosome <E, T> {


	/**
	 *
	 * @return Number of genes.
	 */
	public int getNumberOfGenes();

	public void addGene(E geneType, T gene);

	/**
	 *
	 * @return A map of the chromosome.
	 */
	public Map<E,T> getGenes();

	public List<E> getGeneNames();

	/**
	 *
	 * @param geneType
	 * @return
	 */
	public double getGeneCurrentDoubleValue(E geneType);

	public boolean isGeneActive(E geneType);

	public void setGeneActive(E geneType, boolean active);

	public void setCurrentDoubleValue(E geneType, double currentValue);

	/**
	 *
	 */
	public void mutateChromosome();

	/**
	 * @param geneType
	 * @return
	 */
	public T getGene(E geneType);

	@Override
	public boolean equals(Object other);

	/**
	 * @return
	 */
	public IChromosome<E, T> getCopy();

}
