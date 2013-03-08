package chalmers.dax021308.ecosystem.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JRadioButton;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.JSlider;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import javax.swing.JTextField;
import javax.swing.JButton;

/**
 * The view that holds the parameters that can be changed during a simulation.
 * A panel that is part of the frame that holds the entire application.
 * 
 * Lots of thing here should be done in a separate class ParameterController. //Erik
 * Added methods to be called. //Erik
 * 
 * @author Hanna
 *
 */

public class ParameterView extends JPanel implements IView {
	private static final long serialVersionUID = -4258629187312881582L;
	private final ButtonGroup shapeButtonGroup = new ButtonGroup();
	private final ButtonGroup obstacleButtonGroup = new ButtonGroup();
	private final ButtonGroup delayButtonGroup = new ButtonGroup();
	private JTextField textboxDelayLength;
	private JTextField textField_1;
	private JTextField textField_2;

	/**
	 * Create the panel.
	 */
	public ParameterView(final EcoWorld model) {
		setLayout(new GridLayout(4, 3, 0, 0));
		
		JLabel lblDelay = new JLabel("Delay: ");
		lblDelay.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDelay.setFont(new Font("Tahoma", Font.PLAIN, 11));
		add(lblDelay);
		
		JRadioButton delayOn = new JRadioButton("On");
		delayOn.setSelected(true);
		delayOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DELAY ON
				//Method to be run: EcoWorld.setRunWithoutTimer(true);
				System.out.println("delay on");
			}
		});
		delayButtonGroup.add(delayOn);
		add(delayOn);
		
		JRadioButton delayOff = new JRadioButton("Off");
		delayOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DELAY OFF
				//Method to be run: EcoWorld.setRunWithoutTimer(false);
				System.out.println("delay off");
			}
		});
		delayButtonGroup.add(delayOff);
		add(delayOff);
		
		JLabel lblNewLabel_1 = new JLabel("");
		add(lblNewLabel_1);
		
		JLabel lblPopulationGrowthRate = new JLabel("Population growth rate: ");
		lblPopulationGrowthRate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPopulationGrowthRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		add(lblPopulationGrowthRate);
		
		final JSlider sliderPopulationGrowthRate = new JSlider();
		sliderPopulationGrowthRate.setPaintLabels(true);
		sliderPopulationGrowthRate.setPaintTicks(true);
		sliderPopulationGrowthRate.setMinimum(1);
		sliderPopulationGrowthRate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!sliderPopulationGrowthRate.getValueIsAdjusting()) {  //Checks that the slider is fixed, i.e. not adjusting
					//SET POPULATION GROWTH RATE
					//TODO: No method for this yet, needs implementation.
					System.out.println("population growth rate set");
				}
			}
		});
		add(sliderPopulationGrowthRate);
		
		textField_1 = new JTextField();
		textField_1.setText("0.15");
		textField_1.setFont(new Font("Tahoma", Font.PLAIN, 20));
		add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("");
		add(lblNewLabel);
		
		JLabel lblPredatorEatingRate = new JLabel("Predator eating rate: ");
		lblPredatorEatingRate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPredatorEatingRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		add(lblPredatorEatingRate);
		
		final JSlider sliderEatingRate = new JSlider(); //Vilket spann?
		sliderEatingRate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!sliderEatingRate.getValueIsAdjusting()) {  //Checks that the slider is fixed, i.e. not adjusting
					//SET DELAY LENGTH
					//Method to be run: EcoWorld.adjustTickRate(newTickRate);
					System.out.println("delay length set");
				}
			}
		});
		add(sliderEatingRate);
		
		textField_2 = new JTextField();
		textField_2.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textField_2.setText("15");
		add(textField_2);
		textField_2.setColumns(10);
		
		JLabel label = new JLabel("");
		add(label);
		
		JLabel lblDelayLength = new JLabel("Delay length: ");
		lblDelayLength.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDelayLength.setFont(new Font("Tahoma", Font.PLAIN, 11));
		add(lblDelayLength);
		
		final JSlider sliderDelayLength = new JSlider(); //Vilket spann? Oklart beh�vs tas upp p� m�te. //Erik
		sliderDelayLength.setValue(16);
		sliderDelayLength.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!sliderDelayLength.getValueIsAdjusting()) {  //Checks that the slider is fixed, i.e. not adjusting
					textboxDelayLength.setText(sliderDelayLength.getValue() + "");
					
				}
			}
		});
		add(sliderDelayLength);
		
		textboxDelayLength = new JTextField();
		textboxDelayLength.setFont(new Font("Tahoma", Font.PLAIN, 20));
		textboxDelayLength.setText("16");
		add(textboxDelayLength);
		textboxDelayLength.setColumns(10);
		
		JButton btnUpdateSettings = new JButton("Update");
		btnUpdateSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int delay = Integer.parseInt(textboxDelayLength.getText());
					if(delay == 0) {
						model.setDelayLength(0);
						model.setRunWithoutTimer(true);
					} else {
						model.setDelayLength(delay);
						model.setRunWithoutTimer(false);
					}
				} catch (Exception ex) {
					
				}
			}
		});
		add(btnUpdateSettings);

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

}
