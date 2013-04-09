package chalmers.dax021308.ecosystem.controller;


import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.view.MainWindow;

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
		Dimension d = new Dimension(1300, 1300);
		d.height = d.height - 40;
		this.model = new EcoWorld();
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
