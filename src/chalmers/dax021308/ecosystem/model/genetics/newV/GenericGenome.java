package chalmers.dax021308.ecosystem.model.genetics.newV;

import java.util.List;


/**
 * 
 * @author Loanne Berggren
 *
 * @param <T> Type of genes
 */
public class GenericGenome<E extends Enum<E>, T extends IGene> implements IGenome<E,T>{
	protected IChromosome<E,T> chromosome;

	protected GenericGenome() {}
	
	/**
	 * Creates a new Genome.
	 * Use {@link #setGene(IGenes, boolean)} to change gene value.
	 * @param chromosome
	 */
	public GenericGenome(IChromosome<E,T> chromosome) {
		this.chromosome = (IChromosome<E, T>) chromosome.clone();
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public double getMutationProbability() {
		return this.chromosome.getMutationProbabilty();
	}
	

	@Override
	public void setMutationProbability(double mutationProbability) {
		this.chromosome.setMutationProbabilty(mutationProbability);
	}
	
	/**
	 * 
	 * @param gene
	 * @return		True if this is set. False if it is not.
	 */
	/*@Override
	public boolean hasGeneAllele(E geneType) {
		return this.chromosome.getGene(geneType).isHasGene();
	}*/
	
	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IGenome#getGeneValue(chalmers.dax021308.ecosystem.model.genetics.IGenes)
	 */
	@Override
	public Object getGeneCurrentValue(E geneType) {
		return this.chromosome.getGeneCurrentValue(geneType);
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IGenome#setGene(chalmers.dax021308.ecosystem.model.genetics.IGenes, int)
	 */
	@Override
	public void setGene(E geneType, T gene) {
		this.chromosome.addGene(geneType, gene);
	}

	/**
	 * @return {@inheritDoc}
	 */
	public Object getAllGenes(){
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
	
	/**
	 * @throws IllegalArgumentException if other is null
	 */
/*	@Override
	public IGenomeGeneric<E,N> mateWithMutation(final IGenomeGeneric<E,N> other){
		if (null == other) throw new IllegalArgumentException("other is null.");
		
		IChromosomeGeneric<E,N,T> nChrom = chromosome.crossChromosomes((IChromosomeGeneric<E, N, T>) other.getAllGenes());
		nChrom.mutateChromosome();
		return new NewGenericGenome<E,N,T>(nChrom);
	}*/


	/**
	 * <p>
	 * @return 	A mutated version of this genome. 
	 * 			The original genome is unchanged hopefully.
	 */
	@Override
	public IGenome<E,T> onlyMutate(){
		IChromosome<E,T> nChrom = (IChromosome<E,T>) this.chromosome.clone();
		nChrom.mutateChromosome();
		return new GenericGenome<E,T>(nChrom);
	}


	
	/**
	 * @throws CloneNotSupportedException
	 */
	@Override 
	public Object clone() throws CloneNotSupportedException {
		throw new CloneNotSupportedException();
	}



	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IntAndAbs.IGenomeGeneric#getGene(java.lang.Enum)
	 */
	@Override
	public T getGene(E geneType){
		return this.chromosome.getGene(geneType);
	}

}

