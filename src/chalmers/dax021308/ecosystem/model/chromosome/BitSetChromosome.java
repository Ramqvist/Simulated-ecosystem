package chalmers.dax021308.ecosystem.model.chromosome;

import java.util.BitSet;
import java.util.Random;


/**
 * 
 * @author Loanne Berggren
 *
 */
public class BitSetChromosome {

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
	
	public BitSetChromosome crossChromosomes(final BitSetChromosome other) {

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

	public int getChromosomeSize() {
		return this.numberOfGenes;
	}
	
	public Object clone() {
		return new BitSetChromosome((BitSet)chromosome.clone(), this.numberOfGenes, MUTATION_PROBABILITY);
	}

	public boolean equals(BitSetChromosome other) {
		if ( this.numberOfGenes != other.getChromosomeSize() )
			return false;
		if ( !this.chromosome.equals(other.getGenes()) )
			return false;
		
		return true;
	}
	
	/**
	 * 
	 * @return a clone of the chromosome.
	 */
	public Object getGenes() {
		return this.chromosome.clone();
	}
	
	public boolean findGene(int geneID) {
		return this.chromosome.get(geneID);
	}

	public void setGene(int id, boolean allele){
		this.chromosome.set(id, allele);
	}
	
	public void setAll(boolean allele){
		this.chromosome.set(0, this.numberOfGenes, allele);
	}

}
