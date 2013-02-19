package chalmers.dax021308.ecosystem.model;

/**
 * The generic Pair class
 * 
 * @author Henrik
 * 
 * @param <startNumber>
 *            The first item of the pair
 * @param <endNumber>
 *            The second item of the pair
 */
// Helps the Obstacle class for creating pairs of start
// and end positions on a line for where the obstacle begins and ends
public class Pair<startNumber, endNumber> {
	private startNumber start;
	private endNumber end;

	public Pair(startNumber start, endNumber end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Getter for the first item of the pair
	 * 
	 * @return the first item of the pair is returned
	 */
	public startNumber getStart() {
		return start;
	}

	/**
	 * Getter for the second item of the pair
	 * 
	 * @return the second item of the pair is returned
	 */
	public endNumber getEnd() {
		return end;
	}

	/**
	 * Setter for the first item of the pair
	 * 
	 * @param start
	 *            the item to be set
	 */
	public void setStart(startNumber start) {
		this.start = start;
	}

	/**
	 * Setter for the second item of the pair
	 * 
	 * @param start
	 *            the item to be set
	 */
	public void setEnd(endNumber end) {
		this.end = end;
	}
	
	@Override
	public String toString(){
		return "("+this.getStart()+","+this.getEnd()+")";
	}
}
