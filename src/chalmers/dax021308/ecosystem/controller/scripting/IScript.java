package chalmers.dax021308.ecosystem.controller.scripting;

import java.beans.PropertyChangeListener;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;

/**
 * Simple script run for statistical runs.
 * <p>
 * init(EcoWorld e) is called at the start of every script the rest is up to you to handle.
 *
 * 
 * @author Erik Ramqvist
 *
 */
public interface IScript extends PropertyChangeListener {
	
	/**
	 * Initialize the script.
	 * Add yourself as listener and load the wanted {@link SimulationSettings} object of your choice.
	 * 
	 * Don't use constructor as initializer. init will be called automatically. EcoWorld will be started aswell.
	 * 
	 * @param e
	 */
	public void init(EcoWorld e);

	/**
	 * This is managed by the extending class.
	 */
	public void onFinishOneRun();
	
	/**
	 * This is managed by the extending class.
	 */
	public void postRun();
	
	/**
	 * This is only used for the GUI to distinguish the scripts apart.
	 * @return
	 */
	public String getName();
}
