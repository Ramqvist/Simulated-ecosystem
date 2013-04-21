package chalmers.dax021308.ecosystem.controller.mapeditor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

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
	private final EditObstaclesController editObstacle;
	
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
		editObstacle = new EditObstaclesController(model);
		view.right.add(addObstacle.view);
		view.right.add(editObstacle.view);
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
		view.mntmSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser  fc = new JFileChooser();
				fc.setFileFilter(new MapFileFilter());
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int ret = fc.showSaveDialog(view);
				if(ret == JFileChooser.APPROVE_OPTION) {
					File savedFileAs = fc.getSelectedFile();
					String filePath = savedFileAs.getPath();
					if(!filePath.toLowerCase().endsWith(".map")) {
						savedFileAs = new File(filePath + ".map");
					}
					//TODO: IMplement saving maps.
				}
			}
		});

		view.mntmLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser  fc = new JFileChooser();
				fc.setFileFilter(new MapFileFilter());
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int ret = fc.showOpenDialog(view);
				if(ret == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					if(selectedFile != null) {
						//TODO: IMplement load file.
					}
				}
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
	

	private class MapFileFilter extends FileFilter {
		@Override
		public boolean accept(File f) {
			  return f.isDirectory() || f.getName().toLowerCase().endsWith(".map");  
		}
		@Override
		public String getDescription() {
			  return ".map files"; 
		}
	};
	
}
