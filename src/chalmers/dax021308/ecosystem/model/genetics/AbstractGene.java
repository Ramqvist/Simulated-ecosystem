/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics;

import java.util.Random;

/**
 * @author Loanne Berggren
 *
 */
public abstract class AbstractGene implements IGene {
	protected final double mutationProbabilityMin = 0.0;
	protected final double mutationProbabilityMax = 1.0;
	protected double mutationProbability = 0.1;
	protected boolean isMutable = false;
	protected boolean randomStartValue = false;
	protected boolean geneActiveStatus = true;
	protected static Random randomGenerator = new Random();

	private static NullGene nullGene;
	public static IGene newNullGene(){
		if (nullGene == null)
			nullGene = new NullGene();

		return nullGene;
	}

	/**
	 *
	 */
	public abstract void mutate();

	/**
	 * @param currentValue
	 */
	public void setCurrentDoubleValue(double currentValue){
		// nothing
	}

	/**
	 * @return
	 */
	public double getCurrentDoubleValue(){
		return 0.0;
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#getMutationProbaility()
	 */
	@Override
	public double getMutationProbability() {
		return this.mutationProbability;
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#setMutationProbaility(double)
	 */
	@Override
	public void setMutationProbability(double mutationProbability) {
		// Validate minMutProb <= mutationProbability <= maxMutProb
		if ( (mutationProbability < mutationProbabilityMin) || (mutationProbability > mutationProbabilityMax) ) {
			throw new IllegalArgumentException("Mutation probability should be in range " +
					mutationProbabilityMin + "-" + mutationProbabilityMax);
		}
		this.mutationProbability = mutationProbability;
	}

	/**
	 * @return the haveGene
	 */
	@Override
	public boolean isGeneActive() {
		return this.geneActiveStatus;
	}

	/**
	 * @param hasGene
	 *            the hasGene to set
	 */
	@Override
	public void setHaveGene(boolean haveGene) {
		this.geneActiveStatus = haveGene;
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#isMutable()
	 */
	@Override
	public boolean isMutable(){
		return this.isMutable;
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#setMutable(boolean)
	 */
	@Override
	public void setMutable(boolean isMutable) {
		this.isMutable = isMutable;
	}

	@Override
	public boolean hasRandomStartValue() {
		return this.randomStartValue;
	}

	@Override
	public void setRandomStartValue(boolean random) {
		this.randomStartValue = random;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(this.mutationProbabilityMax);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(this.mutationProbabilityMin);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AbstractGene other = (AbstractGene) obj;
		if (Double.doubleToLongBits(this.mutationProbabilityMax) != Double
				.doubleToLongBits(other.mutationProbabilityMax))
			return false;
		if (Double.doubleToLongBits(this.mutationProbabilityMin) != Double
				.doubleToLongBits(other.mutationProbabilityMin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuffer sB = new StringBuffer();
		sB.append("Active: " + geneActiveStatus + "\t");
		sB.append("Mutable: " + isMutable + "\t");
		sB.append("Random start: " + randomStartValue + "\t");
		return sB.toString();
	}

}
