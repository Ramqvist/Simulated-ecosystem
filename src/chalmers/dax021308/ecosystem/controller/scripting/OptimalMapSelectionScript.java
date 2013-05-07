package chalmers.dax021308.ecosystem.controller.scripting;

import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chalmers.dax021308.ecosystem.controller.scripting.ScriptHandler.OnFinishedScriptListener;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapFileHandler;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.SimulationMap;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Log;

/**
 * Script to run for selecting the optimal or inverse-optimal map for the given population.
 * 
 * @author Erik Ramqvist
 *
 */
public class OptimalMapSelectionScript implements IScript {
	
	private Map<SimulationMap, Long> bestMaps;
	private Map<SimulationMap, Long> worstMaps;
	private EcoWorld e;
	private OnFinishedScriptListener listener;
	private SimulationMap lastMap;
	private List<IPopulation> lastPop;
	private int NUM_ITERATION_PER_SIM = 1000;
	private int rounds = 10;
	

	@Override
	public void init(EcoWorld e, OnFinishedScriptListener listener) {
		this.listener = listener;
		this.e = e;
		e.addObserver(this);

		bestMaps  = new HashMap<SimulationMap, Long>();
		worstMaps = new HashMap<SimulationMap, Long>();
		
		SimulationSettings s = SimulationSettings.DEFAULT;
		e.loadSimulationSettings(s);
	}

	@Override
	public void onFinishOneRun() {
		//Handle data
		//TODO: Decrease counter;
		onFinishScript();
		SimulationSettings s = SimulationSettings.DEFAULT;
		s.setNumIterations(NUM_ITERATION_PER_SIM);
		lastMap = new SimulationMap(null, null);
		s.setMap(lastMap);
		e.loadSimulationSettings(s);
		e.start();
	}

	@Override
	public void onFinishScript() {
		listener.onFinishScript();
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
	public String getName() {
		return "Optimal Map Selection Script";
	} 
	
	@Override
	public String toString() {
		return getName();
	}
	
	private void dumpMapsToFile(String fileHeader, List<SimulationMap> maps) {
		int counter = 1;
		for(SimulationMap m : maps) {
			m.setName(fileHeader + "_" + counter++);
			MapFileHandler.saveSimulationMap(m);
		}
	}
	
	private void printObstacles(List<SimulationMap> maps) {
		if(maps == null) {
			return;
		}
		for(SimulationMap m : maps) {
			printMap(m);
		}
	}
	
	private void printMap(SimulationMap m) {
		if(m == null) {
			return;
		}
		for(IObstacle o : m.getObsList()) {
			Log.v(o.toBinaryString());
		}
	}

}
