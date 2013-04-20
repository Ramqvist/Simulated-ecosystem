package chalmers.dax021308.ecosystem.view.chart;

import java.awt.event.ActionListener;
import java.util.Iterator;
import java.util.List;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.population.AbstractPopulation;
import chalmers.dax021308.ecosystem.model.population.DeerPopulation;
import chalmers.dax021308.ecosystem.model.population.IPopulation;

import info.monitorenter.gui.chart.*;
import info.monitorenter.gui.chart.traces.Trace2DSorted;
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
public class StottingDeerProportionGraph extends AbstractGraph2D {

	public StottingDeerProportionGraph(IModel model, int updateFrequency) {
		super(model, updateFrequency, "Iterations", "Interesting property proportion");
		init();
	}

	@Override
	public void init() {    
		this.rangeX = new Range(0, 1000);
		this.rangeY = new Range(0, 1.1);
		super.init();
		xAxis.setMinorTickSpacing(2000);
		yAxis.setMinorTickSpacing(1);
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
	protected void onStart(Object object) {
		// Nothing
		
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
	 * update points for each population.
	 */
	private void updateGraph(List<IPopulation> populations){
		Iterator<ITrace2D> it = this.getTraces().iterator();
		AbstractPopulation ap = null;
		for (IPopulation p: populations) {
			ap = (AbstractPopulation) p;
			if (it.hasNext()) {
				((ITrace2D) it.next()).addPoint(nIterationsPassed, ap.getInterestingPropertyProportion());
			} else {
				return;
			}
		}
	}



	@Override
	protected void onPause(Object object) {
		// Nothing
	}
}

