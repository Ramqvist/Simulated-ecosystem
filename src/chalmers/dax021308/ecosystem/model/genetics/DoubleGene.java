package chalmers.dax021308.ecosystem.model.genetics;

import java.util.Random;

//import org.junit.experimental.max.MaxCore;


/**
 *
 * @author Loanne Berggren
 *
 */
public class DoubleGene extends AbstractGene {
	protected double minValue;
	protected double maxValue;
	protected int currentValue;
	protected final int nBits;
	protected final double maxInt;

	/**
	 * standard mutation probability and number of bits
	 */
	public DoubleGene(){
		this(0, 100, true, 50, 0.1, 8, true);
	}

	private DoubleGene(double mutProb, int nBits) {
		setMutationProbability(mutProb);	// error checks
		this.nBits = nBits;
		this.maxInt = Math.pow(2, nBits) - 1;
		this.geneActiveStatus = true;
	}

	public DoubleGene(double minValue, double maxValue,
			boolean isMutable, double currentValue, double mutProb, int nBits, boolean randomStartValue) {
		this(mutProb, nBits);
		//this.randomStartValue = randomStartValue;
		//this.nBits = nBits;
		//this.maxInt = Math.pow(2, nBits) - 1;
		this.setValueRange(minValue, maxValue);

		//this.minValue = minValue;
		//this.maxValue = maxValue;
		this.isMutable = isMutable;

		if(randomStartValue) {
			//this.currentValue = new Random().nextInt((int)maxInt + 1);
			currentValue = randomGenerator.nextDouble() * (maxValue - minValue) + minValue;
			//setCurrentDoubleValue(new Random().nextInt((int)maxInt + 1));	// error checks
		} /*else {
			//this.currentValue = parseToInt(currentValue);
			setCurrentDoubleValue(currentValue);	// error checks
		}*/
		setCurrentDoubleValue(currentValue);	// error checks
		//setMutationProbaility(mutProb);	// error checks
	}

	private DoubleGene(DoubleGene toCopy) {
		// copy constructor
		/*this(toCopy.minValue, toCopy.maxValue, toCopy.isMutable, toCopy.getCurrentDoubleValue(),
				toCopy.mutationProbability, toCopy.nBits, toCopy.randomStartValue);
		if (!toCopy.randomStartValue){
			this.setCurrentDoubleValue(toCopy.getCurrentDoubleValue());
		}*/
		this(toCopy.minValue, toCopy.maxValue, toCopy.isMutable, toCopy.getCurrentDoubleValue(),
				toCopy.mutationProbability, toCopy.nBits, false);
		this.randomStartValue = toCopy.randomStartValue;
	}

	/**
	 * @return the currentValue
	 */
	@Override
	public double getCurrentDoubleValue() {
		return this.parseToDouble(currentValue);
	}

	/**
	 * @param currentValue
	 *            the currentValue to set
	 */
	@Override
	public void setCurrentDoubleValue(double currentValue) {
		if ( (currentValue < minValue) || (currentValue > maxValue) ) {
			throw new IllegalArgumentException("Value should be in range " +
					minValue + "-" + maxValue);
		}
		else
			this.currentValue = parseToInt(currentValue);
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

		for (int i = 1; i <= this.maxInt; i=2*i) {
			rand = randomGen.nextDouble();
			if (rand < mutationProbability) {
				currentValue = currentValue^i;
			}
		}
	}

	private double parseToDouble(int c) {
		double result = minValue + (double)c / maxInt *(maxValue-minValue);
		return result;
	}

	private int parseToInt(double c){
		if(c>maxValue) {
			c = maxValue;
		} else if(c < minValue) {
			c = minValue;
		}
		int result = (int) ((maxInt * ((c -minValue) / (maxValue-minValue)))+ 0.5);
		return result;
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#getCopy()
	 */
	@Override
	public IGene getCopy() {
		return new DoubleGene(this);
	}

	@Override
	public double getMinValue() {
		return minValue;
	}

	@Override
	public void setValueRange(double min, double max){
		if (min > max){
			throw new IllegalArgumentException("Max value may not be lower than" +
					" min value");
		}
		this.maxValue = max;
		this.minValue = min;
	}

	@Override
	public double getMaxValue() {
		return maxValue;
	}

	@Deprecated
	@Override
	public void setMinValue(double minValue) {
		if (minValue > maxValue){
			throw new IllegalArgumentException("Min value may not be higher than" +
					" max value");
		}
		this.minValue = minValue;
	}

	@Override
	@Deprecated
	public void setMaxValue(double maxValue) {
		if (minValue > maxValue){
			throw new IllegalArgumentException("Max value may not be lower than" +
					" min value");
		}
		this.maxValue = maxValue;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		long temp;
		temp = Double.doubleToLongBits(this.maxInt);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + this.nBits;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		DoubleGene other = (DoubleGene) obj;
		if (Double.doubleToLongBits(this.maxInt) != Double
				.doubleToLongBits(other.maxInt))
			return false;
		if (this.nBits != other.nBits)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.newV.IGene#randomizeValue()
	 */
	@Override
	public void randomizeValue() {
		setCurrentDoubleValue(randomGenerator.nextDouble() * (maxValue - minValue) + minValue);
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
		DoubleGene other = (DoubleGene) o;

		if (Double.doubleToLongBits(this.currentValue) != Double
				.doubleToLongBits(other.currentValue))
			return false;
		if (Double.doubleToLongBits(this.mutationProbability) != Double
				.doubleToLongBits(other.mutationProbability))
			return false;
		if (Double.doubleToLongBits(this.minValue) != Double
				.doubleToLongBits(other.minValue))
			return false;
		if (Double.doubleToLongBits(this.maxValue) != Double
				.doubleToLongBits(other.maxValue))
			return false;
		if (Double.doubleToLongBits(this.maxInt) != Double
				.doubleToLongBits(other.maxInt))
			return false;
		if (this.nBits != other.nBits)
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

}