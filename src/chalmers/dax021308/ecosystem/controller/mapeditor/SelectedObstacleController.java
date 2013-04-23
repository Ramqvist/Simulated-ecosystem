package chalmers.dax021308.ecosystem.controller.mapeditor;

import chalmers.dax021308.ecosystem.controller.IController;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.view.mapeditor.SelectedObstacleView;

public class SelectedObstacleController implements IController {
	public final SelectedObstacleView view;
	
	public SelectedObstacleController(MapEditorModel model) {
		view = new SelectedObstacleView(model);
	}

	@Override
	public void init() {
		
	}

	@Override
	public void release() {
		
	}

	@Override
	public void setModel(IModel m) {
		
	}
	
}
