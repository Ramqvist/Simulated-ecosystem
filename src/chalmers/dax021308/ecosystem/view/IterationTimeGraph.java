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
 * Shows iteration time.
 *
 * @author Loanne
 * 
 */
public class IterationTimeGraph extends AbstractGraph2D {

	private ITrace2D iterationTimeTrace;

	public IterationTimeGraph(IModel model, int updateFrequency) {
		super(model, updateFrequency, "Iterations", "Iteration time (ms)");
		
		iterationTimeTrace = new Trace2DSorted();
		iterationTimeTrace.setName("Iteration time");
		iterationTimeTrace.setColor(Color.BLACK);
		addTrace(iterationTimeTrace);
		init();
	}

	@Override
	public void init() { 
		this.rangeX = new Range(0, 1000);
		this.rangeY = new Range(0, 15);
		super.init();
		xAxis.setMinorTickSpacing(2000);
		yAxis.setMinorTickSpacing(1);		
	}
	

	@Override
	protected void onStart(Object object){	
		this.iterationTimeTrace.removeAllPoints();
		this.nIterationsPassed = 0;
	}

	@Override
	protected void onStop(Object object){
		this.iterationTimeTrace.removeAllPoints();
		this.nIterationsPassed = 0;
	}
	@Override
	protected void onIterationFinished(Object object){
		nIterationsPassed++;
		if (nIterationsPassed % updateFrequency != 0)
			return;
		updateGraph(object);
	}
	
	private void updateGraph(Object object){	
		Long iterationTime = (Long) (object);
		iterationTime = (long) (0.000001 * iterationTime); // ms	
		this.iterationTimeTrace.addPoint(nIterationsPassed, iterationTime);
	}

	@Override
	protected void onTick(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onPause(Object object) {
		// TODO Auto-generated method stub
		
	}
}

