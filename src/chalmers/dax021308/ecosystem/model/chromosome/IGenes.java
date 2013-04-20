/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.chromosome;

import java.util.EnumSet;

/**
 * @author Loanne Berggren
 *
 */
//public interface IGenes<E extends Enum<E>> {
public interface IGenes {
	/**
	 * 
	 * @return Number of genes.
	 */
	public int getNumberOfGenes();
	
	/**
	 * 
	 * @return The names of the available genes.
	 */
	//public EnumSet<E> getGeneNames();
	
	public abstract int getIndex();
	public abstract int getSize();
	public abstract int getTotalSize();
	
	//public abstract IGenes<E> genesFactory();
}
