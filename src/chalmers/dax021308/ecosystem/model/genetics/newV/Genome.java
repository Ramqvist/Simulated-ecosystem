package chalmers.dax021308.ecosystem.model.genetics.newV;

import chalmers.dax021308.ecosystem.model.genetics.GeneralGeneTypes;

/**
 * 
 * @author Loanne Berggren
 *
 * @param <T> Type of genes
 */
public class Genome extends GenericGenome<GeneralGeneTypes, IGene> {

	/**
	 * Creates a new Genome.
	 * Use {@link #setGene(IGenes, boolean)} to change gene value.
	 * @param chromosome
	 */
	public Genome(IChromosome<GeneralGeneTypes, IGene> chromosome) {
		super(chromosome);
	}
	
	public Genome(){
		super(new Chromosome());
	}
	
	public IGenome<GeneralGeneTypes,IGene> onlyMutate(){
		IChromosome<GeneralGeneTypes,IGene> nChrom = this.chromosome.getCopy();
		nChrom.mutateChromosome();
		return new Genome(nChrom);
	}
	
	
}

