/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.model.genetics;

import chalmers.dax021308.ecosystem.model.genetics.newV.BooleanGene;
import chalmers.dax021308.ecosystem.model.genetics.newV.DoubleGene;
import chalmers.dax021308.ecosystem.model.genetics.newV.IGene;
import chalmers.dax021308.ecosystem.model.genetics.newV.NewChromosome;
import chalmers.dax021308.ecosystem.model.genetics.newV.NewGenome;
import chalmers.dax021308.ecosystem.model.genetics.newV.NewIChromosome;
import chalmers.dax021308.ecosystem.model.genetics.newV.NewIGenome;



/**
 * @author Loanne Berggren
 *
 */
public class GenomeFactory {	
	public static NewIGenome<GeneralGeneTypes, IGene> deerGenomeFactory(){
		NewIGenome<GeneralGeneTypes, IGene> g = new NewGenome(initDeerGenome());
		g.setMutationProbability(0.1);
		return g;
	}
	
	public static NewIGenome<GeneralGeneTypes, IGene> wolfGenomeFactory(){
		NewIGenome<GeneralGeneTypes, IGene> g = new NewGenome(initWolfGenome());
		g.setMutationProbability(0.1);
		return g;
	}
	
	private static NewIChromosome<GeneralGeneTypes,IGene> initDeerGenome(){
		double mutProb = 0.1;
		
		NewIChromosome<GeneralGeneTypes,IGene> chrom = new NewChromosome(); 
		chrom.addGene(GeneralGeneTypes.ISGROUPING, new BooleanGene(false, mutProb, true));
		chrom.addGene(GeneralGeneTypes.ISSTOTTING, new DoubleGene(0.0, 10.0, true, 0.0, 0.1, 7));
				return chrom;
	}
	
	
	private static NewIChromosome<GeneralGeneTypes,IGene> initWolfGenome(){
		double mutProb = 0.1;
		
		NewIChromosome<GeneralGeneTypes,IGene> chrom = new NewChromosome(); 
		chrom.addGene(GeneralGeneTypes.ISGROUPING, new BooleanGene(false, mutProb, true));
		chrom.addGene(GeneralGeneTypes.FOCUSPREY, new DoubleGene(0.0, 10.0, true, 0.0, 0.1, 7));
				return chrom;
	}
}	

