package chalmers.dax021308.ecosystem.view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Observable;

import chalmers.dax021308.ecosystem.model.EcoWorld;
import chalmers.dax021308.ecosystem.model.IObstacle;
import chalmers.dax021308.ecosystem.model.IPopulation;

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


	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String eventName = event.getPropertyName();
		if(eventName == EcoWorld.EVENT_STOP) {
			//Model has stopped. Maybe hide view?
		} else if(eventName == EcoWorld.EVENT_TICK) {
			//Tick notification recived from model. Do something with the data.
			if(event.getNewValue() instanceof List<?>) {
				List<IPopulation> newPops = (List<IPopulation>) event.getNewValue();
			}
			if(event.getOldValue() instanceof List<?>) {
				List<IObstacle> newObs = (List<IObstacle>) event.getOldValue();
			}
		}
	}
}
