package chalmers.dax021308.ecosystem.view.mapeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.io.File;


import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileFilter;  

import chalmers.dax021308.ecosystem.controller.ControlViewController;
import chalmers.dax021308.ecosystem.controller.LiveSettingsViewController;
import chalmers.dax021308.ecosystem.controller.NEWSettingsMenuViewController;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.view.IView;
import chalmers.dax021308.ecosystem.view.OpenGLSimulationView;

/**
 * Map editor view
 * 
 * @author original MainWindow by Hanna, edits by Erik Ramqvist
 *
 */

public class MapEditorView extends JFrame implements IView {
	private static final long serialVersionUID = -8023217907757L;
	private JPanel contentPane;
	private JPanel left = new JPanel();
	private JPanel right = new JPanel();
	private MapEditorGLView openGL;
	
	public final LiveSettingsViewController parameterViewCtrl; 
	public final NEWSettingsMenuViewController smvc;
	
	public final JMenuBar menuBar;
	public final JMenu mnFile;
	public final JMenuItem mntmLoad;
	public final JMenuItem mntmSave;
	public final JMenuItem mntmNew;
	public final JMenuItem mntmExit;

	/**
	 * Create the frame.
	 */
	public MapEditorView(final IModel model) {
		model.addObserver(this);
		setTitle("Map Editor");
		setIconImage(new ImageIcon("res/Simulated ecosystem icon.png").getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
	    Dimension d = new Dimension(1000, 1000);
		openGL = new MapEditorGLView(model, d);
		openGL.init();
		EcoWorld fakeEcoWOrld = new EcoWorld();
		parameterViewCtrl = new LiveSettingsViewController(fakeEcoWOrld);
		smvc = new NEWSettingsMenuViewController(fakeEcoWOrld);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);

		mntmNew = new JMenuItem("New map");
		mnFile.add(mntmNew);
		
		mntmLoad = new JMenuItem("Load map");
		

		
		mntmLoad.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser  fc = new JFileChooser();
				fc.setFileFilter(new MapFileFilter());
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int ret = fc.showOpenDialog(MapEditorView.this);
				if(ret == JFileChooser.APPROVE_OPTION) {
					File selectedFile = fc.getSelectedFile();
					if(selectedFile != null) {
						//TODO: IMplement load file.
					}
				}
			}
		});
		mnFile.add(mntmLoad);
		
		mntmSave = new JMenuItem("Save map");
		mnFile.add(mntmSave);
	
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		
		
		//TODO: MOve this to controller.
		mntmSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser  fc = new JFileChooser();
				fc.setFileFilter(new MapFileFilter());
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				int ret = fc.showSaveDialog(MapEditorView.this);
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
		

		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		left.setLayout(new BorderLayout(0,0));
		
		/*
		 * Quick fix...
		 */
		right.setLayout(new GridLayout(3,1));
		right.setMinimumSize(new Dimension(500, 400));
		right.setPreferredSize(new Dimension(500, 400));
		
		AddObstacleView addObstacleView = new AddObstacleView(model);
		right.add(addObstacleView);
		EditObstaclesView editObstacles = new EditObstaclesView(model);
		right.add(editObstacles);
		setContentPane(contentPane);

		left.add(openGL, BorderLayout.CENTER);
//		left.add(parameterViewCtrl.view, BorderLayout.NORTH);
//		left.add(controlViewCtrl.view, BorderLayout.SOUTH);  
		
		contentPane.add(left, BorderLayout.CENTER);
		contentPane.add(right, BorderLayout.EAST);
		addWindowListener(new WindowAdapter() {			
			@Override
			public void windowClosing(WindowEvent arg0) {
			}
		});
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == MapEditorModel.EVENT_MAPNAME_CHANGED) {
			if(evt.getNewValue() != null && evt.getNewValue() instanceof String) {
				String str = (String) evt.getNewValue();
				if(str.isEmpty()) {
					setTitle("Map Editor") ;
				} else {
					setTitle("Map Editor - " + str) ;
				}
			}
		}
	}

	@Override
	public void init() {
	}

	@Override
	public void addController(ActionListener controller) {
	}

	@Override
	public void onTick() {
	}

	@Override
	public void release() {
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
