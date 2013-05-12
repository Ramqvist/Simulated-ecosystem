package chalmers.dax021308.ecosystem.controller.scripting;

import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Random;

import chalmers.dax021308.ecosystem.controller.scripting.ScriptHandler.OnFinishedScriptListener;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Log;

/**
 * Simple script, used to create new ones. Copy and rename this class.
 * Used for learning purpose only.
 * <p>
 * Tried to make is as straightforward as possible.
 *
 * @author Loanne Berggren, but mostly copied from MySillyScript.java
 *
 *
 */
public class MyBlubbScript implements IScript {

	private EcoWorld model;
	private List<IPopulation> lastPopulation;
	private OnFinishedScriptListener listener;

	private static final String name = "Simple Script";
	private static final int NUM_ITERATION_PER_SIMULATION = 100000;

	/**
	 * Use this to initialize variables.
	 * Called from ScriptHandler when this is selected to run.
	 */
	@Override
	public void init(EcoWorld e, OnFinishedScriptListener listener) {
		this.listener = listener;
		this.model = e;
		e.addObserver(this);
		e.loadSimulationSettings(set());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == EcoWorld.EVENT_TICK) {
			this.lastPopulation = (List<IPopulation>) evt.getNewValue();
		}
		if(evt.getPropertyName() == EcoWorld.EVENT_FINISHED) {
			onFinishOneRun();
		}
	}

	/**
	 * Called when the whole script is finished.
	 * <p>
	 * May shut down computer if Shutdown checkbox is checked.
	 */
	@Override
	public void onFinishScript() {
		Log.v("onFinishScript");
		listener.onFinishScript();
	}

	/**
	 * Called in each simulation-run when EcoWorld runs out of iterations to run.
	 */
	@Override
	public void onFinishOneRun() {
		Log.v("onFinishOneRun");
		if(isThisScriptFinished()) {
			onFinishScript();
		} else {
			model.loadSimulationSettings(set());
			model.start();
		}
	}

	/**
	 * Silly method.
	 * @return
	 */
	private boolean isThisScriptFinished() {
		return new Random().nextBoolean();
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return getName();
	}

	private SimulationSettings set() {
		SimulationSettings s = SimulationSettings.DEFAULT;
		s.setDelayLength(0);
		s.setRunWithoutTimer(true);
		s.setNumIterations(NUM_ITERATION_PER_SIMULATION);
		return s;
	}
}
