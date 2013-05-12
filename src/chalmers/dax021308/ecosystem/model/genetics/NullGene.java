/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics;

/**
 * @author Loanne Berggren
 *
 * Use instead of passing/returning null.
 */
public final class NullGene implements IGene {

	@Override
	public double getMutationProbability() {return 0;}

	@Override
	public void setMutationProbability(double mutationProbability) {}

	@Override
	public boolean isGeneActive() {return false;}


	@Override
	public void setHaveGene(boolean haveGene) {}


	@Override
	public boolean isMutable() { return false; }

	@Override
	public void setMutable(boolean isMutable) {}

	@Override
	public double getMinValue() {return 0;}

	@Override
	public void setMinValue(double minValue) {}

	@Override
	public void setValueRange(double min, double max) {}

	@Override
	public double getMaxValue() {return 0;}

	@Override
	public void setMaxValue(double maxValue) {}

	@Override
	public double getCurrentDoubleValue() {return 0;}

	@Override
	public void setCurrentDoubleValue(double currentValue)
			throws IllegalArgumentException {}

	@Override
	public void mutate() {}

	@Override
	public IGene getCopy() {return null;}

	@Override
	public boolean hasRandomStartValue() {return false;}

	@Override
	public void setRandomStartValue(boolean random) {}

	@Override
	public void randomizeValue() {}

	@Override
	public boolean hasEqualValues(IGene o) {return false;}

}
