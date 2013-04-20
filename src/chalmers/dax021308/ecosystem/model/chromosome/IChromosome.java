/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.chromosome;


/**
 * 
 * @author Loanne Berggren
 *
 */
public interface IChromosome {

	public IChromosome crossChromosomes(final IChromosome other);

	public void mutateChromosome();

	public int getChromosomeSize();
	
	public Object clone();

	public boolean equals(Object other);
	
	/**
	 * 
	 * @return a clone of the chromosome.
	 */
	public Object getGenes();
	
	public boolean findGene(int geneID);

	public void setGene(int id, boolean allele);
	
	public void setAll(boolean allele);
	
	public double getMutationProbabilty();
	
	public int getNumberOfGenes();
}
