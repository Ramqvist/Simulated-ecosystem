package chalmers.dax021308.ecosystem.model.chromosome;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import chalmers.dax021308.ecosystem.model.genetics.GeneralGeneTypes;
import chalmers.dax021308.ecosystem.model.genetics.GenomeFactory;
import chalmers.dax021308.ecosystem.model.genetics.newV.IGene;
import chalmers.dax021308.ecosystem.model.genetics.newV.IGenome;

/**
 *
 * @author Loanne Berggren
 *
 */
@RunWith(JUnit4.class)
public class DeerGenomeTest {
	IGenome<GeneralGeneTypes, IGene> deer1;
	IGenome<GeneralGeneTypes, IGene> deer2;

	@Before
	public void initialize() {
		deer1 = GenomeFactory.deerGenomeFactory();
		deer2 = GenomeFactory.deerGenomeFactory();
	}

	@Test
	public void equals() {
		assertEquals(deer1, deer2);
	}

	/*@Test
	public void crossChromosomes() {
		int expectedSize = deer1.numberOfGenes();
		assertEquals(expectedSize, GeneralGeneTypes.values().length);
		IGenome<IGenes> result = deer1.mateWithMutation(deer2);
		int actualSize = ((IChromosome)result.getChromosomes()).getLength();
		assertEquals(GeneralGeneTypes.values().length, actualSize);
		assertEquals(expectedSize, actualSize);
	}
	*/


}
