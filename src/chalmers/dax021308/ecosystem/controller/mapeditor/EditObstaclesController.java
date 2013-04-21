package chalmers.dax021308.ecosystem.controller.mapeditor;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chalmers.dax021308.ecosystem.controller.IController;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.view.mapeditor.EditObstaclesView;

/**
 * Controller class for EditObstacles panel.
 * 
 * @author Erik Ramqvist
 *
 */
public class EditObstaclesController implements IController {
	
	public final EditObstaclesView view;
	
	public EditObstaclesController(final MapEditorModel m) {
		view = new EditObstaclesView(m);
		view.btnDelete.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				IObstacle selectedObstacle = view.obstaclesJList.getSelectedValue();
				if(selectedObstacle != null) {
					m.removeObstacle(selectedObstacle);
				}
			}
		});
	}
	
	@Override
	public void init() {
		// TODO Auto-generated method stub
		
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
