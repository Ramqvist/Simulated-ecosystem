package chalmers.dax021308.ecosystem.view;

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

//import info.monitorenter.gui.util.ColorIterator;
import info.monitorenter.util.Range;

/**
 * 
 * 
 * 
 * Shows population amount over iterations.
 * However, might not yet work for populations added after
 * simulation start. Will fix later if this should be possible.
 * 
 * @author Loanne
 * 
 */
public class GraphPopulationAmountView extends Chart2D implements IView {

	// Values for axis. More values are set in init()
	private IAxis<IAxisScalePolicy> xAxis;
	private IAxis<IAxisScalePolicy> yAxis;
	private Range rangeX = new Range(0, 1000);
	private Range rangeY = new Range(0, 500);
	private String xAxisTitle = "Iterations";
	private String yAxisTitle = "Population amount";
	private int nIterationsPassed = 0;

	public GraphPopulationAmountView(IModel model) {
		model.addObserver(this);
		init();
	}

	@Override
	public void init() {    
		xAxis = (IAxis<IAxisScalePolicy>)getAxisX(); 
		xAxis.setAxisTitle(new AxisTitle(xAxisTitle));
		xAxis.setRangePolicy(new RangePolicyMinimumViewport(rangeX)); 
		xAxis.setMinorTickSpacing(2000);

		yAxis = (IAxis<IAxisScalePolicy>)getAxisY();
		yAxis.setAxisTitle(new AxisTitle(yAxisTitle));
		yAxis.setRangePolicy(new RangePolicyMinimumViewport(rangeY)); 
		yAxis.setMinorTickSpacing(100);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		nIterationsPassed++;
		String eventName = event.getPropertyName();
		List<IPopulation> populations = null;
		if(event.getNewValue() instanceof List<?>) {
			populations = (List<IPopulation>) event.getNewValue();
			populations = AbstractPopulation.clonePopulationList(populations);
		}
		//Du kan ha "==" d� str�ngarna som tas in �r public static final. "==" �r snabbare �n equals() //Erik
		if (eventName == EcoWorld.EVENT_START) {
		}
		else if(eventName == EcoWorld.EVENT_STOP) {
			this.removeAllTraces().clear();
			this.nIterationsPassed = 0;
		} else if(eventName == EcoWorld.EVENT_TICK) {

			if(populations != null) {		
				if (this.getTraces().size() == 0) {
					// initialize traces
					initializeTraces(populations);
				}
				
				// update graph.
				Iterator<ITrace2D> it = this.getTraces().iterator();
				for (IPopulation p: populations) {
					if (it.hasNext()) {
						((ITrace2D) it.next()).addPoint(nIterationsPassed, p.getSize());
					}
				}
			}
		}
	}

	/*
	 * 
	 */
	private void initializeTraces(List<IPopulation> populations){
		for (IPopulation p: populations) {		
			String name = p.getName();	
			if (name != null) {
				ITrace2D newTrace = new Trace2DSimple(name); 
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
		// TODO Auto-generated method stub	
	}
}

