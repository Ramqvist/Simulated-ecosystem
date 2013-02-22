package chalmers.dax021308.ecosystem.controller;

<<<<<<< HEAD
import chalmers.dax021308.ecosystem.model.EcoWorld;
import chalmers.dax021308.ecosystem.model.Obstacle;
import chalmers.dax021308.ecosystem.view.GraphView;
=======
import java.awt.Dimension;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
>>>>>>> 9e1ebc08f39a9b984b6e1e4b66d6a99d33fd608d
import chalmers.dax021308.ecosystem.view.SimulationView;

/**
 * Controller class
 * 
 * @author Henrik Ernstsson
 */
public class ToyController implements IController {

	private EcoWorld model;
	private SimulationView simView;
	private GraphView graphView;

	public ToyController() {
		init();
	}

	@Override
	public void init() {
		Dimension d = new Dimension(1000, 750);
		
		this.model = new EcoWorld(d, 20, Integer.MAX_VALUE);
		//Uncommend below to run without delay.
		//this.model = new EcoWorld(d);
		
		//Uncomment to start model.
		model.start();
		this.simView = new SimulationView(model, d);
		simView.init();
		
		this.graphView = new GraphView(model);
		graphView.init();
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
	}

}
