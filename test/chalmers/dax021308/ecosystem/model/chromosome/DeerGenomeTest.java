package chalmers.dax021308.ecosystem.model.chromosome;

import static org.junit.Assert.*;


import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import chalmers.dax021308.ecosystem.model.genetics.DeerGenes;
import chalmers.dax021308.ecosystem.model.genetics.Genome;
import chalmers.dax021308.ecosystem.model.genetics.GenomeFactory;
import chalmers.dax021308.ecosystem.model.genetics.IChromosome;
import chalmers.dax021308.ecosystem.model.genetics.IGenes;
import chalmers.dax021308.ecosystem.model.genetics.IGenome;

/**
 * 
 * @author Loanne Berggren
 *
 */
@RunWith(JUnit4.class)
public class DeerGenomeTest {
	IGenome<IGenes> deer1;
	IGenome<IGenes> deer2;
	
	@Before
	public void initialize() {
		deer1 = GenomeFactory.deerGenomeFactory();
		deer2 = GenomeFactory.deerGenomeFactory();
	}
	
	@Test
	public void equals() {
		assertEquals(deer1, deer2);
		deer1.setGene(DeerGenes.GROUPING, true);
		assertTrue(!deer1.equals(deer2));
	}
	
	@Test
	public void crossChromosomes() {
		int expectedSize = deer1.length();
		assertEquals(expectedSize, DeerGenes.values().length);
		IGenome<IGenes> result = deer1.mateWithMutation(deer2);
		int actualSize = ((IChromosome)result.getChromosomes()).getChromosomeLength();
		assertEquals(DeerGenes.values().length, actualSize);
		assertEquals(expectedSize, actualSize);
	}
	
	@Test
	public void isGeneSet() {
		deer1.setGene(DeerGenes.STOTTING, true);
		assertTrue(!deer1.isGeneSet(DeerGenes.GROUPING));
		assertTrue(deer1.isGeneSet(DeerGenes.STOTTING));
		
	}

	

	



	
	

	
}
