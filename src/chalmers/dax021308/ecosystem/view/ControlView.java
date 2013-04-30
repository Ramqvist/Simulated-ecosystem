package chalmers.dax021308.ecosystem.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;

/**
 * The view that holds the controls for starting, stopping and pausing a simulation.
 * A panel that is part of the frame that holds the entire application.
 * 
 * @author Hanna & Albin
 *
 */

public class ControlView extends JPanel implements IView {
	private static final long serialVersionUID = 5134812517352174052L;
	
	public final JButton restartButton;
	public final JButton playPauseButton;
	public boolean play;
	
	public ControlView(EcoWorld model) {  
		playPauseButton = new JButton(new ImageIcon("res/pause.png"));
		restartButton = new JButton(new ImageIcon("res/restart.png"));
		init();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		playPauseButton.setMaximumSize(new Dimension(30, 30));
		playPauseButton.setMinimumSize(new Dimension(30, 30));
		playPauseButton.setToolTipText("Pause");
		restartButton.setMaximumSize(new Dimension(30, 30));
		restartButton.setMinimumSize(new Dimension(30, 30));
		restartButton.setToolTipText("Restart");
		
		setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		add(playPauseButton);
		add(Box.createRigidArea(new Dimension(5, 0)));
		add(restartButton);
		
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
