package chalmers.dax021308.ecosystem.controller.scripting;

import java.beans.PropertyChangeEvent;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Log;

public class MultiThreadedPerformanceScript implements IScript {

	private EcoWorld model;
	private List<IPopulation> lastPop;
	private int NUM_ITERATION_PER_SIM = 1000;
	private int rounds = 10;

	@Override
	public void init(EcoWorld e) {
		this.model = e;
		e.addObserver(this);
		e.loadSimulationSettings(SimulationSettings.DEFAULT);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == EcoWorld.EVENT_TICK) {
			this.lastPop = (List<IPopulation>) evt.getNewValue();
		}
		if(evt.getPropertyName() == EcoWorld.EVENT_FINISHED) {
			onFinishOneRun();
		}
	}


	@Override
	public void onFinishOneRun() {
		SimulationSettings s = SimulationSettings.DEFAULT;
		s.setDelayLength(0);
		s.setRunWithoutTimer(true);
		s.setNumIterations(NUM_ITERATION_PER_SIM);
		model.loadSimulationSettings(s);
		model.start();
	}

	@Override
	public void postRun() {
		Log.v("Finished performance run");
	}

	@Override
	public String getName() {
		return "Multithreaded Performance test";
	}

	@Override
	public String toString() {
		return getName();
	}
}
