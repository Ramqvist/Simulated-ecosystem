package chalmers.dax021308.ecosystem.model.util;

/**
 * Simple container class.
 * 
 * @author Erik Ramqvist
 *
 * @param <T>
 */
public class Container<T> {
	private T item;
	
	public Container(T item) {
		this.item = item;
	}
	
	public T get() {
		return item;
	}
	
	public void set(T item) {
		this.item = item;
	}
	
	@Override
	public String toString() {
		if(item == null) {
			return "null";
		}
		return item.toString();
	}
	
	@Override
	public boolean equals(Object obj) {
		if(item == null) {
			return false;
		}
		return item.equals(obj);
	}
}
