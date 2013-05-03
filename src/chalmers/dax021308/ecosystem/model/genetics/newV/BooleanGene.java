package chalmers.dax021308.ecosystem.model.genetics.newV;

import java.util.Random;

/**
 * 
 * @author Loanne Berggren
 * 
 */
public class BooleanGene extends AbstractGene {
	protected boolean haveGene = true;

	public BooleanGene() {}

	public BooleanGene(boolean haveGene, double mutationProbability, boolean isMutable, boolean randomStartValue) {
		this.randomStartValue = randomStartValue;
		if(this.randomStartValue) {
			if(Math.random()<0.5){
				this.haveGene = false;
			} else {
				this.haveGene = true;
			}
		} else {
			this.haveGene = haveGene;
		}
		
		this.mutationProbability = mutationProbability;
		this.isMutable = isMutable;
	}
	
	private BooleanGene(BooleanGene toCopy) {
		this(toCopy.haveGene,toCopy.mutationProbability,toCopy.isMutable, toCopy.randomStartValue);
	}

	/**
	 * @return the haveGene
	 */
	public boolean haveGene() {
		return this.haveGene;
	}

	/**
	 * @param hasGene
	 *            the hasGene to set
	 */
	public void setHaveGene(boolean haveGene) {
		this.haveGene = haveGene;
	}

	/**
	 * @return the currentValue
	 */
	@Override
	public Object getCurrentValue() {
		return this.haveGene;
	}

	/**
	 * @param currentValue
	 *            the currentValue to set
	 */
	@Override
	public void setCurrentValue(Object currentValue) {
		this.haveGene = ((Boolean)currentValue).booleanValue();
	}

	/**
	 * Override
	 */
	@Override
	public void mutate() {
		if (!isMutable)
			return;
		Random randomGen = new Random();
		double rand;
		rand = randomGen.nextDouble();
		if (rand < mutationProbability) {
			haveGene = !haveGene;
		}
	}
	
	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#getCopy()
	 */
	@Override
	public IGene getCopy() {
		return new BooleanGene(this);
	}

	@Override
	public double getMinValue() {
		return 0;
	}

	@Override
	public double getMaxValue() {
		return 0;
	}

}