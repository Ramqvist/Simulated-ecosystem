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
 * @author original MainWindow class by Hanna, edits by Erik Ramqvist
 *
 */

public class MapEditorView extends JFrame implements IView {
	private static final long serialVersionUID = -8023217907757L;
	private JPanel contentPane;
	public final JPanel left = new JPanel();
	public final JPanel right = new JPanel();
	private MapEditorGLView openGL;
	
	public final JMenuBar menuBar;
	public final JMenu mnFile;
	public final JMenuItem mntmImport;
	public final JMenuItem mntmExport;
	public final JMenuItem mntmNew;
	public final JMenuItem mntmExit;
	public final JMenuItem mntmSave;
	public final JMenuItem mntmSaveAs;
	public final JMenuItem mntmLoad;

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
		
		menuBar = new JMenuBar();
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		mntmNew = new JMenuItem("New");
		mnFile.add(mntmNew);
		mntmSave = new JMenuItem("Save");
		mnFile.add(mntmSave);
		mntmSaveAs = new JMenuItem("Save as...");
		mnFile.add(mntmSaveAs);
		mntmLoad = new JMenuItem("Load...");
		mnFile.add(mntmLoad);
		mnFile.addSeparator();
		mntmImport = new JMenuItem("Import...");
		mnFile.add(mntmImport);
		mntmExport = new JMenuItem("Export...");
		mnFile.add(mntmExport);
		mnFile.addSeparator();
		mntmExit = new JMenuItem("Exit");
		mnFile.add(mntmExit);
		setJMenuBar(menuBar);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		left.setLayout(new BorderLayout(0,0));
	
		right.setLayout(new GridLayout(3,1));
		right.setMinimumSize(new Dimension(500, 400));
		right.setPreferredSize(new Dimension(500, 400));
		setContentPane(contentPane);

		left.add(openGL, BorderLayout.CENTER);
		
		contentPane.add(left, BorderLayout.CENTER);
		contentPane.add(right, BorderLayout.EAST);
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
	
	
}
