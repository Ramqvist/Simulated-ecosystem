package chalmers.dax021308.ecosystem.view.populationsettings;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import chalmers.dax021308.ecosystem.view.IView;

@SuppressWarnings("serial")
public class WolfSettingsPanel extends JPanel implements IView {

	private JPanel labelPanel;
	private JPanel componentPanel;
	private JSpinner maxSpeedSpinner;
	private JSpinner maxAccelerationSpinner;
	private JSpinner visionRangeSpinner;
	private JSpinner maxEnergySpinner;
	private JSpinner maxLifeLengthSpinner;
	private JSpinner maxReproductionRateSpinner;
	private JSpinner maxDigestionTimeSpinner;
	private JCheckBox sprintingCheckBox;
	private JLabel maxSpeedLabel;
	private JLabel maxAccelerationLabel;
	private JLabel visionRangeLabelWolf;
	private JLabel maxEnergyLabel;
	private JLabel maxLifeLengthLabel;
	private JLabel maxReproductionRateLabel;
	private JLabel maxDigestionRateLabel;
	private JLabel sprintingLabel;
	private SpinnerNumberModel maxSpeedModel;
	private SpinnerNumberModel maxAccelerationModel;
	private SpinnerNumberModel visionRangeModel;
	private SpinnerNumberModel maxEnergyModel;
	private SpinnerNumberModel maxLifeLengthModel;
	private SpinnerNumberModel maxReproductionRateModel;
	private SpinnerNumberModel maxDigestionRateModel;
	
	public WolfSettingsPanel() {
		init();
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		maxSpeedModel = new SpinnerNumberModel(0, 0, 10000, 0.1);
		maxAccelerationModel = new SpinnerNumberModel(0, 0, 10000, 0.1);
		visionRangeModel = new SpinnerNumberModel(0, 0, 10000, 0.1);
		maxEnergyModel = new SpinnerNumberModel(0, 0, 10000, 0.1);
		maxLifeLengthModel = new SpinnerNumberModel(0, 0, 10000, 0.1);
		maxReproductionRateModel = new SpinnerNumberModel(0, 0, 10000, 0.1);
		maxDigestionRateModel = new SpinnerNumberModel(0, 0, 10000, 0.1);
		
		maxSpeedSpinner = new JSpinner(maxSpeedModel);
		maxAccelerationSpinner = new JSpinner(maxAccelerationModel);
		visionRangeSpinner = new JSpinner(visionRangeModel);
		maxEnergySpinner = new JSpinner(maxEnergyModel);
		maxLifeLengthSpinner = new JSpinner(maxLifeLengthModel);
		maxReproductionRateSpinner = new JSpinner(maxReproductionRateModel);
		maxDigestionTimeSpinner = new JSpinner(maxDigestionRateModel);
		sprintingCheckBox = new JCheckBox();
		sprintingCheckBox.setAlignmentX(CENTER_ALIGNMENT);
		
		maxSpeedLabel = new JLabel("Max Speed: ");
		maxSpeedLabel.setAlignmentX(RIGHT_ALIGNMENT);
		maxAccelerationLabel = new JLabel("Max Acceleration: ");
		maxAccelerationLabel.setAlignmentX(RIGHT_ALIGNMENT);
		visionRangeLabelWolf = new JLabel("Vision Range: ");
		visionRangeLabelWolf.setAlignmentX(RIGHT_ALIGNMENT);
		maxEnergyLabel = new JLabel("Max Energy: ");
		maxEnergyLabel.setAlignmentX(RIGHT_ALIGNMENT);
		maxLifeLengthLabel = new JLabel("Max Life Length: ");
		maxLifeLengthLabel.setAlignmentX(RIGHT_ALIGNMENT);
		maxReproductionRateLabel = new JLabel("Max Reproduction Rate: ");
		maxReproductionRateLabel.setAlignmentX(RIGHT_ALIGNMENT);
		maxDigestionRateLabel = new JLabel("Max Digestion Time: ");
		maxDigestionRateLabel.setAlignmentX(RIGHT_ALIGNMENT);
		sprintingLabel = new JLabel("Sprinting: ");
		sprintingLabel.setAlignmentX(RIGHT_ALIGNMENT);

		componentPanel = new JPanel();
		componentPanel.setLayout(new BoxLayout(componentPanel, BoxLayout.Y_AXIS));
		componentPanel.add(maxSpeedSpinner);
		componentPanel.add(Box.createRigidArea(new Dimension(0, 4)));
		componentPanel.add(maxAccelerationSpinner);
		componentPanel.add(Box.createRigidArea(new Dimension(0, 4)));
		componentPanel.add(visionRangeSpinner);
		componentPanel.add(Box.createRigidArea(new Dimension(0, 4)));
		componentPanel.add(maxEnergySpinner);
		componentPanel.add(Box.createRigidArea(new Dimension(0, 4)));
		componentPanel.add(maxLifeLengthSpinner);
		componentPanel.add(Box.createRigidArea(new Dimension(0, 4)));
		componentPanel.add(maxReproductionRateSpinner);
		componentPanel.add(Box.createRigidArea(new Dimension(0, 4)));
		componentPanel.add(maxDigestionTimeSpinner);
		componentPanel.add(Box.createRigidArea(new Dimension(0, 4)));
		componentPanel.add(sprintingCheckBox);
		
		labelPanel = new JPanel();
		labelPanel.setLayout(new BoxLayout(labelPanel, BoxLayout.Y_AXIS));
		labelPanel.add(Box.createRigidArea(new Dimension(0, 2)));
		labelPanel.add(maxSpeedLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(0, 6)));
		labelPanel.add(maxAccelerationLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		labelPanel.add(visionRangeLabelWolf);
		labelPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		labelPanel.add(maxEnergyLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		labelPanel.add(maxLifeLengthLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		labelPanel.add(maxReproductionRateLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		labelPanel.add(maxDigestionRateLabel);
		labelPanel.add(Box.createRigidArea(new Dimension(0, 8)));
		labelPanel.add(sprintingLabel);
		
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
