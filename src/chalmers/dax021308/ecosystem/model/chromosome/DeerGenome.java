package chalmers.dax021308.ecosystem.model.chromosome;


/**
 * 
 * @author Loanne Berggren
 *
 */
public class DeerGenome extends AbstractGenome<DeerGenes>{
	
	private BitSetChromosome chromosome;
	// Random value.
	private final double MUTATION_PROBABILITY = 0.05;
	
	/**
	 * Creates a new DeerGenome.
	 * Number of genes depends on DeerGenes.
	 * All genes are set to false initially. 
	 * Use {@link #setGene(DeerGenes, boolean)} to change gene value.
	 * @see {@link #DeerGenome(boolean, boolean)}
	 */
	public DeerGenome() {	
		this.chromosome = new BitSetChromosome(DeerGenes.getNumberOfGenes(), MUTATION_PROBABILITY);
	}
	
	/**
	 * Creates a new DeerGenome.
	 * @param stotting
	 * @param female
	 */
	public DeerGenome(boolean stotting, boolean grouping) {	
		this();
		this.setGene(DeerGenes.STOTTING, stotting);
		this.setGene(DeerGenes.GROUPING, grouping);
	}
	
	/*
	 * Used privately only.
	 */
	private DeerGenome(BitSetChromosome chromosome) {
		this.chromosome = chromosome;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 **/
	@Override
	public AbstractGenome<DeerGenes> mateWithMutation(AbstractGenome<DeerGenes> other){
		if (other instanceof DeerGenome) {
			BitSetChromosome nChrom = chromosome.crossChromosomes((BitSetChromosome)other.getChromosome());
			nChrom.mutateChromosome();
			AbstractGenome<DeerGenes> newGenome = new DeerGenome(nChrom);
			return newGenome;
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractGenome<DeerGenes> onlyMutate(){
		BitSetChromosome nChrom = (BitSetChromosome) this.chromosome.clone();
		nChrom.mutateChromosome();
		AbstractGenome<DeerGenes> newGenome = new DeerGenome(nChrom);	
		return newGenome;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean isGeneSet(DeerGenes gene) {
		return chromosome.findGene(gene.getValue());
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object getChromosome(){
		return this.chromosome;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int numberOfGenes() {
		return DeerGenes.getNumberOfGenes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGene(DeerGenes gene, boolean allele) {
		this.chromosome.setGene(gene.getValue(), allele);
	}

}
