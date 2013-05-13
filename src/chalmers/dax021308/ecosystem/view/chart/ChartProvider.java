/**
 * @author Loanne Berggren
 */
package chalmers.dax021308.ecosystem.view.chart;

import chalmers.dax021308.ecosystem.model.environment.IModel;

/**
 * @author Loanne Berggren
 *
 */
public class ChartProvider {

	public static enum ChartType {
		LIFE_LENGTH_GRAPH,
		POPULATION_AMOUNT_GRAPH,
		ITERATION_TIME_GRAPH,
		GROUPING_PROPORTION_GRAPH;
	}

	private static LifeLengthGraph llg;
	private static PopulationAmountGraph pag;
	private static IterationTimeGraph itg;
	private static GroupingProportionGraph gpg;

	public static IChart getChart(ChartType chart, IModel model){
		int updatefrequency = 100;
		switch(chart) {
			case LIFE_LENGTH_GRAPH:
				if (llg == null)
					llg = new LifeLengthGraph(model, updatefrequency, "Life length", "Iterations", "Population life length mean");
				return llg;
			case POPULATION_AMOUNT_GRAPH:
				if (pag == null)
					pag =  new PopulationAmountGraph(model, updatefrequency, "Population amount", "Iterations", "Population amount");
				return pag;
			case ITERATION_TIME_GRAPH:
				if (itg == null)
					itg =  new IterationTimeGraph(model, updatefrequency, "Iteration time", "Iterations", "Iteration time (ms)");
				return itg;
			case GROUPING_PROPORTION_GRAPH:
				if (gpg == null)
					gpg = new GroupingProportionGraph(model, updatefrequency,"Grouping","Iterations", "Grouping proportion");
				return gpg;
			default:
				return null;
		}
	}

	public static void initCharts(IModel model){
		for (ChartType ct : ChartType.values()) {
			getChart(ct, model);
		}
	}
}
