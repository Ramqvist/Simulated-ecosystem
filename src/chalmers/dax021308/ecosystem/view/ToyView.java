package chalmers.dax021308.ecosystem.view;

import java.awt.event.ActionListener;
import java.util.Observable;

import chalmers.dax021308.ecosystem.model.EcoWorld;

/**
 * "Toy" view, only for getting MVC structure running. Also for use as template.
 * 
 * @author Erik Ramqvist
 *
 */
public class ToyView implements IView {
	
	//Holding a reference to the model might not be necessary.
	private EcoWorld model;
	
	public ToyView(EcoWorld model) {
		this.model = model;
		model.addObserver(this);
	}

	@Override
	public void update(Observable object, Object source) {
		// TODO Auto-generated method stub
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
	}

	@Override
	public void addController(ActionListener controller) {
		// TODO Auto-generated method stub
	}

	@Override
	public void onTick() {
		// TODO Auto-generated method stub
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
	}
}
