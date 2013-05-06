package chalmers.dax021308.ecosystem.controller;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
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
	
	private static final int NUM_ITERATIONS = 200;
	private int rounds = 10;
	private List<IPopulation> lastPop;
	

	public ScriptingController() {
		s = SimulationSettings.EXTREME;
		controller = new MainWindowController();
		controller.model.addObserver(this);
		controller.window.smvc.toggleVisibility();
		s.setNumIterations(NUM_ITERATIONS);
		s.setDelayLength(0);
		s.setNumThreads(8);
		s.setRunWithoutTimer(true);
		startNewRound();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == EcoWorld.EVENT_TICK) {
			this.lastPop = (List<IPopulation>) evt.getNewValue();
		}
		if(evt.getPropertyName() == EcoWorld.EVENT_FINISHED) {
			startNewRound();
		}
	}
	
	private void onFinish() {
		for(IPopulation p : lastPop) {
			if(p.getName().equals("Deers")) {
				p.getLifeLengthMean();
				break;
			}
		}
	}

	private void startNewRound() {
		if(rounds-- > 0) {
			Log.v("Rounds Left: " + rounds);
			controller.model.loadSimulationSettings(s);
			controller.model.start();
		} else {
			onFinish();
			Log.e("--- Scripted run finished ---");
		}
	}
}
