package chalmers.dax021308.ecosystem.controller;

import chalmers.dax021308.ecosystem.model.environment.IModel;

/**
 * IController inteface.
 * 
 * @author Erik
 * 
 */
public interface IController {
	/**
	 * Called to initialize the controller.
	 */
	public void init();

	/**
	 * Releases all the resources held by this Controller.
	 */
	public void release();
	
	/**
	 * Set the model used by this model.
	 */
	public void setModel(IModel m);
}
