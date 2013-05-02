package chalmers.dax021308.ecosystem.model.genetics;

import java.util.ArrayList;
import java.util.List;

public abstract class GeneticSettings {
	
	//TEST Setups
	public static GeneticSettings preySettings = new GeneticSettings() {
		
		@Override
		public List<GenomeSpecification> getGenomes() {
			List<GenomeSpecification> result = new ArrayList<GenomeSpecification>();
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_BOOLEAN, "Grouping"));
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_BOOLEAN, "Focus prey"));
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_DOUBLE, "Grouping Q parameter"));
			return result;
		}
	};
	public static GeneticSettings predSettings = new GeneticSettings() {
		
		@Override
		public List<GenomeSpecification> getGenomes() {
			List<GenomeSpecification> result = new ArrayList<GenomeSpecification>();
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_DOUBLE, "Pred interaction range"));
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_BOOLEAN, "Focus prey"));
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_BOOLEAN, "Pathfinding"));
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_BOOLEAN, "Eat BigTasty"));
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_DOUBLE, "Grouping probability"));
			return result;
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
	};
	
	public abstract List<GenomeSpecification> getGenomes();
	
	public class GenomeSpecification {
		//TODO: Make abstract class with predefines max/min/start values.
		public static final int TYPE_BOOLEAN = 1;
		public static final int TYPE_DOUBLE  = 2;
		
		private Genome g;
		private int genomeType;
		private String name;
		
		public int intValue;
		public double doubleValue;
		public boolean activeOnBirth;
		public boolean mutable;
		
		public GenomeSpecification(Genome g, int type, String name) {
			this.g = g;
			this.genomeType = type;
			this.name = name;
		}
		
		public Genome getGenome() {
			return g;
		}
		
		public void setGenome(Genome g) {
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
