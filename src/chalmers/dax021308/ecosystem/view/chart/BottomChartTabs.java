package chalmers.dax021308.ecosystem.view.chart;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JTabbedPane;

import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.view.IView;

public class BottomChartTabs extends JTabbedPane implements IView{
	private static final long serialVersionUID = 1L;
	private IChart graphPopAmount;
	private IChart graphLifeLength;
	private IChart graphIterationTime;
	
	public BottomChartTabs(IModel model) {
		graphPopAmount = ChartProvider.getChart(ChartProvider.ChartType.POPULATION_AMOUNT_GRAPH, model);
		graphLifeLength = ChartProvider.getChart(ChartProvider.ChartType.LIFE_LENGTH_GRAPH, model);
		graphIterationTime = ChartProvider.getChart(ChartProvider.ChartType.ITERATION_TIME_GRAPH, model);
		addTab(graphPopAmount.getTitle(), graphPopAmount.toComponent());
		addTab(graphLifeLength.getTitle(), graphLifeLength.toComponent());
		addTab(graphIterationTime.getTitle(), graphIterationTime.toComponent());
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
	}
	@Override
	public void init() {
	}

	@Override
	public void addController(ActionListener controller) {
	}


	@Override
	public void release() {
		graphPopAmount.release();
		graphIterationTime.release();
		graphLifeLength.release();
	}

}
