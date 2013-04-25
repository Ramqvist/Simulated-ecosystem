package chalmers.dax021308.ecosystem.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

@SuppressWarnings("serial")
public class GrassSettingsPanel extends JPanel implements IView {

	private JPanel labelPanel;
	private JPanel componentPanel;
	private JSpinner maxLifeLengthSpinner;
	private JSpinner maxReproductionRateSpinner;
	private JLabel maxLifeLengthLabel;
	private JLabel maxReproductionRateLabel;
	private SpinnerNumberModel maxLifeLengthModel;
	private SpinnerNumberModel maxReproductionRateModel;
	
	public GrassSettingsPanel() {
		init();
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		maxLifeLengthModel = new SpinnerNumberModel(0, 0, 10000, 0.1);
		maxReproductionRateModel = new SpinnerNumberModel(0, 0, 10000, 0.1);
		
		maxLifeLengthSpinner = new JSpinner(maxLifeLengthModel);
		maxReproductionRateSpinner = new JSpinner(maxReproductionRateModel);
		
		maxLifeLengthLabel = new JLabel("Max Life Length: ");
		maxLifeLengthLabel.setAlignmentX(RIGHT_ALIGNMENT);
		maxReproductionRateLabel = new JLabel("Max Reproduction Rate: ");
		maxReproductionRateLabel.setAlignmentX(RIGHT_ALIGNMENT);

		componentPanel = new JPanel();
		componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));
		componentPanel.add(maxLifeLengthSpinner);
		componentPanel.add(Box.createRigidArea(new Dimension(0, 4)));
		componentPanel.add(maxReproductionRateSpinner);
		componentPanel.add(Box.createRigidArea(new Dimension(0, 4)));
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.add(Box.createRigidArea(new Dimension(0, 2)));
		labelPanel.add(maxLifeLengthLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(0, 6)));
		labelPanel.add(maxReproductionRateLabel);
		
		this.setLayout(new BorderLayout());
		this.add(labelPanel, BorderLayout.WEST);
		this.add(componentPanel, BorderLayout.EAST);
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
