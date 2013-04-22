package chalmers.dax021308.ecosystem.model.chromosome;

import static org.junit.Assert.*;

import java.util.BitSet;

import org.junit.Before;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import chalmers.dax021308.ecosystem.model.genetics.BitSetChromosome;

/**
 * 
 * @author Loanne Berggren
 *
 */
@RunWith(JUnit4.class)
public class BitSetChromosomeTest {
	BitSetChromosome deer1;
	BitSetChromosome deer2;
	
	@Before
	public void initialize() {
		deer1 = new BitSetChromosome(4, 0.01);
		deer1.setAll(true);
		deer2 = new BitSetChromosome(4, 0.01);
		deer2.setAll(true);
	}
	
	@Test
	public void cloneChromosome() {
		BitSetChromosome clonedDeer = (BitSetChromosome)deer1.clone();
		assertEquals(deer1, clonedDeer);
	}
	

	@Test
	public void crossChromosomes() {
		BitSetChromosome newChrom = (BitSetChromosome)deer1.crossChromosomes(deer2);
		assertEquals(deer1.getChromosomeSize(), deer2.getChromosomeSize());
		assertEquals(deer1.getChromosomeSize(), newChrom.getChromosomeSize());
		
		BitSet expected = new BitSet(4);
		expected.set(0, 4); // 1111
		assertEquals(4, newChrom.getChromosomeSize());
		assertEquals(expected, (BitSet)(newChrom.getGenes()));
	}
	
	@Test
	public void crossChromosomes2() {
		deer1 = new BitSetChromosome(4, 0.01);
		deer2 = new BitSetChromosome(4, 0.01);
		deer2.setAll(true);
		
		BitSetChromosome newChrom = (BitSetChromosome)deer1.crossChromosomes(deer2);
		assertEquals(4, newChrom.getChromosomeSize());
		
		BitSet expected = new BitSet(4);
		expected.set(1, 4);
		assertEquals(expected, (BitSet)newChrom.getGenes());
	}
	
	@Test
	public void mutateChromosome() {
		int length = deer1.getChromosomeSize();
		deer1.mutateChromosome();
		// can check length, and perhaps values being 0 or 1.
		
		assertEquals(deer1.getChromosomeSize(), length);
	}
	
	
}
