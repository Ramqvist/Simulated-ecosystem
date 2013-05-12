/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.chromosome;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import chalmers.dax021308.ecosystem.model.genetics.BooleanGene;
import chalmers.dax021308.ecosystem.model.genetics.DoubleGene;
import chalmers.dax021308.ecosystem.model.genetics.IGene;

/**
 * @author Loanne Berggren
 *
 */
public class DoubleGeneTests {

	private final boolean GENE_ACTIVE = true;
	private final boolean GENE_NOT_ACTIVE = false;
	private final boolean IS_MUTABLE = true;
	private final boolean IS_NOT_MUTABLE = false;
	private final boolean HAS_RANDOM_START_VALUE = true;
	private final boolean HAS_NOT_RANDOM_START_VALUE = false;
	private final double MUT_PROB_MAX = 1.0;
	private final double MUT_PROB_MIN = 0.0;
	private final double MUT_PROB_NEGATIVE = -0.1;
	private final double MUT_PROB_TOO_HIGH = 1.1;
	private final double MUT_PROB_MIDDLE = 0.5;
	private final double VALUE_MAX = 110;
	private final double VALUE_MIN = 3;
	private final double VALUE_TOO_LOW = VALUE_MIN - 10;
	private final double VALUE_TOO_HIGH = VALUE_MAX + 10;
	private final double VALUE_VALID = 55;
	private final int NBITS = 8;

	@Test
	public void constructor() {
		IGene bg = new DoubleGene();
		assertThat(bg, notNullValue());
		assertTrue(bg.getMutationProbability() > 0.0);
		assertTrue(bg.getMutationProbability() <= 1.0);
	}
// public DoubleGene(double minValue, double maxValue,
//	boolean isMutable, double currentValue, double mutProb, int nBits, boolean randomStartValue) {

	@Test
	public void constructor2_validValues() {
		IGene bg = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS, HAS_NOT_RANDOM_START_VALUE);
		assertThat(bg, notNullValue());

		int t = this.parseToInt(VALUE_VALID, VALUE_MIN, VALUE_MAX, NBITS);
		double d = parseToDouble(t, VALUE_MIN, VALUE_MAX, NBITS);
		assertTrue(bg.getCurrentDoubleValue() == d);

		assertThat(bg.isGeneActive(), equalTo(GENE_ACTIVE));
		assertThat(bg.getMutationProbability(), equalTo(MUT_PROB_MIDDLE));
		assertThat(bg.isMutable(), equalTo(IS_MUTABLE) );
		//assertThat(bg.hasRandomStartValue(), equalTo(HAS_NOT_RANDOM_START_VALUE));
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor2_illegalArguments() {
		double mutProb = -0.3;		// negative
		new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, mutProb, NBITS, HAS_RANDOM_START_VALUE);

	}

	double parseToDouble(int c, double minValue, double maxValue, int nBits) {
		double maxInt = Math.pow(2, nBits) - 1;
		double result = minValue + (double)c / maxInt *(maxValue-minValue);
		return result;
	}

	int parseToInt(double c, double minValue, double maxValue, int nBits){
		double maxInt = Math.pow(2, nBits) - 1;
		if(c>maxValue) {
			c = maxValue;
		} else if(c < minValue) {
			c = minValue;
		}
		int result = (int) ((maxInt * ((c -minValue) / (maxValue-minValue)))+ 0.5);
		return result;
	}

	@Test
	public void setGetHaveGene() {
		IGene bg = new DoubleGene();
		bg.setHaveGene(GENE_ACTIVE);
		assertTrue(bg.isGeneActive());

		bg.setHaveGene(GENE_NOT_ACTIVE);
		assertFalse(bg.isGeneActive());
	}

	@Test (expected = IllegalArgumentException.class)
	public void setCurrentValue_tooLow() {
		IGene bg = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_NOT_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS, HAS_NOT_RANDOM_START_VALUE);
		bg.setCurrentDoubleValue(VALUE_TOO_LOW);
	}

	@Test (expected = IllegalArgumentException.class)
	public void setCurrentValue_tooHigh() {
		IGene bg = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_NOT_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS, HAS_NOT_RANDOM_START_VALUE);
		bg.setCurrentDoubleValue(VALUE_TOO_HIGH);
	}

	@Test
	public void setGetCurrentValue_correctValue() {
		IGene bg = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_NOT_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS, HAS_NOT_RANDOM_START_VALUE);
		double f = (double)bg.getCurrentDoubleValue();
		bg.setCurrentDoubleValue(f+10);
		f = (double)bg.getCurrentDoubleValue();
		assertThat((double) bg.getCurrentDoubleValue(), equalTo(f));
	}

	@Test(expected = IllegalArgumentException.class)
	public void setMutationProbability_tooLow() {
		IGene bg = new DoubleGene();
		bg.setMutationProbability(MUT_PROB_NEGATIVE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setMutationProbability_tooHigh() {
		IGene bg = new DoubleGene();
		bg.setMutationProbability(MUT_PROB_TOO_HIGH);
	}

	@Test
	public void mutate_alwaysMutate() {
		IGene bg = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, MUT_PROB_MAX, NBITS, HAS_NOT_RANDOM_START_VALUE);
		double v = (double) bg.getCurrentDoubleValue();
		bg.mutate();
		assertThat((double)bg.getCurrentDoubleValue(), not(equalTo(v)));
	}

	@Test
	public void mutate_neverMutate() {
		IGene bg = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_NOT_MUTABLE, VALUE_VALID, MUT_PROB_MAX, NBITS, HAS_NOT_RANDOM_START_VALUE);
		double v = (double) bg.getCurrentDoubleValue();
		bg.mutate();
		assertThat((double)bg.getCurrentDoubleValue(), equalTo(v));
	}

	@Test
	public void getCopy_returnsDeepCopy() {
		IGene original = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS, HAS_NOT_RANDOM_START_VALUE);
		double ocurr = original.getCurrentDoubleValue();
		double omut = original.getMutationProbability();

		IGene copy = original.getCopy();

		assertThat(copy, not(sameInstance(original)));	// not pointing to same object
		assertThat(copy, equalTo(original));				// but should be equal
		assertTrue(original.hasEqualValues(copy));	// and have equal values

		copy.setMutationProbability(original.getMutationProbability() + 0.3);	// change value in copy, so
		copy.setCurrentDoubleValue(original.getCurrentDoubleValue() + 10);	// change value in copy, so
		assertFalse(original.hasEqualValues(copy));	// now they shouldn't be equal

		assertTrue(original.getCurrentDoubleValue() == ocurr);	// make sure original value is the same.
		assertTrue(original.getMutationProbability() == omut);	// make sure original value is the same.
	}

	@Test
	public void getCopy_randomStartValue() {
		IGene original = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS, HAS_RANDOM_START_VALUE);
		IGene copy;
		for (int i = 0; i < 10000; ++i){
			copy = original.getCopy();	// i think this will always pass now, it didn't before
			assertTrue(copy.hasEqualValues(original));
		}
	}

// equals
	@Test
	public void equals() {
		IGene bg1 = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS, HAS_NOT_RANDOM_START_VALUE);
		IGene bg2 = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS, HAS_NOT_RANDOM_START_VALUE);
		assertThat(bg1, not(sameInstance(bg2)));
		assertThat(bg1, equalTo(bg2));
		assertThat(bg2, equalTo(bg1));

		assertEquals(bg1, bg2);
		assertTrue(bg1.equals(bg2));
		assertTrue(bg2.equals(bg1));
		assertTrue(bg1.hasEqualValues(bg2));
		assertTrue(bg2.hasEqualValues(bg1));
	}

	@Test
	public void equals_not() {
		IGene bg1 = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS, HAS_NOT_RANDOM_START_VALUE);
		IGene bg2 = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS-1, HAS_NOT_RANDOM_START_VALUE);
		assertThat(bg1, not(equalTo(bg2)));
		assertThat(bg2, not(equalTo(bg1)));
		assertFalse(bg1.equals(bg2));
		assertFalse(bg2.equals(bg1));
		assertFalse(bg1.hasEqualValues(bg2));
		assertFalse(bg2.hasEqualValues(bg1));
	}

	@Test
	public void equalValues() {
		IGene bg1 = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS, HAS_NOT_RANDOM_START_VALUE);
		IGene bg2 = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS, HAS_NOT_RANDOM_START_VALUE);
		assertTrue(bg1.equals(bg2));
		assertTrue(bg2.equals(bg1));
		assertTrue(bg1.hasEqualValues(bg2));
		assertTrue(bg2.hasEqualValues(bg1));
	}

	@Test
	public void equalValues_not() {
		IGene bg1 = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, MUT_PROB_MIDDLE, NBITS, HAS_NOT_RANDOM_START_VALUE);
		IGene bg2 = new DoubleGene(VALUE_MIN, VALUE_MAX, IS_MUTABLE, VALUE_VALID, MUT_PROB_MAX, NBITS, HAS_NOT_RANDOM_START_VALUE);
		assertTrue(bg1.equals(bg2));
		assertTrue(bg2.equals(bg1));
		assertFalse(bg1.hasEqualValues(bg2));
		assertFalse(bg2.hasEqualValues(bg1));
	}

	@Test
	public void setValueRange_correct() {
		IGene bg = new DoubleGene();
		bg.setValueRange(10, 50);
		assertTrue(bg.getMinValue() == 10);
		assertTrue(bg.getMaxValue() == 50);
	}

	@Test (expected = IllegalArgumentException.class)
	public void setValueRange_incorrect() {
		IGene bg = new DoubleGene();
		bg.setValueRange(20, 30);
		bg.setValueRange(50, 10);

		assertTrue(bg.getMinValue() == 20);
		assertTrue(bg.getMaxValue() == 30);
	}

	@Test
	public void getMinValue() {
		IGene bg = new DoubleGene();
		bg.setValueRange(10, 20);
		//bg.setMinValue(10);
		assertTrue(bg.getMinValue() == 10);
	}

	@Test
	public void getMaxValue() {
		IGene bg = new DoubleGene();
		bg.setValueRange(0, 10);
		//bg.setMaxValue(40);
		assertTrue(bg.getMaxValue() == 10);
	}

}
