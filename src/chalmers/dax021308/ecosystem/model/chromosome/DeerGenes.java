package chalmers.dax021308.ecosystem.model.chromosome;

/**
 * 
 * @author Loanne Berggren
 *
 */

public enum DeerGenes
{
	STOTTING(0),
	GROUPING(1),
	// To add gene: genename(#)
	// ex JUNK(2)
	;

	
	private int index;
	private static final int numberOfGenes = DeerGenes.values().length;
	
	public int getIndex() {
		return index;
	}
	
	public static int getNumberOfGenes() {
		return numberOfGenes;
	}
	
	private DeerGenes(int index) {
		this.index = index;
	}

}