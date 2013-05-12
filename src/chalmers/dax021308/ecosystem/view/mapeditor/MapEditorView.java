package chalmers.dax021308.ecosystem.view.mapeditor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;


import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapEditorModel;
import chalmers.dax021308.ecosystem.view.IView;
import net.miginfocom.swing.MigLayout;

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
	public JLayeredPane mainPanel = new JLayeredPane();
	
	public final MenuBar menuBar;
	public final Menu mnFile;
	public final MenuItem mntmImport;
	public final MenuItem mntmExport;
	public final MenuItem mntmNew;
	public final MenuItem mntmExit;
	public final MenuItem mntmSave;
	public final MenuItem mntmSaveAs;
	public final MenuItem mntmLoad;

	/**
	 * Create the frame.
	 */
	public MapEditorView(final IModel model) {
		model.addObserver(this);
		setTitle("Map Editor");
		setIconImage(new ImageIcon("res/Simulated ecosystem icon.png").getImage());
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setExtendedState(MAXIMIZED_BOTH);
	    setMinimumSize(new Dimension(500, 500));
		
		menuBar = new MenuBar();
		mnFile = new Menu("File");
		menuBar.add(mnFile);
		mntmNew = new MenuItem("New");
		mnFile.add(mntmNew);
		mntmSave = new MenuItem("Save");
		mnFile.add(mntmSave);
		mntmSaveAs = new MenuItem("Save as...");
		mnFile.add(mntmSaveAs);
		mntmLoad = new MenuItem("Load...");
		mnFile.add(mntmLoad);
		mnFile.addSeparator();
		mntmImport = new MenuItem("Import...");
		mnFile.add(mntmImport);
		mntmExport = new MenuItem("Export...");
		mnFile.add(mntmExport);
		mnFile.addSeparator();
		mntmExit = new MenuItem("Exit");
		mnFile.add(mntmExit);
		setMenuBar(menuBar);
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		left.setLayout(new BorderLayout(0,0));
		setContentPane(contentPane);
		contentPane.add(left, BorderLayout.CENTER);
		contentPane.add(right, BorderLayout.EAST);
		right.setLayout(new MigLayout("", "[grow]", "[grow][grow][grow]"));
		
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
	public void release() {
	}
	
	
}
