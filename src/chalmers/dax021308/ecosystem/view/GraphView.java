package chalmers.dax021308.ecosystem.view;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.population.IPopulation;



import info.monitorenter.gui.chart.Chart2D;

import info.monitorenter.gui.chart.ITrace2D;
import info.monitorenter.gui.chart.traces.Trace2DLtd;
import info.monitorenter.gui.chart.IAxis;
import info.monitorenter.gui.chart.views.ChartPanel;

public class GraphView implements IView{

	private JFrame frame;
	private Chart2D chart = new Chart2D();
	// Create an ITrace: 
	private List<ITrace2D> traces = new ArrayList<ITrace2D>();
	
	private double m_y = 0;
    private long m_starttime = System.currentTimeMillis();
	/**
	 * Create the panel.
	 */
	public GraphView(EcoWorld model) {
		frame = new JFrame("Graph View");
	    
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
	        
			//Tick notification recived from model. Update graph.
			if(event.getNewValue() instanceof List<?>) {
				
				List<IPopulation> newPops = (List<IPopulation>) event.getNewValue();
				
				// init traces. hm.
				if (traces.size() == 0) {
					for (IPopulation p: newPops) {
						ITrace2D newTrace = new Trace2DLtd(200);
						newTrace.setName(p.getAgents().get(0).getName());
						newTrace.setColor(Color.RED);
						traces.add(newTrace);
						chart.addTrace(newTrace);	
					}
				}
				
				// update graph
				for (int i = 0; i < newPops.size(); ++i) {
					int numOfAgents = newPops.get(i).getAgents().size();
					traces.get(i).addPoint(((double) System.currentTimeMillis() - this.m_starttime), numOfAgents);
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

  