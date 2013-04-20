package chalmers.dax021308.ecosystem.model.chromosome;


/**
 * 
 * @author Loanne Berggren
 *
 */
public class WolfGenome extends AbstractGenome<WolfGenes>{
	
	private BitSetChromosome chromosome;
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
	public WolfGenome(boolean stotting, boolean female) {	
		this();
		this.setGene(WolfGenes.GROUPING, stotting);
		this.setGene(WolfGenes.FEMALE, female);
	}
	
	/*
	 * Used privately only.
	 */
	private WolfGenome(BitSetChromosome chromosome) {
		this.chromosome = chromosome;
	}
	
	/**
	 * {@inheritDoc}
	 * 
	 **/
	@Override
	public AbstractGenome<WolfGenes> mateWithMutation(AbstractGenome<WolfGenes> other){
		if (other instanceof WolfGenome) {
			BitSetChromosome nChrom = chromosome.crossChromosomes((BitSetChromosome)other.getChromosome());
			nChrom.mutateChromosome();
			AbstractGenome<WolfGenes> newGenome = new WolfGenome(nChrom);
			return newGenome;
		}
		return null;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public AbstractGenome<WolfGenes> onlyMutate(){
		BitSetChromosome nChrom = (BitSetChromosome) this.chromosome.clone();
		nChrom.mutateChromosome();
		AbstractGenome<WolfGenes> newGenome = new WolfGenome(nChrom);	
		return newGenome;
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
	public Object getChromosome(){
		return this.chromosome;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int numberOfGenes() {
		return WolfGenes.getNumberOfGenes();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void setGene(WolfGenes gene, boolean allele) {
		this.chromosome.setGene(gene.getValue(), allele);
	}

}
