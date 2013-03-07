package chalmers.dax021308.ecosystem.model.util;

/**
 * The generic Pair class
 * 
 * @author Henrik
 * 
 * @param <T>
 *            The first item of the pair
 * @param <T>
 *            The second item of the pair
 */
// Helps the Obstacle class for creating pairs of start
// and end positions on a line for where the obstacle begins and ends
public class Pair<T> {
	private T start;
	private T end;

	public Pair(T start, T end) {
		this.start = start;
		this.end = end;
	}

	/**
	 * Getter for the first item of the pair
	 * 
	 * @return the first item of the pair is returned
	 */
	public T getStart() {
		return start;
	}

	/**
	 * Getter for the second item of the pair
	 * 
	 * @return the second item of the pair is returned
	 */
	public T getEnd() {
		return end;
	}

	/**
	 * Setter for the first item of the pair
	 * 
	 * @param start
	 *            the item to be set
	 */
	public void setStart(T start) {
		this.start = start;
	}

	/**
	 * Setter for the second item of the pair
	 * 
	 * @param start
	 *            the item to be set
	 */
	public void setEnd(T end) {
		this.end = end;
	}
	
	@Override
	public String toString(){
		return "("+this.getStart()+","+this.getEnd()+")";
	}
}
