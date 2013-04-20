package chalmers.dax021308.ecosystem.controller.mapeditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import chalmers.dax021308.ecosystem.controller.IController;
import chalmers.dax021308.ecosystem.controller.mapeditor.NewMapDialogController.OnNameSelectedListener;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.util.Log;
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
	
	private final AddObstacleController addObstacle;
	
	private final OnNameSelectedListener nameSelectedListener = new OnNameSelectedListener() {
		@Override
		public void onSelectedName(String name) {
			model.createNewMap(name);
		}
	};
	
	private final ObstacleInjectListener obstacleListener = new ObstacleInjectListener() {
		@Override
		public void onObstacleAdd(IObstacle o) {
			if(o != null) {
				model.addObstacle(o);
			}
		}
	};

	public MapEditorController() {
		model = new MapEditorModel();
		view = new MapEditorView(model);
		addObstacle = new AddObstacleController(model, obstacleListener);
		view.right.add(addObstacle.view);
		view.setVisible(true);
		init();
	}
	
	
	@Override
	public void init() {
		showSelectNameDialog();
		view.mntmNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showSelectNameDialog();
			}
		});
	}

	private void showSelectNameDialog() {
		new NewMapDialogController(view, nameSelectedListener);
	}
	
	@Override
	public void release() {
		
	}

	@Override
	public void setModel(IModel m) {
		
	}
	
	public interface ObstacleInjectListener {
		public void onObstacleAdd(IObstacle o);
	}
	
}
