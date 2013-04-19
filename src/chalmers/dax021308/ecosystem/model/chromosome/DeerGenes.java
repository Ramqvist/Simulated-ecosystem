package chalmers.dax021308.ecosystem.model.chromosome;

/**
 * 
 * @author Loanne Berggren
 *
 */
public enum DeerGenes
{
	STOTTING(0),
	FEMALE(1),
	JUNK(3);		
	private int value;
	public int getValue() {
		return value;
	}
	private static final int numberOfGenes = 3;
	public static int getNumberOfGenes() {
		return numberOfGenes;
	}
	private DeerGenes(int value) {
		this.value = value;
	}
}