package chalmers.dax021308.ecosystem.view;

import java.util.Observer;

/**
 * IVew inteface.
 * @author Erik
 *
 */
public interface IView extends Observer {
	
	/**
	 * Initialize the View.
	 */
	public void init();
	
	/**
	 * Redraw the GUI, should be implemented fast to allow high frame-rate.
	 */
	public void onTick();
	

	/**
	 * Releases all the resources held by this Controller.
	 */
	public void release();
}
