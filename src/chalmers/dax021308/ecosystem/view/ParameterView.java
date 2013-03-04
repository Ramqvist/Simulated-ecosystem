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

/**
 * The view that holds the parameters that can be changed during a simulation.
 * A panel that is part of the frame that holds the entire application.
 * 
 * @author Hanna
 *
 */

public class ParameterView extends JPanel implements IView {
	private final ButtonGroup shapeButtonGroup = new ButtonGroup();
	private final ButtonGroup obstacleButtonGroup = new ButtonGroup();
	private final ButtonGroup delayButtonGroup = new ButtonGroup();

	/**
	 * Create the panel.
	 */
	public ParameterView() {
		setLayout(new GridLayout(4, 3, 0, 0));
		
		JLabel lblDelay = new JLabel("Delay: ");
		lblDelay.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDelay.setFont(new Font("Tahoma", Font.PLAIN, 11));
		add(lblDelay);
		
		JRadioButton delayOn = new JRadioButton("On");
		delayOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DELAY ON
				System.out.println("delay on");
			}
		});
		delayButtonGroup.add(delayOn);
		add(delayOn);
		
		JRadioButton delayOff = new JRadioButton("Off");
		delayOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DELAY OFF
				System.out.println("delay off");
			}
		});
		delayButtonGroup.add(delayOff);
		add(delayOff);
		
		JLabel lblDelayLength = new JLabel("Delay length: ");
		lblDelayLength.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDelayLength.setFont(new Font("Tahoma", Font.PLAIN, 11));
		add(lblDelayLength);
		
		final JSlider sliderDelayLength = new JSlider(); //Vilket spann?
		sliderDelayLength.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!sliderDelayLength.getValueIsAdjusting()) {  //Checks that the slider is fixed, i.e. not adjusting
					//SET DELAY LENGTH
					System.out.println("delay length set");
				}
			}
		});
		add(sliderDelayLength);
		
		JLabel lblNewLabel = new JLabel("");
		add(lblNewLabel);
		
		JLabel lblPopulationGrowthRate = new JLabel("Population growth rate: ");
		lblPopulationGrowthRate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPopulationGrowthRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		add(lblPopulationGrowthRate);
		
		final JSlider sliderPopulationGrowthRate = new JSlider(); //Vilket spann?
		sliderPopulationGrowthRate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!sliderPopulationGrowthRate.getValueIsAdjusting()) {  //Checks that the slider is fixed, i.e. not adjusting
					//SET POPULATION GROWTH RATE
					System.out.println("population growth rate set");
				}
			}
		});
		add(sliderPopulationGrowthRate);
		
		JLabel label = new JLabel("");
		add(label);
		
		JLabel lblPredatorEatingRate = new JLabel("Predator eating rate: ");
		lblPredatorEatingRate.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPredatorEatingRate.setFont(new Font("Tahoma", Font.PLAIN, 11));
		add(lblPredatorEatingRate);
		
		final JSlider sliderEatingRate = new JSlider(); //Vilket spann?
		sliderEatingRate.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if (!sliderEatingRate.getValueIsAdjusting()) {  //Checks that the slider is fixed, i.e. not adjusting
					//SET EATING RATE
					System.out.println("eating rate set");
				}
			}
		});
		add(sliderEatingRate);

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
