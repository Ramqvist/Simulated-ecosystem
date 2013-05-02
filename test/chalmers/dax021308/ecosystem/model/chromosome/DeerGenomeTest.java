package chalmers.dax021308.ecosystem.model.chromosome;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import chalmers.dax021308.ecosystem.model.genetics.GeneralGeneTypes;
import chalmers.dax021308.ecosystem.model.genetics.GenomeFactory;
import chalmers.dax021308.ecosystem.model.genetics.IntAndAbs.IGenomeGeneric;

/**
 * 
 * @author Loanne Berggren
 *
 */
@RunWith(JUnit4.class)
public class DeerGenomeTest {
	IGenomeGeneric<GeneralGeneTypes, Double> deer1;
	IGenomeGeneric<GeneralGeneTypes, Double> deer2;
	
	@Before
	public void initialize() {
		deer1 = GenomeFactory.deerGenomeFactory();
		deer2 = GenomeFactory.deerGenomeFactory();
	}
	
	@Test
	public void equals() {
		assertEquals(deer1, deer2);
		deer1.setGene(GeneralGeneTypes.GROUPING, 3.1);
		assertTrue(!deer1.equals(deer2));
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
	@Test
	public void isGeneSet() {
		deer1.setGene(GeneralGeneTypes.STOTTING, 2.0);
		assertTrue(deer1.getGeneCurrentValue(GeneralGeneTypes.GROUPING) <= 0);
		assertTrue(deer1.getGeneCurrentValue(GeneralGeneTypes.STOTTING) > 0);
		
	}

	

	



	
	

	
}
