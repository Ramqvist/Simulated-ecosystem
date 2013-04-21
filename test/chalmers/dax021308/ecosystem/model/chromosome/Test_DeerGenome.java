//package chalmers.dax021308.ecosystem.model.chromosome;
//
//import static org.junit.Assert.*;
//
//import org.junit.Before;
//import org.junit.Test;
//
///**
// * 
// * @author Loanne Berggren
// *
// */
//public class Test_DeerGenome {
//	AbstractGenome<DeerGenes> deer1;
//	AbstractGenome<DeerGenes> deer2;
//	
//	@Before
//	public void initialize() {
//		deer1 = new DeerGenome();
//		deer2 = new DeerGenome();
//	}
//	
//	@Test
//	public void equals() {
//		assertEquals(deer1, deer2);
//		deer1.setGene(DeerGenes.FEMALE, true);
//		assertTrue(!deer1.equals(deer2));
//	}
//	
//	@Test
//	public void crossChromosomes() {
//		int expectedSize = deer1.numberOfGenes();
//		
//		AbstractGenome<DeerGenes> result = deer1.mateWithMutation(deer2);
//		int actualSize = ((BitSetChromosome)result.getChromosome()).getChromosomeSize();
//		assertEquals(expectedSize, actualSize);
//	}
//	
//	@Test
//	public void isGeneSet() {
//		deer1.setGene(DeerGenes.STOTTING, true);
//		assertTrue(!deer1.isGeneSet(DeerGenes.FEMALE));
//		assertTrue(deer1.isGeneSet(DeerGenes.STOTTING));
//		
//	}
//
//	
//
//	
//
//
//
//	
//	
//
//	
//}
