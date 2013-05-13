package chalmers.dax021308.ecosystem.model.genetics;

import java.util.Random;

/**
 *
 * @author Loanne Berggren
 *
 */
public class BooleanGene extends AbstractGene {

	/**
	 * standard mutation probability, random start value
	 */
	public BooleanGene(){
		this(true, 0.1, true, true);
	}

	/**
	 * generates random start value
	 * @param mutationProbability
	 * @param isMutable
	 */
	public BooleanGene(double mutationProbability, boolean isMutable) {
		this(true, mutationProbability, isMutable, true);
	}

	public BooleanGene(boolean geneActive, double mutationProbability, boolean isMutable, boolean randomStartValue) {
		this.randomStartValue = randomStartValue;
		if(randomStartValue) {
			this.geneActiveStatus = randomGenerator.nextBoolean();
		} else {
			this.geneActiveStatus = geneActive;
		}

		this.setMutationProbability(mutationProbability); // error checks
		this.isMutable = isMutable;
	}

	private BooleanGene(BooleanGene toCopy) {
		// false randomStartValue, otherwise it the copy will randomize
		this(toCopy.geneActiveStatus,toCopy.mutationProbability,toCopy.isMutable, false);
		this.randomStartValue = toCopy.randomStartValue;
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
			geneActiveStatus = !geneActiveStatus;
		}
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#randomizeStartValue(boolean)
	 */
	@Override
	public void randomizeValue() {
		geneActiveStatus = randomGenerator.nextBoolean();
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#getCopy()
	 */
	@Override
	public IGene getCopy() {
		return new BooleanGene(this);
	}

	/* (non-Javadoc)
	 * Perhaps mostly for testing.
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#hasEqualValues(chalmers.dax021308.ecosystem.model.genetics.newV.IGene)
	 */
	@Override
	public boolean hasEqualValues(IGene o) {
		if (this == o)
			return true;
		if (!super.equals(o))
			return false;
		if (getClass() != o.getClass())
			return false;
		BooleanGene other = (BooleanGene) o;

		if (Double.doubleToLongBits(this.mutationProbability) != Double
				.doubleToLongBits(other.mutationProbability))
			return false;
		if (this.geneActiveStatus != other.geneActiveStatus)
			return false;
		if (this.isMutable != other.isMutable)
			return false;
		if (Double.doubleToLongBits(this.mutationProbabilityMax) != Double
				.doubleToLongBits(other.mutationProbabilityMax))
			return false;
		if (Double.doubleToLongBits(this.mutationProbabilityMin) != Double
				.doubleToLongBits(other.mutationProbabilityMin))
			return false;

		return true;
	}

	// not useful for this class

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#getMinValue()
	 */
	@Override
	public double getMinValue() { return 0; }

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#setMinValue(double)
	 */
	@Override
	public void setMinValue(double minValue) {}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#getMaxValue()
	 */
	@Override
	public double getMaxValue() { return 0; }

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#setMaxValue(double)
	 */
	@Override
	public void setMaxValue(double maxValue) {}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#setValueRange(double, double)
	 */
	@Override
	public void setValueRange(double min, double max) {}

}