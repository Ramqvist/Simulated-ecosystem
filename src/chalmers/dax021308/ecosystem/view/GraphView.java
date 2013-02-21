package chalmers.dax021308.ecosystem.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chalmers.dax021308.ecosystem.model.EcoWorld;
import chalmers.dax021308.ecosystem.model.IObstacle;
import chalmers.dax021308.ecosystem.model.IPopulation;

import info.monitorenter.gui.chart.Chart2D;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.views.ChartPanel;

public class GraphView implements IView{

	private JFrame frame;
	private Chart2D chart = new Chart2D();
	// Create an ITrace: 
    // Note that dynamic charts need limited amount of values!!! 
	final ITrace2D trace1 = new Trace2DLtd(200); 
	final ITrace2D trace2 = new Trace2DLtd(200);
	
	private double m_y = 0;
    private long m_starttime = System.currentTimeMillis();
    int tempY = 0;
	/**
	 * Create the panel.
	 */
	public GraphView(EcoWorld model) {
		frame = new JFrame("Graph View");
		trace1.setColor(Color.RED);
		trace1.setColor(Color.BLUE);
	    // Add the trace to the chart. This has to be done before adding points (deadlock prevention): 
	    chart.addTrace(trace1);
	    chart.addTrace(trace2);
	    
	    model.addObserver(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		// TODO Auto-generated method stub
		String eventName = event.getPropertyName();
		if(eventName == EcoWorld.EVENT_STOP) {
			//Model has stopped. Maybe hide view?
			//frame.setVisible(false);
		} else if(eventName == EcoWorld.EVENT_TICK) {
			// This is just computation of some nice looking value.
	        /*double rand = Math.random();
	        boolean add = (rand >= 0.5) ? true : false;
	        this.m_y = (add) ? this.m_y + Math.random() : this.m_y - Math.random();
	        // This is the important thing: Point is added from separate Thread.
	        trace.addPoint(((double) System.currentTimeMillis() - this.m_starttime), this.m_y);*/
	        
			//Tick notification recived from model. Do something with the data.
			if(event.getNewValue() instanceof List<?>) {
				tempY++;
				List<IPopulation> newPops = (List<IPopulation>) event.getNewValue();
				if (newPops.size() >= 1) {
					int numOfAgents = newPops.get(0).getAgents().size();
				
					trace1.addPoint(((double) System.currentTimeMillis() - this.m_starttime), 5);
				}
				else {
					trace1.addPoint(((double) System.currentTimeMillis() - this.m_starttime), tempY);
					trace2.addPoint(((double) System.currentTimeMillis() - this.m_starttime), tempY+10);
				}
			}
			/*if(event.getOldValue() instanceof List<?>) {
				this.newObs = (List<IObstacle>) event.getOldValue();
			}*/
			/*removeAll();
			repaint();
			revalidate();*/
		}
		
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	    this.frame.setSize(500, 500);
	    this.frame.getContentPane().add(chart);
	    this.frame.setVisible(true); 
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
	
	

}

  