package chalmers.dax021308.ecosystem.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.view.ControlView;

public class ControlViewController implements IController {
	public final ControlView view;
	private EcoWorld model;
	
	public ControlViewController(EcoWorld model) {
		this.model = model;
		view = new ControlView(model);
		init();
	}	
	
	
	@Override
	public void init() {
		addActionListeners();		
		view.setVisible(true);
	}
	
	private void addActionListeners() {
		view.playPauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (!view.play) {
					try {
						model.start();
					} catch (IllegalStateException ex) {
						Log.v("EcoWorld already started");
					}
					view.play = true;
					view.playPauseButton.setIcon(new ImageIcon("res/pause.png"));
					view.playPauseButton.setToolTipText("Pause");
				} else {
					try {
						model.pause();
					} catch (IllegalStateException ex) {
						Log.v("EcoWorld already paused");
					}
					view.play = false;
					view.playPauseButton.setIcon(new ImageIcon("res/play.png"));
					view.playPauseButton.setToolTipText("Play");
				}
			}
		});
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setModel(IModel m) {
		// TODO Auto-generated method stub
		
	}
}
