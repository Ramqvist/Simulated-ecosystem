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
		SimulationSettings s = new SimulationSettings(
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
		//Set views to the values of s.
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
