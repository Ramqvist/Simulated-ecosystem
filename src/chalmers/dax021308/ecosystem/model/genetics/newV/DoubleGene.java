package chalmers.dax021308.ecosystem.model.genetics.newV;

import java.util.Random;

import org.junit.experimental.max.MaxCore;


/**
 * 
 * @author Loanne Berggren
 * 
 */
public class DoubleGene extends AbstractGene {
	protected double minValue;
	protected double maxValue;
	protected int currentValue;
	protected int nBits;
	protected double maxInt;
	
	public DoubleGene() {}

	public DoubleGene(double minValue, double maxValue,
			boolean isMutable, double currentValue, double mutProb, int nBits) {
		this.maxInt = Math.pow(2, nBits) - 1;
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.isMutable = isMutable;
		this.currentValue = parseToInt(currentValue);
		this.mutationProbability = mutProb;
		this.nBits = nBits;
		
	}
	
	private DoubleGene(DoubleGene toCopy) {
		this.minValue = toCopy.minValue;
		this.maxValue = toCopy.maxValue;
		this.isMutable = toCopy.isMutable;
		this.currentValue = toCopy.currentValue;
		this.mutationProbability = toCopy.mutationProbability;
		this.nBits = toCopy.nBits;
		this.maxInt = toCopy.maxInt;
	}

	/**
	 * @return the currentValue
	 */
	@Override
	public Object getCurrentValue() {
		return this.parseToDouble(currentValue);
	}

	/**
	 * @param currentValue
	 *            the currentValue to set
	 */
	@Override
	public void setCurrentValue(Object currentValue) {
		this.currentValue = parseToInt(((Double)currentValue).doubleValue());
		System.out.println(this.currentValue);
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
	public void setMinValue(double minValue) {
		this.minValue = minValue;
	}

	@Override
	public double getMaxValue() {
		return maxValue;
	}
	
	@Override
	public void setMaxValue(double maxValue) {
		this.maxValue = maxValue;
	}

}