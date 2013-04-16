package chalmers.dax021308.ecosystem.view;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.util.Log;

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
	private static final long serialVersionUID = 5134812517352174052L;
	
	public final JButton btnStartNew;
	public final JButton btnStart;
	public final JButton btnStop;
	public final JButton btnPause;

	/**
	 * Create the panel.
	 */
	public ControlView(EcoWorld model) {  
		
		btnStart = new JButton(new ImageIcon("res/play.png"));
		btnStart.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStop = new JButton(new ImageIcon("res/stop.png"));
		btnPause = new JButton(new ImageIcon("res/pause.png"));
		btnPause.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStartNew = new JButton("Start new Simulation");
		btnStartNew.setFont(new Font("Tahoma", Font.PLAIN, 14));

		setLayout(new MigLayout("", "[63px][61px][69px][][][]", "[25px]"));
		add(btnStart, "cell 0 0,alignx left,aligny top");
		
		add(btnPause, "flowx,cell 1 0,alignx left,aligny top");
		btnStop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(btnStop, "cell 1 0,alignx left,aligny top");
		add(btnStartNew, "cell 5 0,alignx left,aligny top");
	}

}
