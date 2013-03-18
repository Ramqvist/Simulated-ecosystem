package chalmers.dax021308.ecosystem.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.population.AbstractPopulation;
import chalmers.dax021308.ecosystem.model.population.IPopulation;

import info.monitorenter.gui.chart.*;
import info.monitorenter.gui.chart.IAxis.*;
import info.monitorenter.gui.chart.rangepolicies.*;
import info.monitorenter.gui.chart.traces.Trace2DSimple;
import info.monitorenter.gui.chart.traces.Trace2DSorted;

//import info.monitorenter.gui.util.ColorIterator;
import info.monitorenter.util.Range;

/**
 * 
 * 
 * 
 * Shows population amount over iterations.
 * 
 * @author Loanne Berggren
 * 
 */
public class GraphPopulationAmountView extends AbstractGraph2D {

	public GraphPopulationAmountView(IModel model, int updateFrequency) {
		super(model, updateFrequency, "Iterations", "Population amount");
		init();
	}

	@Override
	public void init() {    
		this.rangeX = new Range(0, 1000);
		this.rangeY = new Range(0, 500);
		super.init();
		xAxis.setMinorTickSpacing(2000);
		yAxis.setMinorTickSpacing(100);
	}

	/*
	 * 
	 */
	private void initializeTraces(List<IPopulation> populations){
		ITrace2D newTrace;
		for (IPopulation p: populations) {		
			String name = p.getName();	
			if (name != null) {
				newTrace = new Trace2DSorted();
				newTrace.setName(name);
				newTrace.setColor(p.getColor());
				this.addTrace(newTrace);
			}
		}
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
		this.removeAllTraces().clear();
		this.removeAll();
	}
	
	@Override
	protected void onStop(Object object){
		this.removeAllTraces().clear();
		this.nIterationsPassed = 0;
	}
	@Override
	public void onTick(Object object) {
		if (nIterationsPassed % updateFrequency != 0)
			return;
		List<IPopulation> populations = null;
		
		if(!(object instanceof List<?>)) {
			return;
		} 
		
		populations = (List<IPopulation>) object;
		if(populations != null) {		
			if (this.getTraces().size() == 0) {
				// initialize traces
				initializeTraces(populations);
			}
			updateGraph(populations);
		}
		
	}
	/*
	 * update graph
	 */
	private void updateGraph(List<IPopulation> populations){
		Iterator<ITrace2D> it = this.getTraces().iterator();
		for (IPopulation p: populations) {
			if (it.hasNext()) {
				((ITrace2D) it.next()).addPoint(nIterationsPassed, p.getSize());
			}
			else
				return;
		}
	}

	@Override
	protected void onStart(Object object) {
		// Nothing special
		
	}

	@Override
	protected void onPause(Object object) {
		// Nothing
	}
}

