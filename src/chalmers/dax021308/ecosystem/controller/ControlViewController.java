package chalmers.dax021308.ecosystem.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

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
				} else {
					try {
						model.pause();
					} catch (IllegalStateException ex) {
						Log.v("EcoWorld already paused");
					}
				}
			}
		});
		view.iterationDelaySpinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				Integer i = (Integer) view.iterationDelaySpinner.getValue();
				model.setDelayLength(i);
				Log.e(i + "");
			}
		});
	}

	@Override
	public void release() {
		view.release();
	}

	@Override
	public void setModel(IModel m) {
		// TODO Auto-generated method stub
		
	}
}
