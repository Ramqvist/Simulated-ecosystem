/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics;


/**
 * 
 * @author Loanne Berggren
 *
 */
public interface IChromosome {

	/**
	 * 
	 * @return
	 */
	public double getMutationProbabilty();
	
	/**
	 * 
	 * @return The length of the chromosome.
	 */
	public int getChromosomeLength();
	
	/**
	 * 
	 * @return A clone of the chromosome.
	 */
	public Object getGenes();
	
	/**
	 * 
	 * @param geneID
	 * @return
	 */
	public boolean getValue(int index);
	
	/**
	 * 
	 * @param index
	 * @param length
	 * @return
	 */
	public boolean[] getSegment(int index, int length);

	/**
	 * 
	 * @param id
	 * @param allele
	 */
	public void setValue(int index, boolean value);
	
	/**
	 * 
	 * @param id
	 * @param allele
	 */
	public void setSegment(int index, boolean[] segment);
	
	/**
	 * 
	 * @param allele
	 */
	public void setAll(boolean value);
	
	/**
	 * 
	 * @param other
	 * @return
	 */
	public IChromosome crossChromosomes(final IChromosome other);

	/**
	 * 
	 */
	public void mutateChromosome();

	
	public Object clone();
}
