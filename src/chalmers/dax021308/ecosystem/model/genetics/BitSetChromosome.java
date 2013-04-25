package chalmers.dax021308.ecosystem.model.genetics;

import java.util.BitSet;
import java.util.Random;


/**
 * 
 * @author Loanne Berggren
 *
 */
public class BitSetChromosome implements IChromosome{
// TODO Loanne update for longer genes
	private final double MUTATION_PROBABILITY;
	private BitSet chromosome;
	private int length;
	
	public BitSetChromosome(int length, double mutationProbability) {
		super();
		this.length = length;
		this.chromosome = new BitSet(length);
		MUTATION_PROBABILITY = mutationProbability;
	}
	
	public BitSetChromosome(BitSet chromosome, int length, double mutationProbability) {
		super();
		this.length = length;
		this.chromosome = chromosome;
		MUTATION_PROBABILITY = mutationProbability;
	}
	
	@Override
	public double getMutationProbabilty() {
		return this.MUTATION_PROBABILITY;
	}

	@Override
	public int getChromosomeLength() {
		return this.length;
	}
		
	@Override
	public Object getGenes() {
		return this.chromosome.clone();
	}
	
	@Override
	public boolean getValue(int index) {
		return this.chromosome.get(index);
	}

	public boolean[] getSegment(int index, int length){
		// TODO Loanne getSegment(...)
		return null;
	}

	@Override
	public void setValue(int index, boolean value){
		this.chromosome.set(index, value);
	}
	
	@Override
	public void setSegment(int index, boolean[] segment){
		// TODO Loanne
	}
	
	@Override
	public void setAll(boolean value){
		this.chromosome.set(0, this.length, value);
	}

	@Override
	public IChromosome crossChromosomes(final IChromosome other) {

		if (other == null)
			throw new IllegalArgumentException("other is null.");
		if (!(other instanceof BitSetChromosome)){
			throw new IllegalArgumentException("other is not of the same class.");
		}
		// Not the same chromosome size, meaning it's definitively not the same species.
		if ( other.getChromosomeLength() != this.getChromosomeLength())
			return null;
		
		BitSetChromosome theMate = (BitSetChromosome)other;
		BitSet nChrom = new BitSet(length);
		

		// Crossover point chosen randomly
		Random randomGen = new Random();
		//int crossoverPoint = randomGen.nextInt(this.length); // TODO Loanne
		int crossoverPoint = 1; // temp should be the above.
		nChrom.or(this.chromosome);
		nChrom.set(crossoverPoint, this.length); // set rest to false
	
		BitSet temp = (BitSet)theMate.getGenes();
		temp.clear(0, crossoverPoint); // if 1111 -> 0011, if crossover = 2
		
		nChrom.or(temp);
		
		return new BitSetChromosome(nChrom, this.length, MUTATION_PROBABILITY);
	}

	@Override
	public void mutateChromosome() {
		// mutation probability Pmut
				// for each gene
					// random number r [0,1]
					// mutate if r < Pmut
		Random randomGen = new Random();
		double rand;
		
		for ( int bitIndex = 0; bitIndex < this.length; ++bitIndex ) {
			rand = randomGen.nextDouble();
			if (rand < MUTATION_PROBABILITY) {
				// 0 -> 1 , 1 -> 0
				chromosome.flip(bitIndex);
			}
		}
	}

	@Override
	public Object clone() {
		return new BitSetChromosome((BitSet)chromosome.clone(), this.length, MUTATION_PROBABILITY);
	}


	/* (non-Javadoc)
	 * eclipse generated method.
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.MUTATION_PROBABILITY);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result
				+ ((this.chromosome == null) ? 0 : this.chromosome.hashCode());
		result = prime * result + this.length;
		return result;
	}

	/* (non-Javadoc)
	 * eclipse generated method.
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
		if (!(obj instanceof BitSetChromosome)) {
			return false;
		}
		BitSetChromosome other = (BitSetChromosome) obj;
		if (Double.doubleToLongBits(this.MUTATION_PROBABILITY) != Double
				.doubleToLongBits(other.MUTATION_PROBABILITY)) {
			return false;
		}
		if (this.chromosome == null) {
			if (other.chromosome != null) {
				return false;
			}
		} else if (!this.chromosome.equals(other.chromosome)) {
			return false;
		}
		if (this.length != other.length) {
			return false;
		}
		return true;
	}

}
