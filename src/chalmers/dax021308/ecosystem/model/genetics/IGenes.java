/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics;




/**
 * @author Loanne Berggren
 *
 */
public interface IGenes {
	/**
	 * 
	 * @return Number of genes.
	 */
	public abstract int getNumberOfGenes();
	
	/**
	 * 
	 * @return Total size of genes.
	 */
	public abstract int getTotalLengthOfGenes();
	
	/**
	 * 
	 * @return
	 */
	public abstract int getGeneStartIndex();
	
	/**
	 * 
	 * @return
	 */
	public abstract int getGeneSize();
	
	/**
	 * 
	 * @return
	 */
	public abstract double getMutationProbability();
	
}
