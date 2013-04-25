package chalmers.dax021308.ecosystem.model.genetics;


/**
 * 
 * @author Loanne Berggren
 *
 */
public enum WolfGenes implements IGenes
{
	GROUPING(0, 1),
	FEMALE(1, 1),
	JUNK(2, 1);
	
	public static final double MUTATION_PROBABILITY = 0.1;
	public static final int NUMBER_OF_GENES = WolfGenes.values().length;
	private static int totalLengthOfGenes = 0;
	private int startIndex;
	private int geneSize;
	
	
	private WolfGenes(int index, int geneSize) {
		this.startIndex = index;
		this.geneSize = geneSize;
	}
	
	@Override
	public int getGeneStartIndex() {
		return startIndex;
	}
	
	@Override
	public int getGeneSize() { 
		return geneSize; 
	}
	
	@Override
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
		if (WolfGenes.totalLengthOfGenes == 0){
			for (WolfGenes e : WolfGenes.values()) {
				WolfGenes.totalLengthOfGenes += e.geneSize;
			}
		}
		return totalLengthOfGenes;
	}

	/* (non-Javadoc)
	 * @see chalmers.dax021308.ecosystem.model.genetics.IGenes#getMutationProbability()
	 */
	@Override
	public double getMutationProbability() {
		return MUTATION_PROBABILITY;
	}

}