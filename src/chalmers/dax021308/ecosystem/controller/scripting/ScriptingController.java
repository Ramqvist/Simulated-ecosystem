package chalmers.dax021308.ecosystem.controller.scripting;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import chalmers.dax021308.ecosystem.controller.MainWindowController;
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
@Deprecated
public class ScriptingController implements PropertyChangeListener {

	private MainWindowController controller;
	private SimulationSettings s;
	
	private static final int NUM_ITERATIONS = Integer.MAX_VALUE;
	private int rounds = 1;
	private List<IPopulation> lastPop;
	private EcoWorld model;
	

	public ScriptingController() {
		s = SimulationSettings.FASTRUN;
//		controller = new MainWindowController();
		model = new EcoWorld();
//		controller = new MainWindowController();
//		model = controller.model;
//		controller.window.smvc.toggleVisibility();
		model.addObserver(this);
		s.setNumIterations(NUM_ITERATIONS);
		s.setDelayLength(0);
		s.setNumThreads(8);
		s.setRunWithoutTimer(true);
		startNewRound();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
//		if(evt.getPropertyName() == EcoWorld.EVENT_TICK) {
//			this.lastPop = (List<IPopulation>) evt.getNewValue();
//		}
		if(evt.getPropertyName() == EcoWorld.EVENT_FINISHED) {
			startNewRound();
		}
	}
	
	private void onFinish() {
//		for(IPopulation p : lastPop) {
//			if(p.getName().equals("Deers")) {
//				p.getLifeLengthMean();
//				break;
//			}
//		}
	}

	private void startNewRound() {
		if(rounds-- > 0) {
			Log.v("Rounds Left: " + rounds);
			model.loadSimulationSettings(s);
			model.start();
		} else {
			onFinish();
			Log.e("--- Scripted run finished ---");
		}
	}
}
