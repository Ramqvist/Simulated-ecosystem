package chalmers.dax021308.ecosystem.view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;

public class LiveSettingsView extends JPanel {
	static final int DEFAULT_ITERATION_DELAY = 16;
	
	private JPanel panelFirst;
	private JPanel panelSecond;
	private JPanel panelThird;
	private JPanel panelFourth;
	
	private JLabel labelIterationDelay;
	private JLabel labelHeatMapPop;
	
	public final JSpinner spinnerDelayLength;
	
	public final JComboBox<String> comboBoxHeatMapPop;
	
	public String[] populationNames = {"Deers","Wolves","Grass"};
	/*
	public final JRadioButton radioButtonDelayOn;
	public final JRadioButton radioButtonDelayOff;
	
	
	public final ButtonGroupWrapper buttonGroupDelay;
	*/
	
	//public final JButton buttonUpdate;
	
	public LiveSettingsView(EcoWorld model) {
		setLayout(new GridBagLayout());
		
		panelFirst = new JPanel();
		panelSecond = new JPanel();
		panelThird = new JPanel();
		panelFourth = new JPanel();
		
		labelIterationDelay = new JLabel("Iteration Delay");
		labelHeatMapPop = new JLabel("Populations shown on heat map");
		
		spinnerDelayLength = new JSpinner();
		spinnerDelayLength.setModel(new SpinnerNumberModel(DEFAULT_ITERATION_DELAY, 0, 100, 1));
		
		comboBoxHeatMapPop = new JComboBox<String>(populationNames);
		
		/*
		radioButtonDelayOn = new JRadioButton("Delay On");
		radioButtonDelayOff = new JRadioButton("Delay Off");
		
		buttonGroupDelay = new ButtonGroupWrapper();
		*/
		
		//buttonUpdate = new JButton("Update");
		
		/*
		buttonGroupDelay.add(radioButtonDelayOn);
		buttonGroupDelay.add(radioButtonDelayOff);
		*/
		
		panelFirst.setLayout(new GridBagLayout());
		panelSecond.setLayout(new GridBagLayout());
		panelThird.setLayout(new GridBagLayout());
		panelFourth.setLayout(new GridBagLayout());
		
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		//c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		panelFirst.add(labelIterationDelay, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		//c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		panelFirst.add(spinnerDelayLength, c);
		
		
		/*
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		//c.weighty = 0;
		c.gridx = 0;
		c.gridy = 1;
		panelFirst.add(radioButtonDelayOn, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		//c.weighty = 0;
		c.gridx = 0;
		c.gridy = 2;
		panelFirst.add(radioButtonDelayOff, c);
		*/
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		//c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		panelSecond.add(labelHeatMapPop, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		//c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		panelSecond.add(comboBoxHeatMapPop, c);
		
		/*
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weightx = 0.5;
		//c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		panelFourth.add(buttonUpdate, c);
		*/
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.5;
		//c.weighty = 0;
		c.gridx = 0;
		c.gridy = 0;
		add(panelFirst, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.5;
		//c.weighty = 0;
		c.gridx = 1;
		c.gridy = 0;
		add(panelSecond, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.5;
		//c.weighty = 0;
		c.gridx = 2;
		c.gridy = 0;
		add(panelThird, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.5;
		//c.weighty = 0;
		c.gridx = 3;
		c.gridy = 0;
		add(panelFourth, c);
	}

}
