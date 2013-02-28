package chalmers.dax021308.ecosystem.view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

/**
 * 
 * @author Loanne
 * Useless so far.
 */
public interface IGraphView extends IView{

	@Override
	public void propertyChange(PropertyChangeEvent event);

	@Override
	public void init();

	@Override
	public void addController(ActionListener controller);

	@Override
	public void onTick();

	@Override
	public void release();
	
}

  