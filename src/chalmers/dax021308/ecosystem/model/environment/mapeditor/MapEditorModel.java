package chalmers.dax021308.ecosystem.model.environment.mapeditor;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;

/**
 * Model class for the map editor.
 * <p>
 * 
 * @author Erik
 *
 */
public class MapEditorModel implements IModel {
	
	public static final String EVENT_OBSTACLES_CHANGED = "chalmers.dox021308.ecosystem.MapEditorModel.event_obstacles_changed";
	public static final String EVENT_MAPNAME_CHANGED = "chalmers.dox021308.ecosystem.MapEditorModel.event_mapname_changed";
	
	private PropertyChangeSupport pcs;
	private SimulationMap currentMap;
	
	private Object syncObject = new Object();

	public MapEditorModel() {
		pcs = new PropertyChangeSupport(this);
	}
	
	public void createNewMap(String name) {
		synchronized (syncObject) {
			currentMap = new SimulationMap(name);
			fireObstaclesChanged();
			notifyMapNameChanged();
		}
	}
	
	public boolean hasValidMap() {
		if(currentMap == null) {
			return false;
		}
		if(currentMap.getObsList() == null) {
			return false;
		}
		if(currentMap.getName() == null) {
			return false;
		}
		if(currentMap.getName().equals("")) {
			return false;
		}
		if(currentMap.getObsList().isEmpty()) {
			return false;
		}
		return true;
	}
	
	public void notifyMapNameChanged() {
		pcs.firePropertyChange(EVENT_MAPNAME_CHANGED, null, currentMap.getName());
	}
	
	public boolean loadMap(SimulationMap m) {
		if(m == null) {
			return false;
		}
		if(m.getName() == null || m.getObsList() == null) {
			return false;
		}
		if(m.getObsList().isEmpty()) {
			return false;
		}
		currentMap = m;
		fireObstaclesChanged();
		notifyMapNameChanged();
		return true;
	}
	
	public void addObstacle(IObstacle o) {
		if(currentMap == null || o == null) {
			return;
		}
		synchronized (syncObject) {
			if(!currentMap.contains(o)) {
				currentMap.addObstacle(o);
				fireObstaclesChanged();
			}
		}
	}
	
	public void removeObstacle(IObstacle o) {
		if(currentMap == null || o == null) {
			return;
		}
		synchronized (syncObject) {
			currentMap.removeObstacle(o);
			fireObstaclesChanged();
		}
	}
	
	private synchronized void fireObstaclesChanged() {
		pcs.firePropertyChange(EVENT_OBSTACLES_CHANGED, null, currentMap.getObsList());
	}
	
	@Override
	public void addObserver(PropertyChangeListener observer) {
		pcs.addPropertyChangeListener(observer);
	}

	@Override
	public void removeObserver(PropertyChangeListener observer) {
		pcs.removePropertyChangeListener(observer);
	}

	public SimulationMap getCurrentMap() {
		return currentMap;
	}
	
}
