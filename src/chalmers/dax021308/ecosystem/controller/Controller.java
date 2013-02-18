package chalmers.dax021308.ecosystem.controller;

import chalmers.dax021308.ecosystem.model.EcoWorld;


/**
 * Controller class
 * 
 * @author Henrik Ernstsson
 */
public class Controller implements IController {
	
	private EcoWorld world;
	public Controller(){
		world = new EcoWorld();
	}
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

}
