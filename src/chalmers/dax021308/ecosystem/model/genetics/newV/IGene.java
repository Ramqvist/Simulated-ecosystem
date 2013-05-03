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
	 * @return
	 */
	double getMutationProbaility();

	/**
	 * @param mutationProbaility
	 */
	void setMutationProbaility(double mutationProbability);

	/**
	 * @return
	 */
	boolean haveGene();

	/**
	 * @param hasGene
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
	 * @param minValue
	 */
	void setMinValue(double minValue);

	/**
	 * @return
	 */
	double getMaxValue();

	/**
	 * @param maxValue
	 */
	void setMaxValue(double maxValue);

	/**
	 * @return
	 */
	Object getCurrentValue();

	/**
	 * @param currentValue
	 */
	void setCurrentValue(Object currentValue);

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
	public void setHasRandomStartValue(boolean random);
	
}
