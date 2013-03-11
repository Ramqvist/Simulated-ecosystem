package chalmers.dax021308.ecosystem.model.util;

/**
 * 
 * @author Henrik A class for handling four items of the same type
 * @param <T>
 *            The type to be handled
 */
public class DoublePair<T> {
	private T left;
	private T right;
	private T top;
	private T bottom;

	/**
	 * 
	 * @param left The left item
	 * @param right The right item
	 * @param bottom The bottom item
	 * @param top The top item
	 */
	public DoublePair(T left, T right, T bottom, T top) {
		this.left = left;
		this.right = right;
		this.bottom = bottom;
		this.top = top;
	}

	public T getLeft() {
		return left;
	}

	public T getRight() {
		return right;
	}

	public T getTop() {
		return top;
	}

	public T getBottom() {
		return bottom;
	}
}