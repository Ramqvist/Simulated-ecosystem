package chalmers.dax021308.ecosystem.view.chart;

import java.awt.Component;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;

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
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// Values for axis. More values are set in init()
	protected IAxis<IAxisScalePolicy> xAxis;
	protected IAxis<IAxisScalePolicy> yAxis;
	protected Range rangeX;
	protected Range rangeY;
	protected String xAxisTitle;
	protected String yAxisTitle;
	private int nIterationsPassed = 0;
	protected int updateFrequency = 10; // every tenth iteration.
	private boolean hasStopped = false;
	private IModel model;

	public AbstractGraph2D (IModel model, int updateFrequency, String title, String xTitle, String yTitle){
		this.model = model;
		model.addObserver(this);
		this.updateFrequency = updateFrequency;
		this.setName(title);
		this.xAxisTitle = xTitle;
		this.yAxisTitle = yTitle;
		rangeX = new Range(0, 1000);
		rangeY = new Range(0, 15);
	}

	protected void resetChart() {
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
			if (hasStopped) {
				resetChart();
				nIterationsPassed = 0;
				hasStopped = false;
			}
			onStart(event.getNewValue());
		} else if (eventName == EcoWorld.EVENT_PAUSE){
			onPause(event.getNewValue());
		} else if(eventName == EcoWorld.EVENT_STOP) {
			hasStopped = true;
			onStop(event.getNewValue());
		} else if(eventName == EcoWorld.EVENT_TICK){
			onTick(event.getNewValue());
		} else if(eventName == EcoWorld.EVENT_ITERATION_FINISHED) {
			nIterationsPassed++;
			onIterationFinished(event.getNewValue());
		}
	}

	protected int getIterationsPassed(){
		return this.nIterationsPassed;
	}

	@Override
	public void addController(ActionListener controller) {}

	@Override
	public void release() {
		hasStopped = true;
		model.removeObserver(this);
		this.removeAllTraces();
	}

	protected abstract void onStart(Object object);
	protected abstract void onPause(Object object);
	protected abstract void onStop(Object object);
	protected abstract void onTick(Object object);
	protected abstract void onIterationFinished(Object object);

	@Override
	public Component toComponent(){
		return this;
	}

	@Override
	public String getTitle(){
		return this.getName();
	}

	@Override
	public BufferedImage getSnapShot() {
		return this.snapShot();
	}

}
