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

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;

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
	
	public ControlView(EcoWorld model) {  
		playPauseButton = new JButton(new ImageIcon("res/pause.png"));//("res/pause.png"));
		restartButton = new JButton(new ImageIcon("res/restart.png"));
		iterationDelaySpinner = new JSpinner();
		iterationDelayPanel = new JPanel();
		iterationDelayLabel = new JLabel("Iteration Delay");
		init();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		playPauseButton.setMaximumSize(new Dimension(40, 40));
		playPauseButton.setMinimumSize(new Dimension(40, 40));
		playPauseButton.setToolTipText("Pause");
		restartButton.setMaximumSize(new Dimension(40, 40));
		restartButton.setMinimumSize(new Dimension(40, 40));
		restartButton.setToolTipText("Restart");
		
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
		
		play = true;
	}

	@Override
	public void addController(ActionListener controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}
}
