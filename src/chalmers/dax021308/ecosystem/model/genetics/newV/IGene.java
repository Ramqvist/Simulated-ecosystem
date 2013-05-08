/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics.newV;

/**
 * @author Loanne Berggren
 *
 */
public interface IGene {

	/**
	 * Mutation probability specifies the probability that a gene mutates.
	 * @return Mutation Probability
	 */
	public abstract double getMutationProbability();

	/**
	 * Mutation probability specifies the probability that a gene mutates.
	 * Set to 0 if no mutation should occur.
	 * @param mutationProbability
	 */
	public abstract void setMutationProbability(double mutationProbability);

	/**
	 * @return
	 */
	boolean isGeneActive();

	/**
	 * @param haveGene
	 */
	void setHaveGene(boolean haveGene);

	/**
	 * @return
	 */
	boolean isMutable();

	/**
	 * @param isMutable
	 */
	void setMutable(boolean isMutable);

	/**
	 * @return
	 */
	double getMinValue();

	/**
	 * Bug if minValue is higher than current max value
	 * @see setValueRange(...)
	 * @param minValue
	 */
	@Deprecated
	void setMinValue(double minValue);

	/**
	 * Prefer this
	 * @param min
	 * @param max
	 */
	void setValueRange(double min, double max);

	/**
	 * @return
	 */
	double getMaxValue();

	/**
	 * Bug if maxValue is lower than current min value
	 * @see setValueRange(...)
	 * @param maxValue
	 */
	@Deprecated
	void setMaxValue(double maxValue);

	/**
	 * @return
	 */
	double getCurrentDoubleValue();

	/**
	 * @param currentValue
	 */
	void setCurrentDoubleValue(double currentValue) throws IllegalArgumentException;

	/**
	 *
	 */
	void mutate();

	/**
	 *
	 * @return a copy of the gene.
	 */
	public IGene getCopy();

	/**
	 *
	 * @return true if the starting value of the gene should be random.
	 */
	public boolean hasRandomStartValue();

	/**
	 *
	 * @param random true if the start value should be random.
	 */
	public void setRandomStartValue(boolean random);

	public void randomizeValue();

	/**
	 * Perhaps mostly for testing.
	 * @param o
	 * @return
	 */
	public boolean hasEqualValues(IGene o);

}
