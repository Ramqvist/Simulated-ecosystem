package chalmers.dax021308.ecosystem.view.populationsettings;

import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import net.miginfocom.swing.MigLayout;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.JToggleButton;
import javax.swing.JSlider;
import javax.swing.JCheckBox;

public class CommonSettingsPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JTextField textField_6;
	
	public CommonSettingsPanel() {
		setLayout(new MigLayout("", "[][][grow][grow]", "[][][][][][][][][][][][][][][][][][][][]"));
		
		JLabel lblDeerSettings = new JLabel("Common settings");
		lblDeerSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblDeerSettings, "cell 1 0 2 1");
		
		JLabel lblNewLabel = new JLabel("Max energy");
		add(lblNewLabel, "cell 1 2,alignx right,aligny baseline");
		
		textField = new JTextField();
		add(textField, "cell 2 2,growx");
		textField.setColumns(10);
		
		JSlider slider = new JSlider();
		add(slider, "cell 3 2");
		
		JLabel lblMaxAcceleration = new JLabel("Max acceleration");
		lblMaxAcceleration.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
		add(lblMaxAcceleration, "cell 1 3,alignx right");
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		add(textField_1, "cell 2 3,growx");
		
		JSlider slider_1 = new JSlider();
		add(slider_1, "cell 3 3");
		
		JLabel lblMaxSpeed = new JLabel("Vision Range");
		lblMaxSpeed.setHorizontalTextPosition(javax.swing.SwingConstants.LEFT);
		add(lblMaxSpeed, "cell 1 4,alignx trailing");
		
		textField_2 = new JTextField();
		add(textField_2, "cell 2 4,growx");
		textField_2.setColumns(10);
		
		JSlider slider_2 = new JSlider();
		add(slider_2, "cell 3 4");
		
		JLabel lblMaxEnergy = new JLabel("Max Energy");
		lblMaxEnergy.setHorizontalTextPosition(SwingConstants.LEFT);
		add(lblMaxEnergy, "cell 1 5,alignx trailing");
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		add(textField_3, "cell 2 5,growx");
		
		JSlider slider_3 = new JSlider();
		add(slider_3, "cell 3 5");
		
		JLabel lblMaxLifeLength = new JLabel("Max Life Length");
		lblMaxLifeLength.setHorizontalTextPosition(SwingConstants.LEFT);
		add(lblMaxLifeLength, "cell 1 6,alignx trailing");
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		add(textField_4, "cell 2 6,growx");
		
		JSlider slider_4 = new JSlider();
		add(slider_4, "cell 3 6");
		
		JLabel lblMaxReproductionRate = new JLabel("Max Reproduction Rate");
		lblMaxReproductionRate.setHorizontalTextPosition(SwingConstants.LEFT);
		add(lblMaxReproductionRate, "cell 1 7,alignx trailing");
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		add(textField_5, "cell 2 7,growx");
		
		JSlider slider_5 = new JSlider();
		add(slider_5, "cell 3 7");
		
		JLabel lblMaxDigiestionTime = new JLabel("Max Digiestion Time");
		lblMaxDigiestionTime.setHorizontalTextPosition(SwingConstants.LEFT);
		add(lblMaxDigiestionTime, "cell 1 8,alignx trailing");
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		add(textField_6, "cell 2 8,growx");
		
		JSlider slider_6 = new JSlider();
		add(slider_6, "cell 3 8");
		
		JButton btnNewButton = new JButton("Update values");
		add(btnNewButton, "cell 2 10,growx");
		
		JCheckBox chckbxPathfinding = new JCheckBox("Pathfinding");
		add(chckbxPathfinding, "cell 1 12");
		
		JCheckBox chckbxFocusPrey = new JCheckBox("Focus Prey");
		add(chckbxFocusPrey, "cell 2 12");
		
		JCheckBox chckbxGroupBehavior = new JCheckBox("Group behavior");
		add(chckbxGroupBehavior, "cell 3 12");
		
	}
	
	
	public static void main(String[] args) {
		CommonSettingsPanel panel = new CommonSettingsPanel();
		JFrame frame = new JFrame();
		frame.getContentPane().add(panel);
		frame.setSize(new Dimension(500, 600));
		frame.setVisible(true);
	}
}
