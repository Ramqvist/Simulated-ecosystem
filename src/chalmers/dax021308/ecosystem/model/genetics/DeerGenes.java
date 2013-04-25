package chalmers.dax021308.ecosystem.model.genetics;


/**
 * 
 * @author Loanne Berggren
 *
 */
//public enum DeerGenes implements IGenes
public enum DeerGenes implements IGenes
{
	STOTTING(0, 1),
	GROUPING(1, 1),
	// To add gene: genename(startindex, #bits)
	// ex JUNK(2, 4)
	;

	public static final double MUTATION_PROBABILITY = 0.05;
	public static final int NUMBER_OF_GENES = DeerGenes.values().length;
	private static int totalLengthOfGenes = 0;
	private int startIndex;
	private int geneSize;

	private DeerGenes(int startIndex, int geneSize) {
		this.startIndex = startIndex;
		this.geneSize = geneSize;
	}
	
	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IGenesEnum#getNumberOfGenes()
	 */
	public int getNumberOfGenes() {
		return NUMBER_OF_GENES;
	}
	
	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IGenes#getTotalSizeOfGenes()
	 */
	@Override
	public int getTotalLengthOfGenes() {
		return totalLengthOfGenes();
	}
		
	public static int totalLengthOfGenes(){
		if (DeerGenes.totalLengthOfGenes == 0){
			for (DeerGenes e : DeerGenes.values()) {
				DeerGenes.totalLengthOfGenes += e.geneSize;
			}
		}
		return totalLengthOfGenes;
	}
	
	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IGenesEnum#getGeneStartIndex()
	 */
	@Override
	public int getGeneStartIndex() { return startIndex; }
	
	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IGenesEnum#getGeneSize()
	 */
	@Override
	public int getGeneSize() { return geneSize; }

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IGenes#getMutationProbability()
	 */
	@Override
	public double getMutationProbability() {
		return MUTATION_PROBABILITY;
	}
}