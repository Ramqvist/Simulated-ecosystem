package chalmers.dax021308.ecosystem.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.view.LiveSettingsView;

public class LiveSettingsViewController implements IController {
	private EcoWorld model;
	public final LiveSettingsView view;
	private ActionListener listenerUpdateButton;
	private SimulationSettings simSettings;
	//TODO: den här borde typ ta in aktuella SimulationsSettings på nï¿½t sï¿½tt, sï¿½ att den bara kan uppdatera det som ï¿½ndrats
	
	public LiveSettingsViewController(final EcoWorld model) {
		this.model = model;
		view = new LiveSettingsView(model);
		view.setVisible(true);

		view.spinnerDelayLength.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int newDelay = (Integer) view.spinnerDelayLength.getValue();
				if(newDelay > 0) {
					model.setDelayLength(newDelay);
					if(newDelay == 0) {
						model.setRunWithoutTimer(true);
					} else {
						model.setRunWithoutTimer(false);
					}
				}
			}
			
		});
	}
	
	public void setSimulationSettingsObject(SimulationSettings s) { //se till att det hï¿½r ï¿½r samma simsettingsobjekt som ï¿½r aktivt	
		simSettings = s;
		
		if(model == null) {
			throw new NullPointerException("Model not set.");
		}
		if(view == null) {
			//view = new LiveSettingsView(model);
			listenerUpdateButton = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int newDelay = (Integer) view.spinnerDelayLength.getValue();
					if(newDelay > 0) {
						model.setDelayLength(newDelay);
						if(newDelay == 0) {
							model.setRunWithoutTimer(true);
						} else {
							model.setRunWithoutTimer(false);
						}
					}
				}
			};
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

	private void updateSimulation(SimulationSettings s) { //TODO: det hï¿½r mï¿½ste ses ï¿½ver, har bara trollat ihop nï¿½t
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
		
	}

	@Override
	public void setModel(IModel m) {
		if(m instanceof EcoWorld) {
			this.model = (EcoWorld) m;
		}			
	}
	

}
