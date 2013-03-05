package chalmers.dax021308.ecosystem.view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
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

	private Map<String,ITrace2D> traces = new HashMap<String,ITrace2D>();
	//private ColorIterator colors = new ColorIterator();
	
	// Values for axis. More values are set in init()
	private IAxis<IAxisScalePolicy> xAxis;
	private IAxis<IAxisScalePolicy> yAxis;
	private Range rangeX = new Range(0, 1000);
	private Range rangeY = new Range(0, 500);
	private String xAxisTitle = "Iterations";
	private String yAxisTitle = "Population amount";
	private int nIterationsPassed = 0;

	public GraphPopulationAmountView(EcoWorld model) {
		model.addObserver(this);
		init();
	}

	@Override
	public void init() {    
		//this.colors.setStartColor(Color.BLUE);
		// Set how many different colored traces possible.
		//this.colors.setSteps(10);

		xAxis = (IAxis<IAxisScalePolicy>)getAxisX(); 
		xAxis.setAxisTitle(new AxisTitle(xAxisTitle));
		xAxis.setRangePolicy(new RangePolicyMinimumViewport(rangeX)); 
		xAxis.setMinorTickSpacing(2000);

		yAxis = (IAxis<IAxisScalePolicy>)getAxisY();
		yAxis.setAxisTitle(new AxisTitle(yAxisTitle));
		yAxis.setRangePolicy(new RangePolicyMinimumViewport(rangeY)); 
		yAxis.setMinorTickSpacing(50);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		nIterationsPassed++;
		String eventName = event.getPropertyName();
		if(eventName == EcoWorld.EVENT_STOP) {
			//Model has stopped. Maybe hide view?
		} else if(eventName == EcoWorld.EVENT_TICK) {

			if(event.getNewValue() instanceof List<?>) {

				List<IPopulation> populations = (List<IPopulation>) event.getNewValue();

				if (traces.size() != populations.size()) {
					addTraces(populations);
				}

				// update graph.
				for (IPopulation p: populations) {
					traces.get(p.getName()).addPoint(nIterationsPassed, p.getSize());
				}
			}
		}
	}

	/*
	 * 
	 */
	private void addTraces(List<IPopulation> populations){
		for (IPopulation p: populations) {		
			String name = p.getName();	
			if (name != null && !traces.keySet().contains(name)) {
				ITrace2D newTrace = new Trace2DSimple(name); 
				newTrace.setColor(p.getColor());
				traces.put(name, newTrace);
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
	
	/* TODO I don't know if we want this
	public void setRangeX(double min, double max) {
		this.rangeX.setMin(min);
		this.rangeX.setMax(max);	
		xAxis.setRangePolicy(new RangePolicyMinimumViewport(rangeX));
	}

	public void setRangeY(double min, double max) {
		this.rangeY.setMin(min);
		this.rangeY.setMax(max);
		yAxis.setRangePolicy(new RangePolicyMinimumViewport(rangeY));
	}
	 */




}

