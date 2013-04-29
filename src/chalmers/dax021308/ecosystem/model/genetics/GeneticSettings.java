package chalmers.dax021308.ecosystem.model.genetics;

import java.util.ArrayList;
import java.util.List;

public abstract class GeneticSettings {
	
	public static GeneticSettings preySettings = new GeneticSettings() {
		
		@Override
		public List<GenomeSpecification> getGenomes() {
			List<GenomeSpecification> result = new ArrayList<GenomeSpecification>();
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_BOOLEAN, "Grouping"));
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_BOOLEAN, "Focus prey"));
			result.add(new GenomeSpecification(new Genome(null), GenomeSpecification.TYPE_INTEGER, "Grouping Q parameter"));
			return result;
		}
	};
	
	public abstract List<GenomeSpecification> getGenomes();
	
	public class GenomeSpecification {
		//TODO: Make abstract class with predefines max/min/start values.
		public static final int TYPE_BOOLEAN = 1;
		public static final int TYPE_INTEGER = 2;
		public static final int TYPE_DOUBLE  = 3;
		
		private Genome g;
		private int genomeType;
		private String name;
		
		public int intValue;
		public double doubleValue;
		public boolean boolean1;
		public boolean boolean2;
		
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
