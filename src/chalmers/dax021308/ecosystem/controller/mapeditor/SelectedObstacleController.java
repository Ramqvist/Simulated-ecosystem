package chalmers.dax021308.ecosystem.controller.mapeditor;


import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import chalmers.dax021308.ecosystem.controller.IController;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.ObstacleColorContainer;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.view.mapeditor.SelectedObstacleView;

public class SelectedObstacleController implements IController {
	public final SelectedObstacleView view;
	private MapEditorModel model;
	
	public SelectedObstacleController(MapEditorModel model) {
		this.model = model;
		view = new SelectedObstacleView(model);
		init();
	}

	@Override
	public void init() {
		view.sliderWidth.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = view.sliderWidth.getValue();
				model.getSelectedObstacle().setWidth(value);
				view.tbxWidth.setText(value + "");
			}
		});
		view.sliderHeight.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = view.sliderHeight.getValue();
				model.getSelectedObstacle().setHeight(value);
				view.tbxHeight.setText(value + "");
			}
		});
		view.sliderAngle.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				int value = view.sliderAngle.getValue();
				model.getSelectedObstacle().setAngle(view.getUnconvertedRadians(value));
				view.tbxAngle.setText(view.getUnconvertedRadians(value) + "");
			}
		});
		view.colorList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				ObstacleColorContainer colorcontainer = view.colorList.getSelectedValue();
				if(colorcontainer != null) {
					IObstacle s = model.getSelectedObstacle();
					if( s != null) {
						s.setColor(colorcontainer.c);
					}
				}
			}
		});
	}

	@Override
	public void release() {
		
	}

	@Override
	public void setModel(IModel m) {
		
	}
	
}
