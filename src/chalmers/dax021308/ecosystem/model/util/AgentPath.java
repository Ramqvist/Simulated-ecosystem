package chalmers.dax021308.ecosystem.model.util;

import java.util.List;

/**
 * Class representing path to a specific target.
 * <p>
 * Has an internal ttl value to determine how long this path is valid.
 * 
 * @author Erik Ramqvist
 *
 */
public class AgentPath {
	
	private List<Position> path;
	private int ttl;
	
	public AgentPath() {
	}
	
	public AgentPath(List<Position> path) {
		this.path = path;
	}
	

	public AgentPath(List<Position> path, int initial_ttl) {
		this.ttl = initial_ttl;
		this.path = path;
	}
	
	public void clearPath() {
		if(path != null) {
			path.clear();
		}
		path = null;
	}

	public List<Position> getPath() {
		return path;
	}
	
	public void setPath(List<Position> path) {
		this.path = path;
	}

	public void setPath(List<Position> path, int initial_ttl) {
		this.ttl = initial_ttl;
		this.path = path;
	}
	
	public boolean isEmpty() {
		if(path == null) { 
			return true;
		}
		return path.isEmpty();
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

	public int size() {
		if(path != null) {
			return path.size();
		}
		return 0;
	}
	
	public boolean isValid() {
		return ttl > 0;
	}
	
	public int getTTL() {
		return ttl;
	}
	
	public void setTTL(int ttl) {
		this.ttl = ttl;
	}
	
	public void decreaseTTL() {
		ttl--;
	}

	@Override
	public String toString() {
		if(path != null){
			return path.toString();
		} else {
			return null;
		}
	}
}
