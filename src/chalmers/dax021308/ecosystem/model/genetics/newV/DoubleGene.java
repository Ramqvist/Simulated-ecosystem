package chalmers.dax021308.ecosystem.model.genetics.newV;

import java.util.Random;


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
		this.minValue = minValue;
		this.maxValue = maxValue;
		this.isMutable = isMutable;
		this.currentValue = parseToInt(currentValue);
		this.mutationProbability = mutProb;
		this.nBits = nBits;
		this.maxInt = 2^nBits;
		
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
		this.parseToInt(((Double)currentValue).doubleValue());
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

}