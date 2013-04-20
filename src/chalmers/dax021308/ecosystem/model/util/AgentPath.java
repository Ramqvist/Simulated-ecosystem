package chalmers.dax021308.ecosystem.model.util;

import java.util.List;

/**
 * Class representing path to a specific target.
 * 
 * @author Erik Ramqvist
 *
 */
public class AgentPath {
	private List<Position> path;
	
	public AgentPath(List<Position> path) {
		this.path = path;
	}
	
	public void clearPath() {
		path.clear();
		path = null;
	}

	public List<Position> getPath() {
		return path;
	}
	
	public boolean isEmpty() {
		if(path == null || path.isEmpty()) {
			return false;
		}
		return true;
	}
	
	public Position peek() {
		return path.get(0);
	}
	
	public Position pop() {
		return path.remove(0);
	}
	
	public Position getNextPostion(Position current, int distance_threshold) {
		if(isEmpty()) {
			return null;
		}
		if(peek().getDistance(current) <= distance_threshold) {
			pop();
			if(isEmpty()) {
				return null;
			}
			return peek();
		} else {
			return peek();
		}
	}

	private int size() {
		if(path != null) {
			return path.size();
		}
		return 0;
	}
}
