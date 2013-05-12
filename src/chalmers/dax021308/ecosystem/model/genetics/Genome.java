package chalmers.dax021308.ecosystem.model.genetics;

import java.util.Map;


/**
 *
 * @author Loanne Berggren
 *
 * @param <T> Type of genes
 */
public class Genome implements IGenome<GeneralGeneTypes, IGene> {

	private IChromosome<GeneralGeneTypes,IGene> chromosome;

	/**
	 * Creates a new Genome.
	 * Use {@link #setGene(GeneralGeneTypes, IGene)} to change gene value.
	 * @param chromosome
	 */
	public Genome(IChromosome<GeneralGeneTypes, IGene> chromosome) {
		this.chromosome = chromosome;
	}

	public Genome(){
		this.chromosome = new Chromosome();
	}

	/**
	 * <p>
	 * @return 	A mutated version of this genome.
	 * 			The original genome is unchanged hopefully.
	 */
	public IGenome<GeneralGeneTypes,IGene> onlyMutate(){
		IChromosome<GeneralGeneTypes,IGene> nChrom = this.chromosome.getCopy();
		nChrom.mutateChromosome();
		return new Genome(nChrom);
	}

	public IGenome<GeneralGeneTypes, IGene> getCopy(){
		return new Genome(this.chromosome.getCopy());
	}


	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IGenome#getGeneValue(chalmers.dax021308.ecosystem.model.genetics.IGenes)
	 */
	@Override
	public Object getGeneCurrentValue(GeneralGeneTypes geneType) {
		return this.chromosome.getGeneCurrentDoubleValue(geneType);
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IGenome#setGene(chalmers.dax021308.ecosystem.model.genetics.IGenes, int)
	 */
	@Override
	public void setGene(GeneralGeneTypes geneType, IGene gene) {
		this.chromosome.addGene(geneType, gene);
	}

	/**
	 * @return {@inheritDoc}
	 */
	public Map<GeneralGeneTypes, IGene> getAllGenes(){
		return this.chromosome.getGenes();
	}

	/**
	 *
	 * @return Number of genes.
	 */
	@Override
	public int numberOfGenes(){
		return this.chromosome.getNumberOfGenes();
	}



	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IntAndAbs.IGenomeGeneric#getGene(java.lang.Enum)
	 */
	@Override
	public IGene getGene(GeneralGeneTypes geneType){
		return this.chromosome.getGene(geneType);
	}

}

