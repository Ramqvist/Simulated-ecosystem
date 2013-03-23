package chalmers.dax021308.ecosystem.controller;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.view.NewSimulationView;

/**
 * Controller class for {@link NewSimulationView}.
 * 
 * @author Erik
 *
 */
public class NewSimulationController implements IController {
	private EcoWorld model;
	private NewSimulationView view;
	/**
	 * ActionListener for start new simulation button.
	 */
	private ActionListener onStartButtonListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			startSimulation();
		}
	};
	
	public NewSimulationController() {
		
	}

	@Override
	public void init() {
		if(model == null) throw new NullPointerException("Model is NULL.");
		if(view == null) {
			view = new NewSimulationView(model);
			view.btnRunSim.addActionListener(onStartButtonListener);
		}
		injectSimulationSettingsToGUI(SimulationSettings.DEFAULT);
		view.show();
	}

	@Override
	public void release() {
		
	}
	
	private SimulationSettings getSimulationSettingsFromGUI() {
		int numThreads;
		if (view.rdbtn2Threads.isSelected()) {
			numThreads = 2;
		} else if (view.rdbtn4Threads.isSelected()) {
			numThreads = 4;
		} else {
			numThreads = 8;
		}
		boolean runWithoutTimer = false;
		int tickDelay = Integer.parseInt(view.textfield_Iterationdelay.getText());
		if (tickDelay < 1) {
			runWithoutTimer = true;
		} 
		int numIterations = Integer.MAX_VALUE;
		if (view.checkBoxLimitIterations.isSelected()) {
			numIterations = Integer.parseInt(view.tvNumIterations.getText());
		} 
		// Should the shape really be set here?
		// TODO fix an input value for shape and not just a square shape.
		String shape = null;
		if(view.rdbtnCircle.isSelected()) {
			shape = SimulationSettings.SHAPE_CIRCLE;
		} else if (view.rdbtnSquare.isSelected()){
			shape = SimulationSettings.SHAPE_SQUARE;
		} else {
			shape = SimulationSettings.SHAPE_TRIANGLE;
		}
		SimulationSettings s = new SimulationSettings("Custom",
				(String) view.predList.getSelectedValue(),
				Integer.parseInt(view.tvPredPopSize.getText()),
				(String) view.preyList.getSelectedValue(),
				Integer.parseInt(view.tvPreyPopSize.getText()),
				(String) view.grassList.getSelectedValue(),
				Integer.parseInt(view.tvGrassPopSize.getText()), shape, 
				(String) view.obstacleList.getSelectedValue(), numThreads, 
				runWithoutTimer, 
				view.chckbxRecordSimulation.isSelected(), 
				tickDelay, 
				numIterations);
		if(view.chckbxCustomSize.isSelected()) {
			int width = Integer.parseInt(view.tfCustomWidth.getText());
			int height = Integer.parseInt(view.tfCustomHeight.getText());
			s.setSimulationDimension(new Dimension(width, height));
		} else {
			s.setSimulationDimension((String) view.listSimulationDim.getSelectedValue());
		}
		return s;
	}
	
	private void injectSimulationSettingsToGUI(SimulationSettings s) {
		/* Inverse of method getSimulationSettingsFromGUI() */
		int numThreads = s.getNumThreads();
		if(numThreads == 2) {
			view.rdbtn2Threads.setSelected(true);
			view.rdbtn4Threads.setSelected(false);
			view.rdbtn8Threads.setSelected(false);
		} else if(numThreads == 4) {
			view.rdbtn2Threads.setSelected(false);
			view.rdbtn4Threads.setSelected(true);
			view.rdbtn8Threads.setSelected(false);
		} else if(numThreads == 8) {
			view.rdbtn2Threads.setSelected(false);
			view.rdbtn4Threads.setSelected(false);
			view.rdbtn8Threads.setSelected(true);
		}

		view.textfield_Iterationdelay.setText(s.getDelayLength() + "");
		
		if(s.getNumIterations() == Integer.MAX_VALUE) {
			view.checkBoxLimitIterations.setSelected(false);
			view.tvNumIterations.setText("0");
			view.tvNumIterations.setEnabled(false);
		} else {
			view.tvNumIterations.setEnabled(true);
			view.checkBoxLimitIterations.setSelected(true);
			view.tvNumIterations.setText(s.getNumIterations() + "");
		}
		String shape = s.getShapeModel();
		if(shape == SimulationSettings.SHAPE_CIRCLE) {
			view.rdbtnCircle.setSelected(true);
			view.rdbtnSquare.setSelected(false);
			view.rdbtnTriangle.setSelected(false);
		} else if(shape == SimulationSettings.SHAPE_SQUARE) {
			view.rdbtnCircle.setSelected(false);
			view.rdbtnSquare.setSelected(true);
			view.rdbtnTriangle.setSelected(false);
		} else if(shape == SimulationSettings.SHAPE_TRIANGLE) {
			view.rdbtnCircle.setSelected(false);
			view.rdbtnSquare.setSelected(false);
			view.rdbtnTriangle.setSelected(true);
		}
		
		if(s.getSimDimensionConstant() == null) {
			view.chckbxCustomSize.setSelected(true);
			view.tfCustomWidth.setEnabled(true);
			view.tfCustomHeight.setEnabled(true);
			view.listSimulationDim.setEnabled(false);
			view.tfCustomWidth.setText(s.getSimDimension().width + "");
			view.tfCustomHeight.setText(s.getSimDimension().height + "");
		} else {
			view.chckbxCustomSize.setSelected(false);
			view.listSimulationDim.setEnabled(true);
			view.listSimulationDim.setSelectedValue(s.getSimDimensionConstant(), true);
			view.tfCustomWidth.setEnabled(false);
			view.tfCustomHeight.setEnabled(false);
		}
		
		view.grassList.setSelectedValue(s.getGrassModel(), true);
		view.predList.setSelectedValue(s.getPredatorModel(), true);
		view.preyList.setSelectedValue(s.getPreyModel(), true);
		view.obstacleList.setSelectedValue(s.getObstacle(), true);
		
		view.chckbxRecordSimulation.setSelected(s.isRecordSimulation());
		view.tvPredPopSize.setText(s.getPredPopSize() + "");
		view.tvPreyPopSize.setText(s.getPreyPopSize() + "");
		view.tvGrassPopSize.setText(s.getGrassPopSize() + "");
	}

	@Override
	public void setModel(IModel m) {
		if(m instanceof EcoWorld) {
			this.model = (EcoWorld) m;
		}
	}
	
	/**
	 * Loads settings from the view and into {@link EcoWorld} and starts it.
	 * 
	 */
	private void startSimulation() {
		try {
			try {
				model.stop();
			} catch (IllegalStateException e) {
			}
			model.loadSimulationSettings(getSimulationSettingsFromGUI());
			try {
				model.start();
			} catch (IllegalStateException e) {
				Log.v(e.toString());
			}
			view.hide();
		} catch (Exception e) {
			view.showErrorMessage();
		}
	}


}
