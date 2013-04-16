package chalmers.dax021308.ecosystem.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.view.LiveSettingsView;
import chalmers.dax021308.ecosystem.view.NEWSettingsMenuView;

public class LiveSettingsViewController implements IController {
	private EcoWorld model;
	public final LiveSettingsView view;
	private ActionListener listenerUpdateButton;
	private SimulationSettings simSettings;
	//TODO: den här borde typ ta in aktuella SimulationsSettings på nåt sätt, så att den bara kan uppdatera det som ändrats
	
	public LiveSettingsViewController(EcoWorld model) {
		this.model = model;
		view = new LiveSettingsView(model);
		view.setVisible(true);
	}
	
	public void setSimulationSettingsObject(SimulationSettings s) { //se till att det här är samma simsettingsobjekt som är aktivt	
		simSettings = s;
		
		if(model == null) {
			throw new NullPointerException("Model not set.");
		}
		if(view == null) {
			//view = new LiveSettingsView(model);
			listenerUpdateButton = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					updateSimulation(simSettings);
				}
			};
			view.buttonUpdate.addActionListener(listenerUpdateButton);
			/*
			SimulationSettings simSettings = SimulationSettings.loadFromFile();
			if (simSettings == null) {
				Log.v("Failed to load saved settings. Loading default.");
				simSettings = SimulationSettings.DEFAULT;
			}
			setSettings(simSettings);
			*/
		}
		view.setVisible(true);
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	private void updateSimulation(SimulationSettings s) { //TODO: det här måste ses över, har bara trollat ihop nåt
		SimulationSettings simSettings = getSettings(s);
		simSettings.saveToFile();
		model.loadSimulationSettings(simSettings);
	}
	
	private SimulationSettings getSettings(SimulationSettings s) {
		String settingsName;
		int tickDelay;
		
		settingsName = "Live Custom";
		tickDelay = (Integer) view.spinnerDelayLength.getValue();
		
		s.updateLiveSettings(tickDelay);
		
		return s;
	}
	
	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setModel(IModel m) {
		if(m instanceof EcoWorld) {
			this.model = (EcoWorld) m;
		}			
	}

}
