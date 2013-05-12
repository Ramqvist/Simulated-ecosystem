package chalmers.dax021308.ecosystem.view.chart;

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
	private double lastIteration;
	private double lastlastIteration;


	private ITrace2D iterationTimeTrace;

	public IterationTimeGraph(IModel model, int updateFrequency, String title, String xTitle, String yTitle) {
		super(model, updateFrequency, title, xTitle, yTitle);

		iterationTimeTrace = new Trace2DSorted();
		iterationTimeTrace.setName(title);
		iterationTimeTrace.setColor(Color.BLACK);
		addTrace(iterationTimeTrace);
		init();
	}

	@Override
	protected void resetChart(){
		super.resetChart();
		this.iterationTimeTrace.removeAllPoints();
		this.lastIteration = 0;
		this.lastlastIteration = 0;
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
	protected void onStart(Object object){}

	@Override
	protected void onStop(Object object){}

	@Override
	protected void onIterationFinished(Object object){
		if (getIterationsPassed() % updateFrequency != 0)
			return;
		updateGraph(object);
	}

	private void updateGraph(Object object){
		double iterationTime = ((Double) object).doubleValue();
		if(lastIteration == 0) {
			lastIteration = iterationTime;
			this.iterationTimeTrace.addPoint(getIterationsPassed(), iterationTime);
		} else if(lastlastIteration == 0){
			double time = (lastIteration + iterationTime) / 2;
			lastlastIteration = lastIteration;
			lastIteration = time;
			this.iterationTimeTrace.addPoint(getIterationsPassed(), time);
		} else {
			double time = (lastIteration + iterationTime + lastlastIteration) / 3;
			lastIteration = time;
			lastlastIteration = lastIteration;
			this.iterationTimeTrace.addPoint(getIterationsPassed(), time);
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

