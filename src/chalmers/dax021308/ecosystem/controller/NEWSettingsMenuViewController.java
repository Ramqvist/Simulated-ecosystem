package chalmers.dax021308.ecosystem.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.view.NEWSettingsMenuView;

public class NEWSettingsMenuViewController implements IController {
	private EcoWorld model;
	private NEWSettingsMenuView view;
	private ActionListener listenerStartButton;
	private SimulationSettings s;
	
	public NEWSettingsMenuViewController(EcoWorld model) {
		this.model = model;
	}
	
	@Override
	public void init() {
		if(model == null) {
			throw new NullPointerException("Model not set.");
		}
		if(view == null) {
			view = new NEWSettingsMenuView(model);
			listenerStartButton = new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					startSimulation();
				}
			};
			view.buttonStart.addActionListener(listenerStartButton);
			SimulationSettings simSettings = SimulationSettings.loadFromFile();
			if (simSettings == null) {
				Log.v("Failed to load saved settings. Loading default.");
				simSettings = SimulationSettings.DEFAULT;
			}
			setGUISettings(simSettings);
		}
		view.setVisible(true);
	}

	private void startSimulation() {
		try {
			try {
				model.stop();
			} catch (IllegalStateException e) {
			}
			s = getGUISettings();
			s.saveToFile();
			model.loadSimulationSettings(s);
			// TODO: Uppdatera LiveSettingsgrejen?
			try {
				model.start();
			} catch (IllegalStateException e) {
				Log.v(e.toString());
			}
			view.setVisible(false);
		} catch (Exception e) {
			view.showErrorMessage();
			e.printStackTrace();
		}
	}
	
	public SimulationSettings getGUISettings() {
		String settingsName;
		String pred;
		int predPopSize;
		String prey;
		int preyPopSize;
		String veg;
		int vegPopSize;
		String shape;
		String obstacle;
		int noOfThreads;
		boolean isRunningWithoutTimer;
		boolean isRecording;	
		int tickDelay;
		int noOfIterations;
		int width;
		int height;
		
		settingsName = "Custom";
		pred = (String) view.listPred.getSelectedValue(); //TODO: göra snyggare?
		predPopSize = (Integer) view.spinnerPredPopSize.getValue(); //TODO: säker på att det är en int?
		prey = (String) view.listPrey.getSelectedValue();
		preyPopSize = (Integer) view.spinnerPreyPopSize.getValue();
		veg = (String) view.listVegetation.getSelectedValue();
		vegPopSize = (Integer) view.spinnerVegPopSize.getValue();
		shape = view.buttonGroupShape.getSelectedButtonText(); //TODO: test
		obstacle = (String) view.listObstacle.getSelectedValue();
		noOfThreads = Integer.parseInt(view.buttonGroupThread.getSelectedButtonText());
		tickDelay = (Integer) view.spinnerDelayLength.getValue();
		isRunningWithoutTimer = false;
		if (tickDelay < 1) {
			isRunningWithoutTimer = true;
		}
		isRecording = view.checkBoxRecordSim.isSelected();
		noOfIterations = Integer.MAX_VALUE; //den här borde kanske vara konstant nån annanstans
		if (view.checkBoxLimitIterations.isSelected()) {
			noOfIterations = (Integer) view.spinnerNoOfIterations.getValue();
		}
		
		SimulationSettings simSettings = new SimulationSettings(settingsName, pred, predPopSize, prey, preyPopSize, veg, vegPopSize, shape, obstacle, noOfThreads, isRunningWithoutTimer, isRecording, tickDelay, noOfIterations);
		if (view.checkBoxCustomSize.isSelected()) {
			width = Integer.parseInt(view.textFieldWidth.getText());
			height = Integer.parseInt(view.textFieldHeight.getText());
			simSettings.setSimulationDimension(new Dimension(width, height));
		}
		else {
			simSettings.setSimulationDimension((String) view.listSimDimension.getSelectedValue());
		}
		
		return simSettings;
	}
	
	public void setGUISettings(SimulationSettings s) {
		view.buttonGroupThread.selectButtonByText(s.getNumThreads() + "");
		view.buttonGroupShape.selectButtonByText(s.getShapeModel());
		System.out.println(s.getShapeModel());
		
		view.spinnerDelayLength.setValue(s.getDelayLength()); 
		
		view.listVegetation.setSelectedValue(s.getGrassModel(), true);
		view.listPred.setSelectedValue(s.getPredatorModel(), true);
		view.listPrey.setSelectedValue(s.getPreyModel(), true);
		view.listObstacle.setSelectedValue(s.getObstacle(), true);
		
		view.spinnerPredPopSize.setValue(s.getPredPopSize());
		view.spinnerPreyPopSize.setValue(s.getPreyPopSize());
		view.spinnerVegPopSize.setValue(s.getGrassPopSize());
		
		view.checkBoxRecordSim.setSelected(s.isRecordSimulation());
		
		if(s.getNumIterations() == Integer.MAX_VALUE) {
			view.checkBoxLimitIterations.setSelected(false);
			view.spinnerNoOfIterations.setValue(0);
			view.spinnerNoOfIterations.setEnabled(false);
		} else {
			view.spinnerNoOfIterations.setEnabled(true);
			view.checkBoxLimitIterations.setSelected(true);
			view.spinnerNoOfIterations.setValue(s.getNumIterations());
		}
		
		if(s.getSimDimensionConstant() == null) {
			view.checkBoxCustomSize.setSelected(true);
			view.textFieldWidth.setEnabled(true);
			view.textFieldHeight.setEnabled(true);
			view.listSimDimension.setEnabled(false);
			view.textFieldWidth.setText(s.getSimDimension().width + "");
			view.textFieldHeight.setText(s.getSimDimension().height + "");
		} else {
			view.checkBoxCustomSize.setSelected(false);
			view.listSimDimension.setEnabled(true);
			view.listSimDimension.setSelectedValue(s.getSimDimensionConstant(), true);
			view.textFieldWidth.setEnabled(false);
			view.textFieldHeight.setEnabled(false);
		}
		
	}
		
	@Override
	public void release() {
		// TODO: waddafakk is this?
	}

	@Override
	public void setModel(IModel m) {
		if(m instanceof EcoWorld) {
			this.model = (EcoWorld) m;
		}		
	}
	
	public SimulationSettings getSimSettings() {
		return s;
	}
	
}
