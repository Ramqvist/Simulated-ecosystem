package chalmers.dax021308.ecosystem.view;

import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.border.EmptyBorder;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.util.Log;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

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
	
	public final JButton buttonStartNew;
	public final JButton buttonPlay;
	public final JButton buttonStop;
	public final JButton buttonPause;
	
	private JPanel panelButton;
	private JPanel panelButtonPlay;
	private JPanel panelButtonStop;
	private JPanel panelButtonPause;

	/**
	 * Create the panel.
	 */
	public ControlView(EcoWorld model) {  
		
		buttonPlay = new JButton(new ImageIcon("res/play.png"));
		buttonPlay.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonStop = new JButton(new ImageIcon("res/stop.png"));
		buttonPause = new JButton(new ImageIcon("res/pause.png"));
		buttonPause.setFont(new Font("Tahoma", Font.PLAIN, 14));
		buttonStartNew = new JButton("Start new Simulation");
		buttonStartNew.setFont(new Font("Tahoma", Font.PLAIN, 14));
		
		panelButton = new JPanel();
		panelButtonPause = new JPanel();
		panelButtonStop = new JPanel();
		panelButtonPlay = new JPanel();
		
		
		buttonPlay.setMaximumSize(new Dimension(30, 30));
		buttonStop.setMaximumSize(new Dimension(30, 30));
		buttonPause.setMaximumSize(new Dimension(30, 30));
		buttonPlay.setMinimumSize(new Dimension(30, 30));
		buttonStop.setMinimumSize(new Dimension(30, 30));
		buttonPause.setMinimumSize(new Dimension(30, 30));	
		/*
		panelButtonPlay.setMaximumSize(new Dimension(30, 30));
		panelButtonStop.setMaximumSize(new Dimension(30, 30));
		panelButtonPause.setMaximumSize(new Dimension(30, 30));
		panelButtonPlay.setMinimumSize(new Dimension(30, 30));
		panelButtonStop.setMinimumSize(new Dimension(30, 30));
		panelButtonPause.setMinimumSize(new Dimension(30, 30));
		*/
		
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		panelButton.setLayout(new GridBagLayout());
		
		panelButtonPlay.setLayout(new GridBagLayout());
		panelButtonStop.setLayout(new GridBagLayout());
		panelButtonPause.setLayout(new GridBagLayout());
		
		panelButton.setBorder(new EmptyBorder(0, 0, 0, 150));
		/*
		panelButtonPlay.setBorder(new EmptyBorder(0, 2, 0, 0));
		panelButtonStop.setBorder(new EmptyBorder(0, 2, 0, 0));
		panelButtonPause.setBorder(new EmptyBorder(0, 2, 0, 0));
		*/
		
		GridBagConstraints c = new GridBagConstraints();
		
		/*
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.5;
        //c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
		panelButtonPlay.add(buttonPlay);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.5;
        //c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
		panelButtonStop.add(buttonStop);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.5;
        //c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
		panelButtonPause.add(buttonPause);
		*/
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.5;
        //c.weighty = 0.5;
        c.gridx = 0;
        c.gridy = 0;
		panelButton.add(buttonPlay, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.5;
        //c.weighty = 0.5;
        c.gridx = 1;
        c.gridy = 0;
		panelButton.add(buttonPause, c);
		
		c = new GridBagConstraints();
		c.fill = GridBagConstraints.VERTICAL;
		c.weightx = 0.5;
        //c.weighty = 0.5;
        c.gridx = 2;
        c.gridy = 0;		
		panelButton.add(buttonStop, c);
		
		add(panelButton);		
		add(buttonStartNew);
	}

}
