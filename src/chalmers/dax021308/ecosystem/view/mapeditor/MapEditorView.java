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
	private OpenGLSimulationView openGL;
	
	public final LiveSettingsViewController parameterViewCtrl; 
	public final ControlViewController controlViewCtrl;
	public final NEWSettingsMenuViewController smvc;
	
	public final JMenuBar menuBar;
	public final JMenu mnFile;
	public final JMenuItem mntmLoad;
	public final JMenuItem mntmSave;
	public final JMenuItem mntmExit;
	public final JMenu mnControls;
	public final JMenuItem mntmStart;
	public final JMenuItem mntmStop;
	public final JMenuItem mntmPause;
	public final JMenu mnSettings;
	public final JMenuItem mntmSimulationSettings;

	/**
	 * Create the frame.
	 */
	public MapEditorView(final IModel model) {
		setTitle("Map Editor");
		setIconImage(new ImageIcon("res/Simulated ecosystem icon.png").getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setExtendedState(MAXIMIZED_BOTH);
		//OpenGL   
	    Dimension d = new Dimension(1000, 1000);
		openGL = new OpenGLSimulationView(model, d, true);
		openGL.init();
		EcoWorld fakeEcoWOrld = new EcoWorld();
		controlViewCtrl = new ControlViewController(fakeEcoWOrld);
		parameterViewCtrl = new LiveSettingsViewController(fakeEcoWOrld);
		smvc = new NEWSettingsMenuViewController(fakeEcoWOrld);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
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
		
		//TODO: MOve this to controller.
		mntmSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser  fc = new JFileChooser();
				fc.setFileFilter(new MapFileFilter());
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				//File selectedFile = null;//Get file from somewhere.
				//fc.setSelectedFile(selectedFile);
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
		

		
		mnFile.add(mntmExit);
		
		mnControls = new JMenu("Controls");
		menuBar.add(mnControls);
		
		mntmStart = new JMenuItem("Start");
		mnControls.add(mntmStart);
		
		mntmStop = new JMenuItem("Stop");
		mnControls.add(mntmStop);
		
		mntmPause = new JMenuItem("Pause");
		mnControls.add(mntmPause);
		
		mnSettings = new JMenu("Settings");
		menuBar.add(mnSettings);
		
		mntmSimulationSettings = new JMenuItem("Simulation settings");
		
		mntmSimulationSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				smvc.init();
			}
		});
		mnSettings.add(mntmSimulationSettings);
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
		setContentPane(contentPane);
		
		left.add(parameterViewCtrl.view, BorderLayout.NORTH);
		left.add(openGL, BorderLayout.CENTER);
		left.add(controlViewCtrl.view, BorderLayout.SOUTH);  
		
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
	

	public void setBtnStartNewSimWindowActionListener(ActionListener a) {
		controlViewCtrl.view.btnStartNew.addActionListener(a);		
	}
	
	private class MapFileFilter extends FileFilter{
		
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
