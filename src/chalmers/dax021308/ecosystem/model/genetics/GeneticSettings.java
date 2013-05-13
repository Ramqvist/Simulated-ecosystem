package chalmers.dax021308.ecosystem.model.genetics;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

public class GeneticSettings {

	//TEST Setups
	public static GeneticSettings preySettings = new GeneticSettings(GenomeFactory.deerGenomeFactory());
	public static GeneticSettings predSettings = new GeneticSettings(GenomeFactory.wolfGenomeFactory());
	public static GeneticSettings grassSettings = new GeneticSettings(GenomeFactory.grassGenomeFactory());
	public static GeneticSettings grassFieldSettings = new GeneticSettings(GenomeFactory.grassGenomeFactory());

	private IGenome<GeneralGeneTypes, IGene> genome;
	private List<GeneSpecification> genes_d;
	private List<GeneSpecification> genes_b;

	public static void reInitialize() {
		preySettings = new GeneticSettings(GenomeFactory.deerGenomeFactory());
		predSettings = new GeneticSettings(GenomeFactory.wolfGenomeFactory());
	}

	protected GeneticSettings(IGenome<GeneralGeneTypes, IGene> genome){
		this.genome = genome;
		genes_d = new ArrayList<GeneSpecification>();
		genes_b = new ArrayList<GeneSpecification>();
		createLists(genome);
	}

	public IGenome<GeneralGeneTypes, IGene> getGenome(){
		return genome;
	}

	public List<GeneSpecification> getBooleanGeneSpecifications() {
		return genes_b;
	}

	public List<GeneSpecification> getDoubleGeneSpecifications() {
		return genes_d;
	}

	private void createLists(IGenome<GeneralGeneTypes, IGene> genome) {
		Iterator<Entry<GeneralGeneTypes,IGene>> it = genome.getAllGenes().entrySet().iterator();
		while(it.hasNext()) {
			Entry<GeneralGeneTypes,IGene> entry = it.next();
			GeneralGeneTypes key = entry.getKey();
			if (key.getType() == GeneSpecification.TYPE_BOOLEAN)
				this.genes_b.add(makeSpec(key, entry.getValue()));
			else
				this.genes_d.add(makeSpec(key, entry.getValue()));
		}
	}

	private GeneSpecification makeSpec(GeneralGeneTypes type, IGene g){
		return new GeneSpecification(g, type.getType(), type.getName());
	}

	public class GeneSpecification {
		//TODO: Make abstract class with predefines max/min/start values.
		public static final int TYPE_BOOLEAN = 1;
		public static final int TYPE_DOUBLE  = 2;

		private IGene g;
		private int genomeType;
		private String name;


		public GeneSpecification(IGene g, int type, String name) {
			this.g = g;
			this.genomeType = type;
			this.name = name;
		}

		public int getGeneType() {
			return genomeType;
		}

		public String getName() {
			return name;
		}

		public double getCurrentDoubleValue() {
			return g.getCurrentDoubleValue();
		}

		public void setCurrentDoubleValue(double v) {
			g.setCurrentDoubleValue(v);
		}

		public boolean isActiveOnBirth() {
			return g.isGeneActive();
		}

		public void setActiveOnBirth(boolean activeOnBirth) {
			g.setHaveGene(activeOnBirth);
		}

		public boolean isMutable() {
			return g.isMutable();
		}

		public void setMutable(boolean mutable) {
			g.setMutable(mutable);
		}

		public void setRandomStartValue(boolean random){
			g.setRandomStartValue(random);
		}

		public boolean hasRandomStartValue(){
			return g.hasRandomStartValue();
		}

		public void setMaxValue(double max){
			g.setMaxValue(max);
		}

		public void setMinValue(double min){
			g.setMinValue(min);
		}

		public double getMaxValue(){
			return g.getMaxValue();
		}

		public double getMinValue(){
			return g.getMinValue();
		}

	}
}
