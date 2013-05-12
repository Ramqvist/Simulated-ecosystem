package chalmers.dax021308.ecosystem.model.chromosome;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import chalmers.dax021308.ecosystem.model.genetics.BooleanGene;
import chalmers.dax021308.ecosystem.model.genetics.Chromosome;
import chalmers.dax021308.ecosystem.model.genetics.DoubleGene;
import chalmers.dax021308.ecosystem.model.genetics.GeneralGeneTypes;
import chalmers.dax021308.ecosystem.model.genetics.IChromosome;
import chalmers.dax021308.ecosystem.model.genetics.IGene;

/**
 *
 * @author Loanne Berggren
 *
 */
@RunWith(JUnit4.class)
public class ChromosomeTest {

	@Rule
	public ExpectedException exception = ExpectedException.none();

	IChromosome<GeneralGeneTypes, IGene> deer1;
	IChromosome<GeneralGeneTypes, IGene> deer2;

	@Before
	public void initialize() {
		deer1 = new Chromosome();
		//deer1.setAll(1);
		deer2 = new Chromosome();
		//deer2.setAll(1);
	}

	@Ignore
	public void cloneChromosome() {
		IChromosome<GeneralGeneTypes, IGene> clonedDeer = (IChromosome<GeneralGeneTypes, IGene>)deer1.getCopy();
		assertEquals(clonedDeer, deer1);
		assertEquals(deer1, clonedDeer);
		assertFalse(deer1 == clonedDeer);
	}


	@Test
	public void addGene() {
		deer1.addGene(GeneralGeneTypes.ISGROUPING, new BooleanGene());
		assertThat(deer1.getGene(GeneralGeneTypes.ISGROUPING), notNullValue());
		deer1.addGene(GeneralGeneTypes.GROUPING_COHESION, new DoubleGene());
		assertThat(deer1.getGene(GeneralGeneTypes.GROUPING_COHESION), notNullValue());
	}

	@Test
	public void set() {
		deer1.addGene(GeneralGeneTypes.GROUPING_COHESION, new DoubleGene());
		deer1.setCurrentDoubleValue(GeneralGeneTypes.GROUPING_COHESION, 3.0);
		assertTrue( (int)deer1.getGeneCurrentDoubleValue(GeneralGeneTypes.GROUPING_COHESION) == 3 );
	}


	@Test
	public void mutateChromosome() {
		//IChromosome<GeneralGeneTypes, IGene> clonedDeer = deer1.getCopy();
		//deer1.mutateChromosome();
		deer1.getCopy().mutateChromosome();
	}
}
