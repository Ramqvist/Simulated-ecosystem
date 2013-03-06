package chalmers.dax021308.ecosystem.model.environment;

import java.beans.PropertyChangeListener;

public interface IModel {
	public void addObserver(PropertyChangeListener observer);
	public void removeObserver(PropertyChangeListener observer);
}
