package chalmers.dax021308.ecosystem.model.environment;

import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;


/**
 * Class for containing a map.
 * <p>
 * Represented by a String and list of obstacles.
 * 
 * @author Erik Ramqvist
 *
 */
public class SimulationMap {
	private List<IObstacle> obsList;
	private String name;
	
	public SimulationMap(List<IObstacle> obsList, String name) {
		this.obsList = obsList;
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
	public void setObsList(List<IObstacle> obsList) {
		this.obsList = obsList;
	}
}