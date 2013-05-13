package chalmers.dax021308.ecosystem.controller.scripting;

import java.beans.PropertyChangeEvent;
import java.util.Collection;
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
	
	private long currentPopulationPoints = 0;
	
	private int rounds = 10;
	
	private static final String populationToTrack = "Deers";
	private static final int NUM_ITERATION_PER_SIM = 100;
	private static final int NUM_BESTMAPS  = 5;
	private static final int NUM_WORSTMAPS = 5;
	
	private static final int PHASE_BROAD_RANDOMINESS 	= 1;
	private static final int PHASE_ANALYSE_RESULT 		= 2;
	private static final int PHASE_FINALIZE_MAPS 		= 3;
	
	private int current_phase = PHASE_BROAD_RANDOMINESS;
	private String name = "Optimal Map Selection Script";
	

	@Override
	public void init(EcoWorld e, OnFinishedScriptListener listener) {
		this.listener = listener;
		this.e = e;
		e.addObserver(this);

		bestMaps  = new HashMap<SimulationMap, Long>();
		worstMaps = new HashMap<SimulationMap, Long>();
		
		SimulationSettings s = SimulationSettings.DEFAULT;
		lastMap = SimulationMap.randomMap();
		s.setMap(lastMap); 
		s.setDelayLength(0);
		s.setRunWithoutTimer(true);
		s.setNumIterations(NUM_ITERATION_PER_SIM);
		e.loadSimulationSettings(s);
	}

	@Override
	public void onFinishOneRun() {
		//Handle data
		if(bestMaps.size() < NUM_BESTMAPS) {
			bestMaps.put(lastMap, currentPopulationPoints);
		} else {
			for(SimulationMap m : bestMaps.keySet()) {
				Long l = bestMaps.get(m);
				if(l == null) {
					Log.v("L IS NULL");
				}
				if(currentPopulationPoints > l) {
					bestMaps.remove(m);
					bestMaps.put(lastMap, currentPopulationPoints);
					break;
				}
			}
		}
		if(worstMaps.size() < NUM_WORSTMAPS) {
			worstMaps.put(lastMap, currentPopulationPoints);
		} else {
			for(SimulationMap m : worstMaps.keySet()) {
				if(currentPopulationPoints < worstMaps.get(m)) {
					worstMaps.remove(m);
					worstMaps.put(lastMap, currentPopulationPoints);
					break;
				}
			}
		}
		currentPopulationPoints = 0;
		if(--rounds >= 0) {
			SimulationSettings s = SimulationSettings.DEFAULT;
			s.setNumIterations(NUM_ITERATION_PER_SIM);
			lastMap = SimulationMap.randomMap();
			s.setMap(lastMap);
			e.loadSimulationSettings(s);
			e.start();
		} else {
			onFinishScript();
		}
	}

	@Override
	public void onFinishScript() {
		Log.v("*** Script finished ***");
		Log.v("--- Best Maps ---");
		printObstacles(bestMaps.keySet());
		dumpMapsToFile("BestMaps", bestMaps.keySet());
		Log.v(" ");
		Log.v("--- Worst Maps ---");
		printObstacles(worstMaps.keySet());
		dumpMapsToFile("WorstMaps", worstMaps.keySet());
		listener.onFinishScript();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == EcoWorld.EVENT_TICK) {
			this.lastPop = (List<IPopulation>) evt.getNewValue();
			for(IPopulation p : lastPop) {
				if(p.getName().equals(populationToTrack)) {
					currentPopulationPoints = currentPopulationPoints + p.getAgents().size();
				}
			}
		}
		if(evt.getPropertyName() == EcoWorld.EVENT_FINISHED) {
			onFinishOneRun();
		}
	}
	
	@Override
	public String getName() {
		return name ;
	} 
	
	@Override
	public String toString() {
		return getName();
	}
	
	private void dumpMapsToFile(String fileHeader, Collection<SimulationMap> maps) {
		int counter = 1;
		for(SimulationMap m : maps) {
			m.setName(fileHeader + "_" + counter++);
			MapFileHandler.saveSimulationMap(m);
		}
	}
	
	private void printObstacles(Collection<SimulationMap> maps) {
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
