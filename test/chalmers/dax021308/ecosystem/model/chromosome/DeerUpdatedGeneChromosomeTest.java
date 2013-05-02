package chalmers.dax021308.ecosystem.model.chromosome;

import static org.junit.Assert.*;
import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import chalmers.dax021308.ecosystem.model.genetics.GeneralGeneTypes;
import chalmers.dax021308.ecosystem.model.genetics.GeneGeneric;
import chalmers.dax021308.ecosystem.model.genetics.IntAndAbs.IChromosomeGeneric;
import chalmers.dax021308.ecosystem.model.genetics.doubles.Chromosome;

/**
 * 
 * @author Loanne Berggren
 *
 */
@RunWith(JUnit4.class)
public class DeerUpdatedGeneChromosomeTest {
	
	@Rule
	public ExpectedException exception = ExpectedException.none();

	IChromosomeGeneric<GeneralGeneTypes, Double, GeneGeneric<Double>> deer1;
	IChromosomeGeneric<GeneralGeneTypes, Double, GeneGeneric<Double>> deer2;
	
	@Before
	public void initialize() {
		deer1 = new Chromosome<GeneralGeneTypes, GeneGeneric<Double>>();
		//deer1.setAll(1);
		deer2 = new Chromosome<GeneralGeneTypes, GeneGeneric<Double>>();
		//deer2.setAll(1);
	}
	
	@Test
	public void cloneChromosome() {
		IChromosomeGeneric<GeneralGeneTypes, Double, GeneGeneric<Double>> clonedDeer = (IChromosomeGeneric<GeneralGeneTypes, Double, GeneGeneric<Double>>)deer1.clone();
		assertEquals(deer1, clonedDeer);
	}
	
	/*@Test
	public void setAll() {
		int[] actual;
		int[] expected = new int[4];
		for (int i = 0; i < expected.length; ++i) {
			expected[i] = true;
		}
		
		actual = (int[])(deer1.getGenes());
		for (int i = 0; i < deer1.getLength(); ++i) {
			assertEquals(expected[0], actual[i]);
		}
	}*/
	
	/*@Test
	public void constructor2(){
		int[] expected = new int[4];
		expected[0] = 1;
		expected[1] = 1;
		expected[2] = 0;
		expected[3] = 1;
		
		deer1 = new DeerUpdatedGeneChromosome();
		assertTrue(0.01 == deer1.getMutationProbabilty());
		assertArrayEquals(expected, (int[])deer1.getGenes());
	}*/
	
	@Test
	public void addGene() {
		deer1.addGene(GeneralGeneTypes.GROUPING, new GeneGeneric<Double>(0.0,4.0,true,true,2.0));
		assertTrue(2.0 == deer1.getGeneCurrentValue(GeneralGeneTypes.GROUPING));
	}
	
	@Test
	public void set() {
		deer1.addGene(GeneralGeneTypes.GROUPING, new GeneGeneric<Double>(0.0,4.0,true,true,2.0));
		deer1.setCurrentValue(GeneralGeneTypes.GROUPING, 3.0);
		assertTrue(3.0 == deer1.getGeneCurrentValue(GeneralGeneTypes.GROUPING));
	}
	
	/*@Test
	public void set_tooLong() {
		int[] barr = new int[5];
		//Arrays.fill(barr, false);
		
		exception.expect(IndexOutOfBoundsException.class);
		deer1.set(1, barr);
	}*/
	
	/*@Test
	public void getSegment() {
		int[] expected = new int[2];
		expected[0] = 2;
		expected[1] = 3;
		
		int[] init = new int[4];
		init[0] = 1;
		init[1] = 2;
		init[2] = 3;
		init[3] = 4;
		
		deer1 = new DeerUpdatedGeneChromosome(init, 0.01);
		assertArrayEquals(expected, (int[])deer1.getSegment(1, 2));
	}*/
	
	
	
	/*@Test
	public void isEqualType() {
		// equal length, mutation probaility, different boolean values.
		deer1 = new DeerUpdatedGeneChromosome(4, 0.01);
		deer1.setAll(1);
		deer2 = new DeerUpdatedGeneChromosome(4, 0.01);
		assertTrue(deer1.isEqualType(deer2));
	}*/
	
	/*@Test
	public void isEqualType_not() {
		deer1 = new DeerUpdatedGeneChromosome(4, 0.01);
		
		// different length, but equal mutation probability, equal boolean values
		deer2 = new DeerUpdatedGeneChromosome(3, 0.01);
		assertFalse(deer1.isEqualType(deer2));
		
		// equal length, but different mutation probability, equal boolean values
		deer2 = new DeerUpdatedGeneChromosome(4, 0.1);
		assertFalse(deer1.isEqualType(deer2));
	}*/
	
	/*@Test
	public void isEqualType_not_otherClass() {
		deer1 = new GenericGeneChromosome<DeerGeneTypes, DoublesSingleDeerGene>();
		
		// equal length, equal mutation probability, different class
		BitSetChromosome deer2 = new BitSetChromosome(4, 0.01);
		assertFalse(deer1.isEqualType(deer2));
	}*/
	
	@Test
	public void mutateChromosome() {
		Chromosome<GeneralGeneTypes, GeneGeneric<Double>> clonedDeer = (Chromosome<GeneralGeneTypes, GeneGeneric<Double>>)deer1.clone();
		deer1.mutateChromosome();
		assertTrue(clonedDeer.isEqualType(deer1));
	}

	/*@Test
	public void crossChromosomes_exceptions() {
		exception.expect(IllegalArgumentException.class);
		deer1.crossChromosomes(null);
		
		BitSetChromosome other = new BitSetChromosome(4, 0.01);
		exception.expect(IllegalArgumentException.class);
		deer1.crossChromosomes(other);
	}*/
	
	/*@Test
	public void crossChromosomes_producesValidChild() {	
		DeerUpdatedGeneChromosome child = (DeerUpdatedGeneChromosome)deer1.DeerUpdatedGeneChromosome(deer2);
		assertTrue(deer1.isEqualType(child));
	}*
	
	/*@Test
	public void crossChromosomes_equalChromosomes() {	
		DeerUpdatedGeneChromosome child = (DeerUpdatedGeneChromosome)deer1.crossChromosomes(deer2);
		
		int[] expected = new int[4];
		Arrays.fill(expected, 1);
		
		assertArrayEquals(expected, (int[])child.getGenes());
	}*/
	
	/*@Test
	public void crossChromosomes_WontChangeParents(){
		DeerUpdatedGeneChromosome deer1clone = (DeerUpdatedGeneChromosome)deer1.clone();
		deer1.crossChromosomes(deer2);
		assertEquals(deer1clone, deer1);
	}*/
	
	/*@Test
	public void crossChromosomes_differentParentsRandomCrossOverPoint(){
		deer2 = new DeerUpdatedGeneChromosome(4, 0.01); // all 1
		DeerUpdatedGeneChromosome child = (DeerUpdatedGeneChromosome)deer1.crossChromosomes(deer2);
		

		int[] expected = new int[4];
		for (int i = 0; i < deer1.crossP; ++i) {
			expected[i] = (int)deer1.getValue(i);
		}
		for (int i = deer1.crossP; i < expected.length; ++i) {
			expected[i] = (int)deer2.getValue(i);
		}
		
		assertArrayEquals(expected, (int[])child.getGenes());
	}*/
		
	

}
