package chalmers.dax021308.ecosystem.controller.mapeditor;

import chalmers.dax021308.ecosystem.controller.IController;
import chalmers.dax021308.ecosystem.controller.mapeditor.NewMapDialogController.OnNameSelectedListener;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.view.mapeditor.MapEditorView;


/**
 * Controller class for the Map Editor system.
 * 
 * @author Erik
 *
 */
public class MapEditorController implements IController {
	
	private final MapEditorView view;
	private final MapEditorModel model;
	private OnNameSelectedListener nameSelectedListener = new OnNameSelectedListener() {
		@Override
		public void onSelectedName(String name) {
			model.createNewMap(name);
		}
	};

	public MapEditorController() {
		model = new MapEditorModel();
		view = new MapEditorView(model);
		view.setVisible(true);
		new NewMapDialogController(view, nameSelectedListener);
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
