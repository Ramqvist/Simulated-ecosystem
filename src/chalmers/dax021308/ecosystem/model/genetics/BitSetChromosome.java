package chalmers.dax021308.ecosystem.model.genetics;

import java.util.BitSet;
import java.util.Random;


/**
 * 
 * @author Loanne Berggren
 *
 */
public class BitSetChromosome implements IChromosome{

	private final double MUTATION_PROBABILITY;
	private BitSet chromosome;
	private int numberOfGenes;
	
	public BitSetChromosome(int numOfGenes, double mutationProbability) {
		super();
		this.numberOfGenes = numOfGenes;
		this.chromosome = new BitSet(numOfGenes);
		MUTATION_PROBABILITY = mutationProbability;
	}
	
	public BitSetChromosome(BitSet chromosome, int numOfGenes, double mutationProbability) {
		super();
		this.numberOfGenes = numOfGenes;
		this.chromosome = chromosome;
		MUTATION_PROBABILITY = mutationProbability;
	}
	
	@Override
	public BitSetChromosome crossChromosomes(final IChromosome other) {

		// Not the same chromosome size, meaning it's definitively not the same species.
		if ( other.getChromosomeSize() != this.getChromosomeSize())
			return null;
		
		
		BitSet nChrom = new BitSet(numberOfGenes);
		//IChromosome newChromosome = new Chromosome1dArray(this.chromosome.length);
		
		// Chromosome should be of the same type (length)
		// Crossover point chosen randomly
		// cross
		// return new chromosome
		
		Random randomGen = new Random();
		//int crossoverPoint = randomGen.nextInt(chromosome.size());
		int crossoverPoint = 1; // temp should be the above.
		nChrom.or(this.chromosome);
		
		nChrom.set(crossoverPoint, getChromosomeSize()); // set rest to false
		
		BitSetChromosome theMate = (BitSetChromosome)other;
		BitSet temp = (BitSet)theMate.getGenes();
		temp.clear(0, crossoverPoint); // if 1111 -> 0011, if crossover = 2
		
		nChrom.or(temp);
		
		return new BitSetChromosome(nChrom, this.numberOfGenes, MUTATION_PROBABILITY);
	}

	@Override
	public void mutateChromosome() {
		// mutation probability Pmut
				// for each gene
					// random number r [0,1]
					// mutate if r < Pmut
		Random randomGen = new Random();
		double rand;
		
		for ( int bitIndex = 0; bitIndex < this.numberOfGenes; ++bitIndex ) {
			rand = randomGen.nextDouble();
			if (rand < MUTATION_PROBABILITY) {
				// 0 -> 1 , 1 -> 0
				chromosome.flip(bitIndex);
			}
		}
	}

	@Override
	public int getChromosomeSize() {
		return this.numberOfGenes;
	}
	
	@Override
	public Object clone() {
		return new BitSetChromosome((BitSet)chromosome.clone(), this.numberOfGenes, MUTATION_PROBABILITY);
	}

	@Override
	public boolean equals(Object other) {
		BitSetChromosome o;
		if (other instanceof BitSetChromosome)
			o = (BitSetChromosome)other;
		else return false;
		if ( this.numberOfGenes != o.getChromosomeSize() )
			return false;
		if ( this.MUTATION_PROBABILITY != o.getMutationProbabilty() )
			return false;
		if ( !this.chromosome.equals(o.getGenes()) )
			return false;
		
		return true;
	}
	
	@Override
	public Object getGenes() {
		return this.chromosome.clone();
	}
	
	@Override
	public boolean findGene(int geneID) {
		return this.chromosome.get(geneID);
	}

	@Override
	public void setGene(int id, boolean allele){
		this.chromosome.set(id, allele);
	}
	
	@Override
	public void setAll(boolean allele){
		this.chromosome.set(0, this.numberOfGenes, allele);
	}

	@Override
	public double getMutationProbabilty() {
		return this.MUTATION_PROBABILITY;
	}

	@Override
	public int getNumberOfGenes() {
		return this.numberOfGenes;
	}

}
