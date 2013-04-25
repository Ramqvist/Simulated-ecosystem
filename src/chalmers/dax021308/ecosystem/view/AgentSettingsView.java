package chalmers.dax021308.ecosystem.view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;

/**
 * A tabbed view to modify specific parameters for our current agents.
 * 
 * General props: maxSpeed, visionRange, maxAcceleration, max energy, max life length, reproduction rate, digestion time
 * Wolf: sprinting.
 * Deer: stotting.
 * Grass: 
 * @author Albin
 */
@SuppressWarnings("serial")
public class AgentSettingsView extends JTabbedPane implements IView {

	private WolfSettingsPanel wolfPanel;
	private DeerSettingsPanel deerPanel;
	private GrassSettingsPanel grassPanel;
	
	
	public AgentSettingsView() {
		init();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		wolfPanel = new WolfSettingsPanel();
		deerPanel = new DeerSettingsPanel();
		grassPanel = new GrassSettingsPanel();
		this.addTab("Wolf", wolfPanel);
		this.addTab("Deer", deerPanel);
		this.addTab("Grass", grassPanel);
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
