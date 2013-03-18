package chalmers.dax021308.ecosystem.view;

import java.awt.Color;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import info.monitorenter.gui.chart.*;
import info.monitorenter.gui.chart.traces.Trace2DSorted;
import info.monitorenter.util.Range;

/**
 * 
 * 
 * Shows iteration time.
 *
 * @author Loanne Berggren
 * 
 */
public class IterationTimeGraph extends AbstractGraph2D {
	private static final long serialVersionUID = 1499857926631611643L;
	private long lastIteration;
	private long lastlastIteration;
	
	
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
		if(lastlastIteration == 0) {
			iterationTime = (long) (0.000001 * iterationTime); // ms	
			lastIteration = iterationTime;
			this.iterationTimeTrace.addPoint(nIterationsPassed, iterationTime);
		} else if(lastlastIteration == 0){
			iterationTime = (long) (0.000001 * iterationTime); // ms	
			long time = (lastIteration + iterationTime) / 2;
			lastlastIteration = lastIteration;
			lastIteration = time;
			this.iterationTimeTrace.addPoint(nIterationsPassed, time);
		} else {
			iterationTime = (long) (0.000001 * iterationTime); // ms	
			long time = (lastIteration + iterationTime + lastlastIteration) / 3;
			lastIteration = time;
			lastlastIteration = lastIteration;
			this.iterationTimeTrace.addPoint(nIterationsPassed, time);
		}
	}

	@Override
	protected void onTick(Object object) {
		// Nothing
		
	}

	@Override
	protected void onPause(Object object) {
		// Nothing
		
	}
}

