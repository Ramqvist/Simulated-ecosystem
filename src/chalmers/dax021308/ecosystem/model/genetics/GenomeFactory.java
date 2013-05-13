/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics;

import java.io.FileWriter;
import java.io.IOException;

import chalmers.dax021308.ecosystem.model.util.SimulationResultPrinter;

import com.sun.java_cup.internal.runtime.Scanner;




/**
 * @author Loanne Berggren
 *
 */
public class GenomeFactory {
	public static IGenome<GeneralGeneTypes, IGene> deerGenomeFactory(){
		//IGenome<GeneralGeneTypes, IGene> g = new Genome(initDeerGenome());
		IGenome<GeneralGeneTypes, IGene> g = new Genome(rand_deerChromosome());
		return g;
	}

	public static IGenome<GeneralGeneTypes, IGene> wolfGenomeFactory(){
		//IGenome<GeneralGeneTypes, IGene> g = new Genome(initWolfGenome());
		IGenome<GeneralGeneTypes, IGene> g = new Genome(rand_wolfChromosome());
		return g;
	}

	private static IChromosome<GeneralGeneTypes,IGene> initDeerGenome(){
		double mutProb = 0.01;

		IChromosome<GeneralGeneTypes,IGene> chrom = new Chromosome();

		//Grouping behaviour
		chrom.addGene(GeneralGeneTypes.ISGROUPING, new BooleanGene(true, mutProb, false, false));
		chrom.addGene(GeneralGeneTypes.GROUPING_SEPARATION_FACTOR, new DoubleGene(0, 120, false, 80, mutProb, 10, false));
		chrom.addGene(GeneralGeneTypes.GROUPING_COHESION, new DoubleGene(0, 10, false, 2, mutProb, 10, false));
		chrom.addGene(GeneralGeneTypes.GROUPING_FORWARD_THRUST, new DoubleGene(0, 2, false, 1.5, mutProb, 10, false));
		chrom.addGene(GeneralGeneTypes.GROUPING_ARRAYAL_FORCE, new DoubleGene(0, 10, false, 2, mutProb, 10, false));

		//Stotting behaviour
		chrom.addGene(GeneralGeneTypes.ISSTOTTING, new BooleanGene(false, mutProb, false, false));
		chrom.addGene(GeneralGeneTypes.STOTTINGLENGTH, new DoubleGene(0, 30, false, 9, mutProb, 8, false));
		chrom.addGene(GeneralGeneTypes.STOTTINGRANGE, new DoubleGene(0, 200, false, 12, mutProb, 8, false));
		chrom.addGene(GeneralGeneTypes.STOTTINGANGLE, new DoubleGene(Math.PI/2, Math.PI, false, Math.PI/2, mutProb, 8, false));

		return chrom;
	}

	private static IChromosome<GeneralGeneTypes,IGene> initWolfGenome(){
		double mutProb = 0.01;

		IChromosome<GeneralGeneTypes,IGene> chrom = new Chromosome();

		//Grouping behaviour
		chrom.addGene(GeneralGeneTypes.ISGROUPING, new BooleanGene(true, mutProb, false, false));
		chrom.addGene(GeneralGeneTypes.GROUPING_SEPARATION_FACTOR, new DoubleGene(0, 150, false, 100, mutProb, 10, false));
		chrom.addGene(GeneralGeneTypes.GROUPING_COHESION, new DoubleGene(0, 20, false, 2, mutProb, 10, false));
		chrom.addGene(GeneralGeneTypes.GROUPING_FORWARD_THRUST, new DoubleGene(0, 2.5, false, 1, mutProb, 10, false));
		chrom.addGene(GeneralGeneTypes.GROUPING_ARRAYAL_FORCE, new DoubleGene(0, 10, false, 1, mutProb, 10, false));

		//Focus preys
		chrom.addGene(GeneralGeneTypes.FOCUSPREY, new BooleanGene(true, mutProb, false, false));

		return chrom;
	}

	public static IGenome<GeneralGeneTypes, IGene> grassGenomeFactory(){
		IGenome<GeneralGeneTypes, IGene> g = new Genome(initGrassGenome());
		return g;
	}

	private static IChromosome<GeneralGeneTypes,IGene> initGrassGenome(){
		IChromosome<GeneralGeneTypes,IGene> chrom = new Chromosome();
		chrom.addGene(GeneralGeneTypes.EATEN_EQUALS_DEATH, BooleanGene.newNullGene());
		chrom.addGene(GeneralGeneTypes.WEED_TRANSFORMATION_FACTOR, DoubleGene.newNullGene());
		chrom.addGene(GeneralGeneTypes.HIGH_FACTOR, DoubleGene.newNullGene());
		return chrom;
	}


	/*
	 * For script
	 */
	public static IChromosome<GeneralGeneTypes,IGene> rand_deerChromosome(){

		double mutProb = 0.01;
		IChromosome<GeneralGeneTypes,IGene> chrom = new Chromosome();

		//Grouping behaviour
		chrom.addGene(GeneralGeneTypes.ISGROUPING, new BooleanGene(false, mutProb, true, true));
		chrom.addGene(GeneralGeneTypes.GROUPING_SEPARATION_FACTOR, new DoubleGene(0, 120, true, 80, mutProb, 10, true));
		chrom.addGene(GeneralGeneTypes.GROUPING_COHESION, new DoubleGene(0, 10, true, 2, mutProb, 10, true));
		chrom.addGene(GeneralGeneTypes.GROUPING_FORWARD_THRUST, new DoubleGene(0, 2, true, 1.5, mutProb, 10, true));
		chrom.addGene(GeneralGeneTypes.GROUPING_ARRAYAL_FORCE, new DoubleGene(0, 10, true, 2, mutProb, 10, true));

		//Stotting behaviour
		chrom.addGene(GeneralGeneTypes.ISSTOTTING, new BooleanGene(false, mutProb, true, true));
		chrom.addGene(GeneralGeneTypes.STOTTINGLENGTH, new DoubleGene(0, 30, true, 9, mutProb, 8, true));
		chrom.addGene(GeneralGeneTypes.STOTTINGRANGE, new DoubleGene(0, 200, true, 12, mutProb, 8, true));
		chrom.addGene(GeneralGeneTypes.STOTTINGANGLE, new DoubleGene(Math.PI/2, Math.PI, true, Math.PI/2, mutProb, 8, true));

		SimulationResultPrinter.saveGeneStartValuesToFile("Deers\n" + chrom.toString());
		return chrom;
	}

	public static IChromosome<GeneralGeneTypes,IGene> rand_wolfChromosome(){
		double mutProb = 0.01;
		IChromosome<GeneralGeneTypes,IGene> chrom = new Chromosome();

		//Grouping behaviour
		chrom.addGene(GeneralGeneTypes.ISGROUPING, new BooleanGene(false, mutProb, true, true));
		chrom.addGene(GeneralGeneTypes.GROUPING_SEPARATION_FACTOR, new DoubleGene(0, 150, true, 100, mutProb, 10, true));
		chrom.addGene(GeneralGeneTypes.GROUPING_COHESION, new DoubleGene(0, 20, true, 2, mutProb, 10, true));
		chrom.addGene(GeneralGeneTypes.GROUPING_FORWARD_THRUST, new DoubleGene(0, 2.5, true, 1, mutProb, 10, true));
		chrom.addGene(GeneralGeneTypes.GROUPING_ARRAYAL_FORCE, new DoubleGene(0, 10, true, 1, mutProb, 10, true));

		//Focus preys
		chrom.addGene(GeneralGeneTypes.FOCUSPREY, new BooleanGene(false, mutProb, true, true));

		SimulationResultPrinter.saveGeneStartValuesToFile("Wolves\n" + chrom.toString());

		return chrom;
	}

}

