package chalmers.dax021308.ecosystem.view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeListener;
import java.util.Observer;

import chalmers.dax021308.ecosystem.controller.IController;

/**
 * IVew interface. Extends {@link PropertyChangeListener} interface.
 * <p>
 * Should hold references the all the view and the model.
 * It listens to commands from the model via. the observer pattern.
 * <p>
 * Taking in a refrence to the model in the constructor is recommended.
 * 
 * @author Hanna & Erik
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
	 * <p>
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
