package chalmers.dax021308.ecosystem.controller;

import chalmers.dax021308.ecosystem.model.EcoWorld;

/**
 * Controller class
 * 
 * @author Henrik Ernstsson
 */
public class Controller implements IController {

	private EcoWorld world;

	public Controller() {
		init();
	}

	@Override
	public void init() {
		world = new EcoWorld();
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub

	}

}
