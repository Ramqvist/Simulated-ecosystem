/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics.newV;

import java.util.List;

import chalmers.dax021308.ecosystem.model.genetics.GeneralGeneTypes;

/**
 * 
 * @author Loanne Berggren
 *
 */
public interface IChromosome <E, T> {
	/**
	 * 
	 * @return
	 */
	public double getMutationProbabilty();
	
	/**
	 * 
	 * @return
	 */
	public void setMutationProbabilty(double mutationProbability);
	
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
	public Object getGeneCurrentValue(E geneType);

	public void setCurrentValue(E geneType, Object currentValue);
	
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
