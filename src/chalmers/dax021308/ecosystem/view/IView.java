package chalmers.dax021308.ecosystem.view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Observer;

import chalmers.dax021308.ecosystem.controller.IController;

/**
 * IVew inteface. Extends {@link Observer} interface.
 * 
 * @author Hanna
 *
 */
public interface IView extends PropertyChangeListener {
	
	/**
	 * Initialize the View.
	 */
	public void init();
	
	/**
	 * Add an {@link ActionListener} to the IView, from the {@link IController}. 
	 * <p>
	 * Example:
	 * <p>
	 * myButton.addActionListener(controller);
	 */
	public void addController(ActionListener controller);
	
	/**
	 * Redraw the GUI, should be implemented fast to allow high frame-rate.
	 */
	public void onTick();
	

	/**
	 * Releases all the resources held by this Controller.
	 * <p>
	 * For example removes all the {@link ActionListener}, hides all the Views.
	 * <p>
	 * A {@link System#gc()} call might be appropriate. 
	 */
	public void release();
}
