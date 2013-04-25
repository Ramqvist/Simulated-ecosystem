/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.view.chart;

import chalmers.dax021308.ecosystem.model.environment.IModel;

/**
 * @author Loanne Berggren
 *
 */
public class ChartFactory {

	public static enum ChartType {
		LIFE_LENGTH_GRAPH, 
		POPULATION_AMOUNT_GRAPH,
		ITERATION_TIME_GRAPH,
		GROUPING_PROPORTION_GRAPH;
	}
	
	public static IChart makeChart(ChartType chart, IModel model){
		int updatefrequency = 10;
		switch(chart) {
		case LIFE_LENGTH_GRAPH:
			return new LifeLengthGraph(model, updatefrequency);
		case POPULATION_AMOUNT_GRAPH:
			return new PopulationAmountGraph(model, updatefrequency);
		case ITERATION_TIME_GRAPH:
			return new IterationTimeGraph(model, updatefrequency);
		case GROUPING_PROPORTION_GRAPH:
			return new GroupingProportionGraph(model, updatefrequency);
		default: // Although shouldn't be necessary to have this.
			return null;
		}
	}
	
}
