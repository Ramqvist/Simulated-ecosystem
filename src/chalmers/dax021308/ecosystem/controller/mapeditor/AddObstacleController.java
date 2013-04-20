package chalmers.dax021308.ecosystem.controller.mapeditor;

import chalmers.dax021308.ecosystem.controller.IController;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.view.mapeditor.AddObstacleView;

public class AddObstacleController implements IController {
	
	private final AddObstacleView view;
	
	public AddObstacleController(MapEditorModel m) {
		view = new AddObstacleView(m);
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
