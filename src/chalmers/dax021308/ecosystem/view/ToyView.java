package chalmers.dax021308.ecosystem.view;

import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Observable;

import javax.swing.JFrame;

import chalmers.dax021308.ecosystem.model.EcoWorld;
import chalmers.dax021308.ecosystem.model.IObstacle;
import chalmers.dax021308.ecosystem.model.IPopulation;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;

/**
 * "Toy" view, only for getting MVC structure running. Also for use as template.
 * 
 * @author Erik Ramqvist
 *
 */
public class ToyView implements IView {
	
	//Holding a reference to the model might not be necessary.
	private EcoWorld model;
	private JFrame frame;
	
	public ToyView(EcoWorld model) {
		this.model = model;
		model.addObserver(this);
		frame = new JFrame("");
		frame.setSize(600, 400);
		
		JLabel lblHelloWorld = new JLabel("Hello world!");
		GroupLayout groupLayout = new GroupLayout(frame.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(208)
					.addComponent(lblHelloWorld)
					.addContainerGap(220, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(132)
					.addComponent(lblHelloWorld)
					.addContainerGap(163, Short.MAX_VALUE))
		);
		frame.getContentPane().setLayout(groupLayout);
	}


	@Override
	public void init() {
		frame.setVisible(true);
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
		if(frame.isVisible()) {
			frame.setVisible(false);
		}
		frame.removeAll();
		frame = null;
		System.gc();
	}


	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String eventName = event.getPropertyName();
		if(eventName == EcoWorld.EVENT_STOP) {
			//Model has stopped. Maybe hide view?
			frame.setVisible(false);
		} else if(eventName == EcoWorld.EVENT_TICK) {
			//Tick notification recived from model. Do something with the data.
			if(event.getNewValue() instanceof List<?>) {
				List<IPopulation> newPops = (List<IPopulation>) event.getNewValue();
			}
			if(event.getOldValue() instanceof List<?>) {
				List<IObstacle> newObs = (List<IObstacle>) event.getOldValue();
			}
		}
	}
}
