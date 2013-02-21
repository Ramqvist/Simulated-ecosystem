package chalmers.dax021308.ecosystem.controller;

import java.awt.Dimension;

import chalmers.dax021308.ecosystem.model.EcoWorld;
import chalmers.dax021308.ecosystem.model.Obstacle;
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

	public ToyController() {
		init();
	}

	@Override
	public void init() {
		Dimension d = new Dimension(1000, 750);
//		int periodTime = 20;
		this.model = new EcoWorld(100, Integer.MAX_VALUE);
//		eco.start();
//		this.model = new EcoWorld();
		
		//Uncomment to start model.
		model.start();
		this.simView = new SimulationView(model, d);
		simView.init();
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
	}

}
