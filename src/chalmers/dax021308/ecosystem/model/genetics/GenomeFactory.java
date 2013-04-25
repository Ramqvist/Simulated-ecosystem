/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics;


/**
 * @author Loanne Berggren
 *
 */
public class GenomeFactory {	
	public static IGenome<IGenes> deerGenomeFactory(){
		IChromosome chrom = new BitSetChromosome(DeerGenes.totalLengthOfGenes(), DeerGenes.MUTATION_PROBABILITY); 
		return new Genome<IGenes>(chrom);
	}
	
	public static IGenome<IGenes> wolfGenomeFactory(){
		IChromosome chrom = new BitSetChromosome(WolfGenes.totalLengthOfGenes(), WolfGenes.MUTATION_PROBABILITY); 
		return new Genome<IGenes>(chrom);
	}
}	

