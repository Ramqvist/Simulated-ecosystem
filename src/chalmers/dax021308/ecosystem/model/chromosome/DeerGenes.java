package chalmers.dax021308.ecosystem.model.chromosome;

import java.util.EnumSet;

/**
 * 
 * @author Loanne Berggren
 *
 */
/*public class DeerGenes extends AbstractGenes<DeerGenes.DG>
{
	protected enum DG{
		STOTTING(0),
		FEMALE(1),
		JUNK(3);

		private int value;

		public int getValue() {
			return value;
		}

		private DG(int value) {
			this.value = value;
		}
		private static final int numberOfGenes = DG.values().length;


	}
	//private static final int numberOfGenes = DG.values().length;

	private DeerGenes() {
		super(DG.class);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public AbstractGenes<?> genesFactory() {
		// TODO Auto-generated method stub
		return new DeerGenes();
	}

}

*/


//public enum DeerGenes implements IGenes<DeerGenes>
public enum DeerGenes
{
	STOTTING(0),
	FEMALE(1)
	// To add gene: genename(#)
	// ex JUNK(2)
	;
	
	private int index;
	private static final int numberOfGenes = DeerGenes.values().length;
	
	public int getIndex() {
		return index;
	}
	
	public static int getNumberOfGenes2() {
		return numberOfGenes;
	}
	
	private DeerGenes(int index) {
		this.index = index;
	}

}