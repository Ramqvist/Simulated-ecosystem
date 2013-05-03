/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics.newV;

/**
 * @author Loanne Berggren
 *
 */
public abstract class AbstractGene implements IGene {
	protected double mutationProbability = 0.1;
	protected boolean isMutable = true;
	
	/**
	 * 
	 */
	public abstract void mutate();

	/**
	 * @param currentValue
	 */
	public abstract void setCurrentValue(Object currentValue);

	/**
	 * @return
	 */
	public abstract Object getCurrentValue();
	
	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#getMutationProbaility()
	 */
	@Override
	public double getMutationProbaility() {
		return this.mutationProbability;
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#setMutationProbaility(double)
	 */
	@Override
	public void setMutationProbaility(double mutationProbability) {
		this.mutationProbability = mutationProbability;
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#hasGene()
	 */
	@Override
	public boolean haveGene() {
		throw new UnsupportedOperationException("haveGene");
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#setHasGene(boolean)
	 */
	@Override
	public void setHaveGene(boolean haveGene) {
		throw new UnsupportedOperationException("setHaveGene");
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

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#setMinValue(double)
	 */
	@Override
	public void setMinValue(double minValue) {
		throw new UnsupportedOperationException("setMinValue");
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#setMaxValue(double)
	 */
	@Override
	public void setMaxValue(double maxValue) {
		throw new UnsupportedOperationException("setMaxValue");
	}

}
