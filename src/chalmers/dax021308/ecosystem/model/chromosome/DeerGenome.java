package chalmers.dax021308.ecosystem.model.chromosome;


/**
 * 
 * @author Loanne Berggren
 *
 */
public class DeerGenome extends AbstractGenome<DeerGenes>{
	
	private BitSetChromosome chromosome;
	private final double MUTATION_PROBABILITY = 0.1;
	
	public DeerGenome() {	
		this.chromosome = new BitSetChromosome(DeerGenes.getNumberOfGenes(), MUTATION_PROBABILITY);
	}
	
	private DeerGenome(BitSetChromosome chromosome) {
		this.chromosome = chromosome;
	}
	
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
	 * 
	 * @return a mutated version of this genome. The original genome is unchanged.
	 */
	@Override
	public AbstractGenome<DeerGenes> onlyMutate(){
		BitSetChromosome nChrom = (BitSetChromosome) this.chromosome.clone();
		nChrom.mutateChromosome();
		AbstractGenome<DeerGenes> newGenome = new DeerGenome(nChrom);	
		return newGenome;
	}
	
	@Override
	public boolean isGeneSet(DeerGenes gene) {
		return chromosome.findGene(gene.getValue());
	}
	
	@Override
	public Object getChromosome(){
		return this.chromosome;
	}

	@Override
	public int numberOfGenes() {
		return DeerGenes.getNumberOfGenes();
	}

	@Override
	public void setGene(DeerGenes gene, boolean allele) {
		this.chromosome.setGene(gene.getValue(), allele);
	}

}
