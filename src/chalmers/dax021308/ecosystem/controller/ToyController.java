package chalmers.dax021308.ecosystem.controller;

import chalmers.dax021308.ecosystem.model.EcoWorld;
import chalmers.dax021308.ecosystem.model.Obstacle;
import chalmers.dax021308.ecosystem.view.ToyView;

/**
 * Controller class
 * 
 * @author Henrik Ernstsson
 */
public class ToyController implements IController {

	private EcoWorld model;
	private ToyView toyView;

	public ToyController() {
		init();
	}

	@Override
	public void init() {
//		int periodTime = 20;
//		EcoWorld eco = new EcoWorld(20, Integer.MAX_VALUE);
//		eco.start();
		Obstacle o = new Obstacle("Obstacle.txt");
		this.model = new EcoWorld();
		
		//Uncomment to start model.
		//model.start();
		this.toyView = new ToyView(model);
		toyView.init();
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
	}

}
