package chalmers.dax021308.ecosystem.model.genetics;

import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.genetics.newV.Genome;
import chalmers.dax021308.ecosystem.model.genetics.newV.IGene;
import chalmers.dax021308.ecosystem.model.genetics.newV.IGenome;

public abstract class GeneticSettings {
	
	//TEST Setups
	public static GeneticSettings preySettings = new GeneticSettings() {
		IGenome<GeneralGeneTypes, IGene> deerGenome = GenomeFactory.deerGenomeFactory();
		
		@Override
		public List<GenomeSpecification> getGenomes() {
			List<GenomeSpecification> result = new ArrayList<GenomeSpecification>();
			
			result.add(new GenomeSpecification(deerGenome, GenomeSpecification.TYPE_BOOLEAN, "Grouping"));
			result.add(new GenomeSpecification(deerGenome, GenomeSpecification.TYPE_DOUBLE, "Separation force"));
			result.add(new GenomeSpecification(deerGenome, GenomeSpecification.TYPE_DOUBLE, "Cohesion"));
			result.add(new GenomeSpecification(deerGenome, GenomeSpecification.TYPE_DOUBLE, "Arrayal force"));
			result.add(new GenomeSpecification(deerGenome, GenomeSpecification.TYPE_DOUBLE, "Forward thrust"));
			
			result.add(new GenomeSpecification(deerGenome, GenomeSpecification.TYPE_BOOLEAN, "Stotting"));
			result.add(new GenomeSpecification(deerGenome, GenomeSpecification.TYPE_DOUBLE, "Stotting length"));
			result.add(new GenomeSpecification(deerGenome, GenomeSpecification.TYPE_DOUBLE, "Stotting range"));
			result.add(new GenomeSpecification(deerGenome, GenomeSpecification.TYPE_DOUBLE, "Stotting angle"));
			
			return result;
		}
		
		@Override
		public IGenome<GeneralGeneTypes, IGene> getGenome(){
			return deerGenome;
		}
	};
	public static GeneticSettings predSettings = new GeneticSettings() {
		
		public IGenome<GeneralGeneTypes, IGene> wolfGenome = GenomeFactory.wolfGenomeFactory();
		
		@Override
		public List<GenomeSpecification> getGenomes() {
			List<GenomeSpecification> result = new ArrayList<GenomeSpecification>();
			
			result.add(new GenomeSpecification(wolfGenome, GenomeSpecification.TYPE_BOOLEAN, "Grouping"));
			result.add(new GenomeSpecification(wolfGenome, GenomeSpecification.TYPE_DOUBLE, "Separation force"));
			result.add(new GenomeSpecification(wolfGenome, GenomeSpecification.TYPE_DOUBLE, "Cohesion"));
			result.add(new GenomeSpecification(wolfGenome, GenomeSpecification.TYPE_DOUBLE, "Arrayal force"));
			result.add(new GenomeSpecification(wolfGenome, GenomeSpecification.TYPE_DOUBLE, "Forward thrust"));
			
			result.add(new GenomeSpecification(wolfGenome, GenomeSpecification.TYPE_BOOLEAN, "Focus preys"));
			return result;
		}

		@Override
		public IGenome<GeneralGeneTypes, IGene> getGenome() {
			return wolfGenome;
		}
	};
	public static GeneticSettings grassSettings = new GeneticSettings() {
		
		@Override
		public List<GenomeSpecification> getGenomes() {
			List<GenomeSpecification> result = new ArrayList<GenomeSpecification>();
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_BOOLEAN, "Will die when eaten"));
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_DOUBLE, "Chance to transform to weed"));
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_DOUBLE, "High factor"));
			return result;
		}

		@Override
		public IGenome<GeneralGeneTypes, IGene> getGenome() {
			// TODO Auto-generated method stub
			return null;
		}
	};
	public static GeneticSettings grassFieldSettings = new GeneticSettings() {
		@Override
		public List<GenomeSpecification> getGenomes(){
			List<GenomeSpecification> result = new ArrayList<GenomeSpecification>();
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_BOOLEAN, "Will die when eaten"));
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_DOUBLE, "Chance to transform to weed"));
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_DOUBLE, "High factor"));
			return result;
		}

		@Override
		public IGenome<GeneralGeneTypes, IGene> getGenome() {
			// TODO Auto-generated method stub
			return null;
		}
	};
	
	public abstract List<GenomeSpecification> getGenomes();
	
	public abstract IGenome<GeneralGeneTypes, IGene> getGenome();
	
	public class GenomeSpecification {
		//TODO: Make abstract class with predefines max/min/start values.
		public static final int TYPE_BOOLEAN = 1;
		public static final int TYPE_DOUBLE  = 2;
		
		private IGenome<GeneralGeneTypes, IGene> g;
		private int genomeType;
		private String name;
		
		public int intValue;
		public double doubleValue;
		public boolean activeOnBirth;
		public boolean mutable;
		
		public GenomeSpecification(IGenome<GeneralGeneTypes, IGene> g, int type, String name) {
			this.g = g;
			this.genomeType = type;
			this.name = name;
		}
		
		public IGenome<GeneralGeneTypes, IGene> getGenome() {
			return g;
		}
		
		public void setGenome(IGenome<GeneralGeneTypes, IGene> g) {
			this.g = g;
		}
		
		public void setGenomeType(int genomeType) {
			this.genomeType = genomeType;
		}
		
		public int getGenomeType() {
			return genomeType;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
