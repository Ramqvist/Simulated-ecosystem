package chalmers.dax021308.ecosystem.model.genetics;

/**
 * 
 * @author Loanne Berggren
 *
 */
public class DeerGenome extends AbstractGenome<DeerGenes>{
	
	// Random value.
	private static final double MUTATION_PROBABILITY = 0.05;
	
	/**
	 * Creates a new DeerGenome.
	 * Number of genes depends on DeerGenes.
	 * All genes are set to false initially. 
	 * Use {@link #setGene(DeerGenes, boolean)} to change gene value.
	 * 
	 */

	public DeerGenome() {
		super(new BitSetChromosome(DeerGenes.getNumberOfGenes(), MUTATION_PROBABILITY));
	}
	
	public DeerGenome(boolean stotting, boolean grouping) {	
		this();
		this.setGene(DeerGenes.STOTTING, stotting);
		this.setGene(DeerGenes.GROUPING, grouping);

	}
	
	/*
	 * Used privately only.
	 */
	private DeerGenome(IChromosome chromosome) {
		super(chromosome);
	}

	/**
	 * {@inheritDoc}
	 * 
	 **/
	@Override
	public boolean isGeneSet(DeerGenes gene) {
		return this.chromosome.findGene(gene.getIndex());
	}

	/**
	 * 
	 * @param gene	The gene of type DeerGenes to set.
	 * @param allele	{@inheritDoc}
	 */
	@Override
	public void setGene(DeerGenes gene, boolean allele) {
		this.chromosome.setGene(gene.getIndex(), allele);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other) {
		DeerGenome o;
		if (other instanceof DeerGenome)
			o = (DeerGenome)other;
		else return false;
		if ( !this.chromosome.equals(o.getChromosomes()) )
			return false;		
		return true;
	}
	
	@Override
	protected IGenome<DeerGenes> genomeFactory(IChromosome chromosome) {
		return new DeerGenome(chromosome);
	}
}
