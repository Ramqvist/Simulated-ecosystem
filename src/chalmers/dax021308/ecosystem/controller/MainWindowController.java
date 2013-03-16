package chalmers.dax021308.ecosystem.controller;


import chalmers.dax021308.ecosystem.view.AWTSimulationView;
import chalmers.dax021308.ecosystem.view.HeatMapView;
import chalmers.dax021308.ecosystem.view.MainWindow;
import chalmers.dax021308.ecosystem.view.OpenGLSimulationView;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.util.Log;

/**
 * Controller class
 * 
 * @author Henrik Ernstsson
 */
public class MainWindowController implements IController {

	private EcoWorld model;
	private MainWindow window;
	private NewSimulationController newSim;

	private ActionListener showNewSimWindowBtnListener = new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			try {
				Log.v("Showing new simview. ");
				showNewSimulationWindow();
			} catch (IllegalStateException ex) {
				Log.v("EcoWorld already stopped");
			}
		}
	};
	public MainWindowController() {
		init();
	}

	@Override
	public void init() {
		int tickDelay = 16;
		int numIterations = Integer.MAX_VALUE;
		boolean recordSimulation = false;
		
		Dimension d = new Dimension(1300, 1300);
		d.height = d.height - 40;
		Dimension f = new Dimension(d.width-16, d.height-39);
		this.model = new EcoWorld(f, tickDelay, numIterations, recordSimulation);
		this.window = new MainWindow(model);
		window.setVisible(true);
		window.setBtnStartNewSimWindowActionListener(showNewSimWindowBtnListener);
		showNewSimulationWindow();
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
	}

	@Override
	public void setModel(IModel m) {
		// TODO Auto-generated method stub
	}
	
	public void showNewSimulationWindow() {
		if(newSim == null) {
			newSim = new NewSimulationController();
			newSim.setModel(model);
			newSim.init();
		} else {
			newSim.init();
		}
	}
	
	

}
