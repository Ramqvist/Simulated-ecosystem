package chalmers.dax021308.ecosystem.view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;

/**
 * General props: maxSpeed, visionRange, maxAcceleration, max energy, max life length, reproduction rate, digestion time
 * Wolf: sprinting.
 * Deer: stotting.
 * Grass: 
 * @author Albin
 */
@SuppressWarnings("serial")
public class AgentSettingsView extends JTabbedPane implements IView {

	private JPanel wolfPanel;
	private JPanel deerPanel;
	private JPanel grassPanel;
	private JSpinner maxSpeedWolf;
	private JSpinner visionRangeWolf;
	private JSpinner maxAccelerationWolf;
	
	public AgentSettingsView() {
		maxSpeedWolf = new JSpinner();
		maxAccelerationWolf = new JSpinner();
		visionRangeWolf = new JSpinner();
		
		wolfPanel = new JPanel();
		wolfPanel.setLayout(new BoxLayout(wolfPanel, BoxLayout.Y_AXIS));
		wolfPanel.add(maxSpeedWolf);
		wolfPanel.add(visionRangeWolf);
		wolfPanel.add(maxAccelerationWolf);
		
		this.addTab("Wolf", wolfPanel);
		
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
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

	
	public static void main(String[] args) {
		AgentSettingsView asv = new AgentSettingsView();
		
		JFrame f = new JFrame();
		f.add(asv);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.pack();
		f.setVisible(true);
	}
}
