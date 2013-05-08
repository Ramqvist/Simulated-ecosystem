/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics.newV;

import java.util.List;


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
	 * @return A clone of the chromosome.
	 */
	public Object getGenes();

	public List<E> getGeneNames();

	/**
	 *
	 * @param geneType
	 * @return
	 */
	public double getGeneCurrentDoubleValue(E geneType);

	public boolean isGeneActive(E geneType);

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
