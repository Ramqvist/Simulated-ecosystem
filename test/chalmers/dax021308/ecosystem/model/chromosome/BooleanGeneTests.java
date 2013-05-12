/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.chromosome;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import chalmers.dax021308.ecosystem.model.genetics.BooleanGene;
import chalmers.dax021308.ecosystem.model.genetics.IGene;

/**
 * @author Loanne Berggren
 *
 */
public class BooleanGeneTests {

	private final boolean GENE_ACTIVE = true;
	private final boolean GENE_NOT_ACTIVE = false;
	private final boolean IS_MUTABLE = true;
	private final boolean IS_NOT_MUTABLE = false;
	private final boolean HAS_RANDOM_START_VALUE = true;
	private final boolean HAS_NOT_RANDOM_START_VALUE = false;
	private final double MUT_PROB_MAX = 1.0;
	private final double MUT_PROB_MIN = 0.0;
	private final double MUT_PROB_NEGATIVE = -0.1;
	private final double MUT_PROB_TO_HIGH = 1.1;
	private final double MUT_PROB_MIDDLE = 0.5;

	@Test
	public void constructor() {
		IGene bg = new BooleanGene();
		assertThat(bg, notNullValue());
		assertTrue(bg.getMutationProbability() > 0.0);
		assertTrue(bg.getMutationProbability() <= 1.0);
	}

	@Test
	public void constructor2_validValues() {
		IGene bg = new BooleanGene(GENE_ACTIVE, MUT_PROB_MIDDLE, IS_MUTABLE, HAS_NOT_RANDOM_START_VALUE);
		assertThat(bg, notNullValue());
		bg.getCurrentDoubleValue();
		assertThat(bg.isGeneActive(), equalTo(GENE_ACTIVE));
		assertThat(bg.getMutationProbability(), equalTo(MUT_PROB_MIDDLE));
		assertThat(bg.isMutable(), equalTo(IS_MUTABLE) );
		//assertThat(bg.hasRandomStartValue(), equalTo(HAS_NOT_RANDOM_START_VALUE));
	}

	@Test(expected = IllegalArgumentException.class)
	public void constructor2_illegalArguments() {
		double mutProb = -0.3;		// negative
		new BooleanGene(GENE_ACTIVE, mutProb, IS_MUTABLE, HAS_RANDOM_START_VALUE);
	}

	@Test
	public void setGetHaveGene() {
		IGene bg = new BooleanGene();
		bg.setHaveGene(GENE_ACTIVE);
		assertTrue(bg.isGeneActive());

		bg.setHaveGene(GENE_NOT_ACTIVE);
		assertFalse(bg.isGeneActive());
	}

	@Test
	public void setGetCurrentValue() {
		// just checks that no exception is thrown
		IGene bg = new BooleanGene(GENE_ACTIVE, MUT_PROB_MIDDLE, IS_NOT_MUTABLE, HAS_NOT_RANDOM_START_VALUE);
		bg.getCurrentDoubleValue();
		bg.setCurrentDoubleValue(1.0);
	}


	@Test(expected = IllegalArgumentException.class)
	public void setMutationProbability_tooLow() {
		IGene bg = new BooleanGene();
		bg.setMutationProbability(MUT_PROB_NEGATIVE);
	}

	@Test(expected = IllegalArgumentException.class)
	public void setMutationProbability_tooHigh() {
		IGene bg = new BooleanGene();
		bg.setMutationProbability(MUT_PROB_TO_HIGH);
	}

	@Test
	public void mutate_alwaysMutate() {
		IGene bg = new BooleanGene(GENE_ACTIVE, MUT_PROB_MAX, IS_MUTABLE, HAS_NOT_RANDOM_START_VALUE);
		bg.mutate();
		assertThat(bg.isGeneActive(), equalTo(GENE_NOT_ACTIVE));
	}

	@Test
	public void mutate_neverMutate() {
		IGene bg = new BooleanGene(GENE_ACTIVE, MUT_PROB_MAX, IS_NOT_MUTABLE, HAS_NOT_RANDOM_START_VALUE);
		bg.mutate();
		assertThat(bg.isGeneActive(), equalTo(GENE_ACTIVE));
	}

	@Test
	public void getCopy_returnsDeepCopy() {
		IGene original = new BooleanGene(GENE_ACTIVE, MUT_PROB_MIDDLE, IS_MUTABLE, HAS_NOT_RANDOM_START_VALUE);
		double omut = original.getMutationProbability();
		IGene copy = original.getCopy();

		assertThat(copy, not(sameInstance(original)));	// not pointing to same object
		assertThat(copy, equalTo(original));				// but should be equal
		assertTrue(original.hasEqualValues(copy));	// and have equal values

		copy.setMutationProbability(original.getMutationProbability() + 0.3);	// change value in copy, so
		assertFalse(original.hasEqualValues(copy));	// now they shouldn't be equal

		assertTrue(original.getMutationProbability() == omut);	// make sure original value is the same.
	}

	@Test
	public void getCopy_randomStartValue() {
		IGene original = new BooleanGene(GENE_ACTIVE, MUT_PROB_MIDDLE, IS_MUTABLE, HAS_RANDOM_START_VALUE);
		IGene copy;
		for (int i = 0; i < 10000; ++i){
			copy = original.getCopy();	// i think this will always pass now, it didn't before
			assertTrue(copy.hasEqualValues(original));
		}
	}

// equals
	@Test
	public void equals() {

		IGene bg1 = new BooleanGene(GENE_ACTIVE, MUT_PROB_MIDDLE, IS_MUTABLE, HAS_NOT_RANDOM_START_VALUE);
		IGene bg2 = new BooleanGene(GENE_ACTIVE, MUT_PROB_MIDDLE, IS_MUTABLE, HAS_NOT_RANDOM_START_VALUE);
		assertThat(bg1, not(sameInstance(bg2)));
		//assertThat(bg1, equalTo(bg2));
		//assertThat(bg2, equalTo(bg1));

		assertTrue(bg1.equals(bg2));
		assertTrue(bg2.equals(bg1));

		assertTrue(bg1.hasEqualValues(bg2));
		assertTrue(bg2.hasEqualValues(bg1));
	}

	@Test
	public void equalValues_not() {
		IGene bg1 = new BooleanGene(GENE_ACTIVE, MUT_PROB_MIDDLE, IS_MUTABLE, HAS_NOT_RANDOM_START_VALUE);
		IGene bg2 = new BooleanGene(GENE_ACTIVE, MUT_PROB_MAX, IS_MUTABLE, HAS_NOT_RANDOM_START_VALUE);
		assertThat(bg1, equalTo(bg2));
		assertThat(bg2, equalTo(bg1));
		assertFalse(bg1.hasEqualValues(bg2));
		assertFalse(bg2.hasEqualValues(bg1));
	}


// Test methods that arn't actually in use.

	@Test
	public void getMinValue() {
		IGene bg = new BooleanGene();
		assertThat(bg.getMinValue(), equalTo(0.0));
	}

	@Test
	public void getMaxValue() {
		IGene bg = new BooleanGene();
		assertThat(bg.getMaxValue(), equalTo(0.0));
	}

}
