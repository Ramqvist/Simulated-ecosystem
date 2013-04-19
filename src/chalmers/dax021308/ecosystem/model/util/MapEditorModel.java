package chalmers.dax021308.ecosystem.model.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.SimulationMap;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;

/**
 * Model class for the map editor.
 * <p>
 * 
 * @author Erik
 *
 */
public class MapEditorModel implements IModel {
	
	public static final String EVENT_OBSTACLESCHANGED = "chalmers.dox021308.ecosystem.MapEditorModel.event_obstacles_changed";
	
	private PropertyChangeSupport pcs;
	private SimulationMap currentMap;

	public MapEditorModel() {
		pcs = new PropertyChangeSupport(this);
	}
	
	public void createNewMap(String name) {
		currentMap = new SimulationMap(name);
	}
	
	
	public void addObstacle(IObstacle o) {
		currentMap.addObstacle(o);
	}
	
	private void fireObstaclesUpdate() {
		
	}
	

	@Override
	public void addObserver(PropertyChangeListener observer) {
		pcs.addPropertyChangeListener(observer);
	}

	@Override
	public void removeObserver(PropertyChangeListener observer) {
		pcs.removePropertyChangeListener(observer);
	}
	
}
