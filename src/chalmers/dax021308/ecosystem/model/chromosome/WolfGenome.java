package chalmers.dax021308.ecosystem.model.chromosome;


/**
 * 
 * @author Loanne Berggren
 *
 */
public class WolfGenome extends AbstractGenome<WolfGenes>{
	
	// Random value.
	private final double MUTATION_PROBABILITY = 0.1;
	
	/**
	 * Creates a new WolfGenome.
	 * Number of genes depends on WolfGenes.
	 * All genes are set to false initially. 
	 * Use {@link #setGene(WolfGenes, boolean)} to change gene value.
	 * @see {@link #WolfGenome(boolean, boolean)}
	 */
	public WolfGenome() {	
		this.chromosome = new BitSetChromosome(WolfGenes.getNumberOfGenes(), MUTATION_PROBABILITY);
	}
	
	/**
	 * Creates a new WolfGenome.
	 * @param stotting
	 * @param female
	 */
	public WolfGenome(boolean grouping, boolean female) {	
		this();
		this.setGene(WolfGenes.GROUPING, grouping);
		this.setGene(WolfGenes.FEMALE, female);
	}
	
	/*
	 * Used privately only.
	 */
	private WolfGenome(IChromosome chromosome) {
		this.chromosome = chromosome;
	}
	
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isGeneSet(WolfGenes gene) {
		return chromosome.findGene(gene.getValue());
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGene(WolfGenes gene, boolean allele) {
		this.chromosome.setGene(gene.getValue(), allele);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean equals(Object other) {
		WolfGenome o;
		if (other instanceof WolfGenome)
			o = (WolfGenome)other;
		else return false;
		if ( !this.chromosome.equals(o.getChromosomes()) )
			return false;		
		return true;
	}
	
	@Override
	protected IGenome<WolfGenes> genomeFactory(IChromosome chromosome) {
		return new WolfGenome(chromosome);
	}
	
}
