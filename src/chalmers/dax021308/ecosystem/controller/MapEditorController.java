package chalmers.dax021308.ecosystem.controller;

import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.MapEditorModel;
import chalmers.dax021308.ecosystem.view.MapEditorView;

public class MapEditorController implements IController {
	
	private final MapEditorView view;
	private final MapEditorModel model;

	public MapEditorController() {
		model = new MapEditorModel();
		view = new MapEditorView(model);
		view.setVisible(true);
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
