package chalmers.dax021308.ecosystem.model.genetics.newV;

import java.util.Random;

/**
 * 
 * @author Loanne Berggren
 * 
 */
public class BooleanGene extends AbstractGene {
	//protected double mutationProbability = 0.1;
	protected boolean haveGene = true;

	public BooleanGene() {}

	public BooleanGene(boolean haveGene, double mutationProbability, boolean isMutable) {
		this.haveGene = haveGene;
		this.mutationProbability = mutationProbability;
		this.isMutable = isMutable;
	}
	
	private BooleanGene(BooleanGene toCopy) {
		this.mutationProbability = toCopy.mutationProbability;
		this.haveGene = toCopy.haveGene;
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
		this.haveGene = (boolean)currentValue;
	}

	/**
	 * Override
	 */
	@Override
	public void mutate() {
		
		Random randomGen = new Random();
		double rand;
		rand = randomGen.nextDouble();
		if (rand < mutationProbability) {
			haveGene = !haveGene;
		}
	}
	
	public Object clone(){
		return new BooleanGene(this);
	}

}