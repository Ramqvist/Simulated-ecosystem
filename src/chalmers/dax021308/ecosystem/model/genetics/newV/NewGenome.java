package chalmers.dax021308.ecosystem.model.genetics.newV;

import chalmers.dax021308.ecosystem.model.genetics.GeneralGeneTypes;

/**
 * 
 * @author Loanne Berggren
 *
 * @param <T> Type of genes
 */
public class NewGenome extends NewGenericGenome<GeneralGeneTypes, IGene> {

	/**
	 * Creates a new Genome.
	 * Use {@link #setGene(IGenes, boolean)} to change gene value.
	 * @param chromosome
	 */
	public NewGenome(NewIChromosome<GeneralGeneTypes, IGene> chromosome) {
		super(chromosome);
	}
	
	public NewGenome(){
		super(new NewChromosome());
	}
	
	
}

