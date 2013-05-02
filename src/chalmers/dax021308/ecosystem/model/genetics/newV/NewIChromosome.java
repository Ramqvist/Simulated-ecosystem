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
public interface NewIChromosome <E, T> {
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
	 * @param other
	 * @return
	 */
	//NewIChromosome<E,N,T> crossChromosomes(NewIChromosome<E,N,T> other);

	/**
	 * 
	 */
	public void mutateChromosome();

	/**
	 * Checks if other and this are the same implementing class, have the same length and
	 * mutation probability. This method will not care about actual "gene" values.
	 * @param other
	 * @return true if they are of same "chromosome type", otherwise false;
	 */
	//public boolean isEqualType(NewIChromosome<E,N,T> other);

	public Object clone();

	/**
	 * @param geneType
	 * @return
	 */
	public T getGene(E geneType);

	/**
	 * @param gene
	 * @param minValue
	 * @param maxValue
	 * @param hasGene
	 * @param isMutable
	 * @param currentValue
	 * @param mutProb
	 */
	/*void changeGene(E gene, N minValue, N maxValue,
			boolean hasGene, boolean isMutable, N currentValue, double mutProb);
*/
	@Override
	public boolean equals(Object other);
	
}
