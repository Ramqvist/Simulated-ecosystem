package chalmers.dax021308.ecosystem.controller;

import chalmers.dax021308.ecosystem.model.EcoWorld;
import chalmers.dax021308.ecosystem.view.SimulationView;

/**
 * Controller class
 * 
 * @author Henrik Ernstsson
 */
public class ToyController implements IController {

	private EcoWorld model;
	private SimulationView simView;

	public ToyController() {
		init();
	}

	@Override
	public void init() {
//		int periodTime = 20;
		this.model = new EcoWorld(100, Integer.MAX_VALUE);
//		eco.start();
//		this.model = new EcoWorld();
		
		//Uncomment to start model.
		model.start();
		this.simView = new SimulationView(model);
		simView.init();
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
	}

}
