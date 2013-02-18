package chalmers.dax021308.daisy;

import java.awt.Color;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.ui.ApplicationFrame;

/**
 * Daisy application graph frame.
 * 
 * @author Erik
 *
 */
public class DaisyLineFrame extends ApplicationFrame {
	private static final long serialVersionUID = -2796492945005823682L;
	

	
	public DaisyLineFrame(String title, List<Integer> blackFlowerList, List<Integer> whiteFlowerList, List<Double> temperatureList) {
		super(title);
		
        final XYDataset dataset = setDataset(blackFlowerList, whiteFlowerList, temperatureList);
        final JFreeChart chart = createChart(dataset);
        final ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(1300, 700));
        setContentPane(chartPanel);
	}
	
	public XYDataset setDataset(List<Integer> blackFlowerList, List<Integer> whiteFlowerList, List<Double> temperatureList) {

        final XYSeries blackSerie = new XYSeries("Black");
        int counter = 1;
        for(Integer i : blackFlowerList) {
        	blackSerie.add(counter++, i);
        }
        
        counter = 1;
        final XYSeries whiteSerie = new XYSeries("White");
        for(Integer i : whiteFlowerList) {
        	whiteSerie.add(counter++, i);
        }


        counter = 1;
        final XYSeries temperatureSerie = new XYSeries("Temperature * Capacity");
        for(Double i : temperatureList) {
        	temperatureSerie.add(counter++, i);
        }

        counter = 1;
        final XYSeries totalPopSerie = new XYSeries("Total capacity");
        for(int i=0; i< blackFlowerList.size(); i++) {
        	totalPopSerie.add(counter++, blackFlowerList.get(i)+whiteFlowerList.get(i));
        }
        
        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(blackSerie);
        dataset.addSeries(whiteSerie);
        dataset.addSeries(temperatureSerie);
        dataset.addSeries(totalPopSerie);
        
        
                
        return dataset;
	}
	
    /**
     * Creates a chart.
     * 
     * @param dataset  the data for the chart.
     * 
     * @return a chart.
     */
    private JFreeChart createChart(final XYDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createXYLineChart(
            "Daisy Black/White simulation",      // chart title
            "Generation",                      // x axis label
            "Population size",                      // y axis label
            dataset,                  // data
            PlotOrientation.VERTICAL,
            true,                     // include legend
            true,                     // tooltips
            false                     // urls
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
        chart.setBackgroundPaint(Color.white);
        chart.setAntiAlias(true);
        chart.setTextAntiAlias(true);
        

//        final StandardLegend legend = (StandardLegend) chart.getLegend();
  //      legend.setDisplaySeriesShapes(true);
        
        // get a reference to the plot for further customisation...
        /*final XYPlot plot = chart.getXYPlot();
        plot.setBackgroundPaint(Color.lightGray);
    //    plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0));
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        
        final XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer(true, true);
        renderer.setSeriesLinesVisible(0, false);
        renderer.setSeriesShapesVisible(1, false);
        plot.setRenderer(renderer);*/

        // change the auto tick unit selection to integer units only...
        //final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        //rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        // OPTIONAL CUSTOMISATION COMPLETED.
                
        return chart;
        
    }

    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
   
}
