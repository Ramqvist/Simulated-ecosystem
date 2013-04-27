package chalmers.dax021308.ecosystem.model.environment.mapeditor;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;


import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.util.Log;


/**
 * Class for containing a map.
 * <p>
 * Represented by a String and list of obstacles.
 * 
 * @author Erik Ramqvist
 *
 */
public class SimulationMap {
	public static final Dimension DEFAULT_OBSTACLE_DIMENSION = new Dimension(1000, 1000);
	
	private List<IObstacle> obsList;
	private String name;
	
	public SimulationMap(List<IObstacle> obsList, String name) {
		this.obsList = obsList;
		this.name = name;
	}
	
	public SimulationMap(String name) {
		this.obsList = new ArrayList<IObstacle>();
		this.name = name;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<IObstacle> getObsList() {
		return obsList;
	}
	/**
	 * Use this method in simulation program to get the correct scaled versions of the obstacles. 
	 * <p>
	 * Maps are encoded using 1000 x 1000 dimension.
	 * 
	 * @param dim The Dimension to scale to.
	 * @return the up-scaled obstacles, or null or empty list if this is not a valid map.
	 */
	public List<IObstacle> getScaledObstacles(Dimension dim) {
		if(!isValidMap()) {
			return null;
		}
		double scaleX = dim.getWidth() / DEFAULT_OBSTACLE_DIMENSION.width;
		double scaleY = dim.getHeight() / DEFAULT_OBSTACLE_DIMENSION.height;

		List<IObstacle> result = new ArrayList<IObstacle>(obsList.size());
		for(IObstacle o: obsList) {
			result.add(o.scale(scaleX, scaleY));
		}
		return result;
	}
	public void setObsList(List<IObstacle> obsList) {
		this.obsList = obsList;
	}

	public void addObstacle(IObstacle o) {
		if(obsList != null) {
			obsList.add(o);
		}
	}

	public boolean removeObstacle(IObstacle o) {
		if(obsList != null) {
			return obsList.remove(o);
		}
		return false;
	}

	public boolean contains(IObstacle o) {
		if(obsList != null) {
			return obsList.contains(o);
		}
		return false;
	}
	
	@Override
	public String toString() {
		return name;
	}
	public boolean isValidMap() {
		if(getName() == null || getObsList() == null) {
			return false;
		}
		if(getObsList().isEmpty()) {
			return false;
		}
		if(name.equals("")) {
			return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((obsList == null) ? 0 : obsList.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof SimulationMap))
			return false;
		SimulationMap other = (SimulationMap) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (obsList == null) {
			if (other.obsList != null)
				return false;
		} 
		if(this.obsList.size() != other.getObsList().size()) {
			return false;
		}
		return true;
	}
	
}