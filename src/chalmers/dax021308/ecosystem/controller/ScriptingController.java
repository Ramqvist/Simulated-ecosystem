package chalmers.dax021308.ecosystem.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.util.Log;

/**
 * Special Controller that can be scripted to run EcoWorld several times.
 * 
 * @author Erik Ramqvist
 *
 */
public class ScriptingController implements PropertyChangeListener {

	private MainWindowController controller;
	private SimulationSettings s;
	
	private static final int NUM_ITERATIONS = 50000;
	private int rounds = 10;

	public ScriptingController() {
		s = SimulationSettings.DEFAULT;
		controller = new MainWindowController();
		controller.model.addObserver(this);
		s.setNumIterations(NUM_ITERATIONS);
		s.setDelayLength(0);
		startNewRound();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == EcoWorld.EVENT_FINISHED) {
			startNewRound();
		}
	}

	private void startNewRound() {
		if(rounds-- > 0) {
			controller.model.loadSimulationSettings(s);
			controller.model.start();
		} else {
			Log.e("--- Scripted run finished ---");
		}
	}
	
	
	
	
}
