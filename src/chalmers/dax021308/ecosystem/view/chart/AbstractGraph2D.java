package chalmers.dax021308.ecosystem.view.chart;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.view.IView;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.IAxisScalePolicy;
import info.monitorenter.gui.chart.IAxis.AxisTitle;
import info.monitorenter.gui.chart.rangepolicies.RangePolicyMinimumViewport;
import info.monitorenter.util.Range;

/**
 * 
 * 
 * Abstract class for graphs.
 *
 * @author Loanne Berggren
 * 
 */
public abstract class AbstractGraph2D extends Chart2D implements IChart{
	// Values for axis. More values are set in init()
	protected IAxis<IAxisScalePolicy> xAxis;
	protected IAxis<IAxisScalePolicy> yAxis;
	protected Range rangeX;
	protected Range rangeY;
	protected String xAxisTitle;
	protected String yAxisTitle;
	protected int nIterationsPassed = 0;
	protected int updateFrequency = 10; // every tenth iteration.
	
	public AbstractGraph2D (IModel model, int updateFrequency, String xTitle, String yTitle){
		model.addObserver(this);
		this.updateFrequency = updateFrequency;
		this.xAxisTitle = xTitle;
		this.yAxisTitle = yTitle;
		rangeX = new Range(0, 1000);
		rangeY = new Range(0, 15);
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
		String eventName = event.getPropertyName();
		if (eventName == EcoWorld.EVENT_START) {
			onStart(event.getNewValue());
		} else if (eventName == EcoWorld.EVENT_PAUSE){
			onPause(event.getNewValue());
		} else if(eventName == EcoWorld.EVENT_STOP) {
			onStop(event.getNewValue());
		} else if(eventName == EcoWorld.EVENT_TICK){
			onTick(event.getNewValue());
		} else if(eventName == EcoWorld.EVENT_ITERATION_FINISHED) {
			onIterationFinished(event.getNewValue());
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
	
	protected abstract void onStart(Object object);
	protected abstract void onPause(Object object);
	protected abstract void onStop(Object object);
	protected abstract void onTick(Object object);

	protected void onIterationFinished(Object object){
		nIterationsPassed++;
	}

	
	

}
