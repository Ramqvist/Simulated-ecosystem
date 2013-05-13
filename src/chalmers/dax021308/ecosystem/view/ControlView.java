package chalmers.dax021308.ecosystem.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.util.Log;

/**
 * The view that holds the controls for starting, stopping and pausing a simulation.
 * A panel that is part of the frame that holds the entire application.
 * 
 * @author Hanna & Albin
 */

public class ControlView extends JPanel implements IView {
	
	private static final long serialVersionUID = 5134812517352174052L;
	private static final int DEFAULT_ITERATION_DELAY = 16;
	public final JButton restartButton;
	public final JButton playPauseButton;
	public final JSpinner iterationDelaySpinner;
	public final JPanel iterationDelayPanel;
	public final JLabel iterationDelayLabel;
	public boolean play;
	private IModel model;
	
	public ControlView(IModel model) {  
		playPauseButton = new JButton(new ImageIcon("res/play.png"));//("res/pause.png"));
		restartButton = new JButton(new ImageIcon("res/restart.png"));
		iterationDelaySpinner = new JSpinner();
		iterationDelayPanel = new JPanel();
		iterationDelayLabel = new JLabel("Iteration Delay");
		this.model = model;
		model.addObserver(this);
		init();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName() == EcoWorld.EVENT_START) {
			if (!playPauseButton.isEnabled()) {
				playPauseButton.setEnabled(true);
				restartButton.setEnabled(true);
			}
			swithToPauseButton();
		} else if (evt.getPropertyName() == EcoWorld.EVENT_PAUSE) {
			switchToPlayButton();
		} else if (evt.getPropertyName() == EcoWorld.EVENT_STOP) {
			switchToPlayButton();
			playPauseButton.setEnabled(false);
			restartButton.setEnabled(false);
		} else if (evt.getPropertyName() == EcoWorld.EVENT_SETTINGS_CHANGED) {
			Object o = evt.getNewValue();
			if(o instanceof SimulationSettings) {
				SimulationSettings s = (SimulationSettings) o;
				iterationDelaySpinner.setValue(s.getDelayLength());
			}
		}
	}

	@Override
	public void init() {
		playPauseButton.setMaximumSize(new Dimension(40, 40));
		playPauseButton.setMinimumSize(new Dimension(40, 40));
		playPauseButton.setToolTipText("Play");
		playPauseButton.setEnabled(false);
		restartButton.setMaximumSize(new Dimension(40, 40));
		restartButton.setMinimumSize(new Dimension(40, 40));
		restartButton.setToolTipText("Restart");
		restartButton.setEnabled(false);
		
		iterationDelaySpinner.setModel(new SpinnerNumberModel(DEFAULT_ITERATION_DELAY, 0, 100, 1));
		iterationDelaySpinner.setAlignmentX(LEFT_ALIGNMENT);
		
		iterationDelayLabel.setAlignmentX(LEFT_ALIGNMENT);
		
		iterationDelayPanel.setLayout(new BoxLayout(iterationDelayPanel, BoxLayout.Y_AXIS));
		iterationDelayPanel.setMaximumSize(new Dimension(72, 100));
		iterationDelayPanel.add(iterationDelayLabel);
		iterationDelayPanel.add(iterationDelaySpinner);
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(playPauseButton);
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(restartButton);
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(iterationDelayPanel);
		
		play = false;
	}

	@Override
	public void addController(ActionListener controller) {
		
	}

	@Override
	public void release() {
		model.removeObserver(this);
		model = null;
	}
	
	public void switchToPlayButton() {
		play = false;
		playPauseButton.setIcon(new ImageIcon("res/play.png"));
		playPauseButton.setToolTipText("Play");
	}
	
	public void swithToPauseButton() {
		play = true;
		playPauseButton.setIcon(new ImageIcon("res/pause.png"));
		playPauseButton.setToolTipText("Pause");
	}
}
