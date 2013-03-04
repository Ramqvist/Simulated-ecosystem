package chalmers.dax021308.ecosystem.view;

import javax.swing.JPanel;
import javax.swing.JButton;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import net.miginfocom.swing.MigLayout;

/**
 * The view that holds the controls for starting, stopping and pausing a simulation.
 * A panel that is part of the frame that holds the entire application.
 * 
 * @author Hanna 
 *
 */

public class ControlView extends JPanel {

	/**
	 * Create the panel.
	 */
	public ControlView(final EcoWorld ew) {  
		
		JButton btnStart = new JButton("Start");
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ew.start();
			}
		});
		setLayout(new MigLayout("", "[63px][61px][69px]", "[25px]"));
		add(btnStart, "cell 0 0,alignx left,aligny top");
		
		JButton btnStop = new JButton("Stop");
		btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ew.stop();
				ew.forceStop();
			}
		});
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(btnStop, "cell 1 0,alignx left,aligny top");
		
		JButton btnPause = new JButton("Pause");
		btnPause.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(btnPause, "cell 2 0,alignx left,aligny top");

	}

}
