package chalmers.dax021308.ecosystem.controller;

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
}
