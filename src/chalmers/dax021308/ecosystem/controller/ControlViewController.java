package chalmers.dax021308.ecosystem.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
		view.btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					model.start();
				} catch (IllegalStateException ex) {
					Log.v("EcoWorld already stopped");
				}
			}
		});
		
		view.btnStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model.stop();
				} catch (IllegalStateException ex) {
					Log.v("EcoWorld already stopped");
				}
			}
		});
		
		view.btnPause.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					model.pause();
				} catch (IllegalStateException ex) {
					//Don't care.
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
