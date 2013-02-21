package chalmers.dax021308.ecosystem.controller;

import chalmers.dax021308.ecosystem.model.EcoWorld;
import chalmers.dax021308.ecosystem.model.Obstacle;
import chalmers.dax021308.ecosystem.view.GraphView;
import chalmers.dax021308.ecosystem.view.SimulationView;
import chalmers.dax021308.ecosystem.view.ToyView;

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
//		int periodTime = 20;
//		EcoWorld eco = new EcoWorld(20, Integer.MAX_VALUE);
//		eco.start();
		this.model = new EcoWorld();
		
		//Uncomment to start model.
		model.start();
		this.simView = new SimulationView(model);
		simView.init();
		
		this.graphView = new GraphView(model);
		graphView.init();
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
	}

}
