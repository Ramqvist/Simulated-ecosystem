package chalmers.dax021308.ecosystem.view.chart;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JTabbedPane;

import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.view.IView;

public class BottomChartTabs extends JTabbedPane implements IView{
	private static final long serialVersionUID = 1L;
	
	public BottomChartTabs(IModel model) {
		IChart graphView1 = ChartProvider.getChart(ChartProvider.ChartType.POPULATION_AMOUNT_GRAPH, model);
		IChart graphView2 = ChartProvider.getChart(ChartProvider.ChartType.LIFE_LENGTH_GRAPH, model);
		IChart graphView3 = ChartProvider.getChart(ChartProvider.ChartType.ITERATION_TIME_GRAPH, model);
		addTab(graphView1.getTitle(), graphView1.toComponent());
		addTab(graphView2.getTitle(), graphView2.toComponent());
		addTab(graphView3.getTitle(), graphView3.toComponent());
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
	public void onTick() {
	}

	@Override
	public void release() {
	}

}
