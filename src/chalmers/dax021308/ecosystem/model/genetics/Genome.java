package chalmers.dax021308.ecosystem.model.genetics;


/**
 * 
 * @author Loanne Berggren
 *
 * @param <T> Type of genes
 */
public class Genome<T extends IGenes> implements IGenome<T> {

	protected IChromosome chromosome;

	/**
	 * Creates a new Genome.
	 * All genes are set to false initially. 
	 * Use {@link #setGene(IGenes, boolean)} to change gene value.
	 * @param size
	 * @param mutprob
	 */
	/*public Genome(int size, double mutprob) {
		this(new BitSetChromosome(size, mutprob));
	}*/
	
	/**
	 * Creates a new Genome.
	 * Use {@link #setGene(IGenes, boolean)} to change gene value.
	 * @param chromosome
	 */
	public Genome(IChromosome chromosome) {
		this.chromosome = chromosome;
	}
	

	/**
	 * Crosses this genome with other genome. The new genome might also be mutated.
	 * The original genomes are unchanged hopefully.
	 * <p>
	 * @param other
	 * @return The baby genome.
	 */
	@Override
	public IGenome<T> mateWithMutation(final IGenome<T> other){
			IChromosome nChrom = chromosome.crossChromosomes((IChromosome)other.getChromosomes());
			nChrom.mutateChromosome();
			return new Genome<T>(nChrom);
	}


	/**
	 * <p>
	 * @return 	A mutated version of this genome. 
	 * 			The original genome is unchanged hopefully.
	 */
	@Override
	public IGenome<T> onlyMutate(){
		IChromosome nChrom = (IChromosome) this.chromosome.clone();
		nChrom.mutateChromosome();
		return new Genome<T>(nChrom);
	}
	
	/**
	 * 
	 * @param gene
	 * @return		True if this is set. False if it is not.
	 */
	@Override
	public boolean isGeneSet(T gene) {
		// TODO Loanne. More than one bit gene.
		//this.chromosome.getSegment(gene.getGeneStartIndex(), gene.getGeneSize());
		return this.chromosome.getValue(gene.getGeneStartIndex());
	}

	/**
	 * @return {@inheritDoc}
	 */
	@Override
	public Object getChromosomes(){
		return this.chromosome;
	}

	/**
	 * 
	 * @return Number of genes.
	 */
	@Override
	public int numberOfGenes(){
		// TODO Loanne numberOfGenes() Maybe delete.
		
		return 0;
	}
	
	@Override
	public int length(){
		return this.chromosome.getChromosomeLength();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getMutationProbability() {
		return this.chromosome.getMutationProbabilty();
	}
	
	/**
	 * 
	 * @param gene	The gene to set.
	 * @param allele	True if gene should be set, otherwise false.
	 */
	@Override
	public void setGene(T gene, boolean allele){
		this.chromosome.setValue(gene.getGeneStartIndex(), allele);
	}
		
	/**
	 * @throws CloneNotSupportedException
	 */
	@Override 
	public Object clone() throws CloneNotSupportedException {
	    throw new CloneNotSupportedException();
	}

	/* (non-Javadoc)
	 * eclipse generated method
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((this.chromosome == null) ? 0 : this.chromosome.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * eclipse generated method
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Genome)) {
			return false;
		}
		Genome<?> other = (Genome<?>) obj;
		if (this.chromosome == null) {
			if (other.chromosome != null) {
				return false;
			}
		} else if (!this.chromosome.equals(other.chromosome)) {
			return false;
		}
		return true;
	}
	
}

