package chalmers.dax021308.ecosystem.model.chromosome;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import chalmers.dax021308.ecosystem.model.genetics.DeerGenes;
import chalmers.dax021308.ecosystem.model.genetics.DeerGenome;
import chalmers.dax021308.ecosystem.model.genetics.IChromosome;
import chalmers.dax021308.ecosystem.model.genetics.IGenome;

/**
 * 
 * @author Loanne Berggren
 *
 */
@RunWith(JUnit4.class)
public class DeerGenomeTest {
	IGenome<DeerGenes> deer1;
	IGenome<DeerGenes> deer2;
	
	@Before
	public void initialize() {
		deer1 = new DeerGenome();
		deer2 = new DeerGenome();
	}
	
	@Test
	public void equals() {
		assertEquals(deer1, deer2);
		deer1.setGene(DeerGenes.GROUPING, true);
		assertTrue(!deer1.equals(deer2));
	}
	
	@Test
	public void crossChromosomes() {
		int expectedSize = deer1.numberOfGenes();
		
		IGenome<DeerGenes> result = deer1.mateWithMutation(deer2);
		int actualSize = ((IChromosome)result.getChromosomes()).getChromosomeSize();
		assertEquals(expectedSize, actualSize);
	}
	
	@Test
	public void isGeneSet() {
		deer1.setGene(DeerGenes.STOTTING, true);
		assertTrue(!deer1.isGeneSet(DeerGenes.GROUPING));
		assertTrue(deer1.isGeneSet(DeerGenes.STOTTING));
		
	}

	

	



	
	

	
}
