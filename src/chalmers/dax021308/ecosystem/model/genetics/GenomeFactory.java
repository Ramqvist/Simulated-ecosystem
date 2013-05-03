/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics;

import chalmers.dax021308.ecosystem.model.genetics.newV.BooleanGene;
import chalmers.dax021308.ecosystem.model.genetics.newV.DoubleGene;
import chalmers.dax021308.ecosystem.model.genetics.newV.IGene;
import chalmers.dax021308.ecosystem.model.genetics.newV.Chromosome;
import chalmers.dax021308.ecosystem.model.genetics.newV.Genome;
import chalmers.dax021308.ecosystem.model.genetics.newV.IChromosome;
import chalmers.dax021308.ecosystem.model.genetics.newV.IGenome;



/**
 * @author Loanne Berggren
 *
 */
public class GenomeFactory {	
	public static IGenome<GeneralGeneTypes, IGene> deerGenomeFactory(){
		IGenome<GeneralGeneTypes, IGene> g = new Genome(initDeerGenome());
		g.setMutationProbability(0.1);
		return g;
	}
	
	public static IGenome<GeneralGeneTypes, IGene> wolfGenomeFactory(){
		IGenome<GeneralGeneTypes, IGene> g = new Genome(initWolfGenome());
		g.setMutationProbability(0.1);
		return g;
	}
	
	private static IChromosome<GeneralGeneTypes,IGene> initDeerGenome(){
		double mutProb = 0.1;
		
		IChromosome<GeneralGeneTypes,IGene> chrom = new Chromosome(); 
		
		//Grouping behaviour
		chrom.addGene(GeneralGeneTypes.ISGROUPING, new BooleanGene(true, mutProb, false));
		chrom.addGene(GeneralGeneTypes.GROUPING_SEPARATION_FACTOR, new DoubleGene(0, 100, false, 20.0, mutProb, 8));
		chrom.addGene(GeneralGeneTypes.GROUPING_COHESION, new DoubleGene(0, 20, false, 1, mutProb, 8));
		chrom.addGene(GeneralGeneTypes.GROUPING_FORWARD_THRUST, new DoubleGene(0, 3, false, 0.1, mutProb, 8));
		chrom.addGene(GeneralGeneTypes.GROUPING_ARRAYAL_FORCE, new DoubleGene(0, 15, false, 4, mutProb, 8));
		
		//Stotting behaviour
		chrom.addGene(GeneralGeneTypes.ISSTOTTING, new BooleanGene(false, mutProb, false));
		chrom.addGene(GeneralGeneTypes.STOTTINGLENGTH, new DoubleGene(0, 30, false, 9, mutProb, 8));
		chrom.addGene(GeneralGeneTypes.STOTTINGRANGE, new DoubleGene(0, 200, false, 12, mutProb, 8));
		chrom.addGene(GeneralGeneTypes.STOTTINGANGLE, new DoubleGene(Math.PI/2, Math.PI, false, Math.PI/2, mutProb, 8));
		
		return chrom;
	}
	
	
	private static IChromosome<GeneralGeneTypes,IGene> initWolfGenome(){
		double mutProb = 0.1;
		
		IChromosome<GeneralGeneTypes,IGene> chrom = new Chromosome(); 
		
		//Grouping behaviour
		chrom.addGene(GeneralGeneTypes.ISGROUPING, new BooleanGene(true, mutProb, false));
		chrom.addGene(GeneralGeneTypes.GROUPING_SEPARATION_FACTOR, new DoubleGene(0, 100, false, 20, mutProb, 8));
		chrom.addGene(GeneralGeneTypes.GROUPING_COHESION, new DoubleGene(0, 20, false, 1, mutProb, 8));
		chrom.addGene(GeneralGeneTypes.GROUPING_FORWARD_THRUST, new DoubleGene(0, 3, false, 0.1, mutProb, 8));
		chrom.addGene(GeneralGeneTypes.GROUPING_ARRAYAL_FORCE, new DoubleGene(0, 15, false, 4, mutProb, 8));

		//Focus preys
		chrom.addGene(GeneralGeneTypes.FOCUSPREY, new BooleanGene(true, mutProb, false));
		
		return chrom;
	}
}	

