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
	
	public static final String EVENT_OBSTACLESCHANGED = "chalmers.dox021308.ecosystem.MapEditorModel.event_obstacles_changed";
	
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
		}
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
		pcs.firePropertyChange(EcoWorld.EVENT_TICK, currentMap.getObsList(), null);
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
