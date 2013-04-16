package chalmers.dax021308.ecosystem.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import chalmers.dax021308.ecosystem.controller.ControlViewController;
import chalmers.dax021308.ecosystem.controller.LiveSettingsViewController;
import chalmers.dax021308.ecosystem.controller.NEWSettingsMenuViewController;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.view.chart.AbstractGraph2D;
import chalmers.dax021308.ecosystem.view.chart.IterationTimeGraph;
import chalmers.dax021308.ecosystem.view.chart.PopulationAmountGraph;

/**
 * The view that holds the entire application.
 * 
 * @author Hanna
 *
 */

public class MainWindow extends JFrame implements IView {
	private static final long serialVersionUID = -8023060073777907757L;
	private JPanel contentPane;
//	private JPanel simulationPanel = new JPanel();
	private JPanel left = new JPanel();
	private JPanel right = new JPanel();
	private AWTSimulationView awt;
	private OpenGLSimulationView openGL;
	private HeatMapView heatMap;
	private AbstractGraph2D graphView1;
	private AbstractGraph2D graphView2;
	
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
	public MainWindow(final EcoWorld model) {
		setTitle("Simulated Ecosystem");
		setIconImage(new ImageIcon("res/Simulated ecosystem icon.png").getImage());
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 613, 516);
		this.setExtendedState(MAXIMIZED_BOTH);
		//OpenGL   
	    Dimension d = model.getSize();
		openGL = new OpenGLSimulationView(model, d, true);
		openGL.init();
		//openGL.setSize(new Dimension(980,700));
		heatMap = new HeatMapView(model, d, 11, "Deers");
		controlViewCtrl = new ControlViewController(model);
		parameterViewCtrl = new LiveSettingsViewController(model);
		graphView1 = new PopulationAmountGraph(model, 10);
		graphView2 = new IterationTimeGraph(model, 10);
		smvc = new NEWSettingsMenuViewController(model);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmLoad = new JMenuItem("Load simulation");
		
		mnFile.add(mntmLoad);
		
		mntmSave = new JMenuItem("Save simulation");
		mnFile.add(mntmSave);
	
		mntmExit = new JMenuItem("Exit");

		
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
//		GridBagConstraints gbc = new GridBagConstraints();
//		gbc.gridx = 0;
//		gbc.gridwidth = gbc.gridheight = 1;
//		gbc.fill = GridBagConstraints.HORIZONTAL; 
//		gbc.gridy = GridBagConstraints.RELATIVE; //makes sure every new add is placed beneath the previous
//		gbc.anchor = GridBagConstraints.PAGE_START;
		setContentPane(contentPane);
		
//		simulationPanel.setSize(d);
		//simulationPanel.add(openGL);
//		simulationPanel.setBackground(Color.RED);
		left.add(parameterViewCtrl.view, BorderLayout.NORTH);
		left.add(openGL, BorderLayout.CENTER);
		left.add(controlViewCtrl.view, BorderLayout.SOUTH);  
		//right.add(parameterView, BorderLayout.CENTER);
		//graphView1.setMinimumSize(new Dimension(500, 400));
		//graphView1.setPreferredSize(new Dimension(500, 400));
		right.add(graphView2, BorderLayout.CENTER); // during development.
		right.add(graphView1, BorderLayout.CENTER);
		right.add(heatMap, BorderLayout.CENTER); 
//		right.setBackground(Color.BLUE);
//		parameterView.setBackground(Color.GREEN);
		
		contentPane.add(left, BorderLayout.CENTER);
		contentPane.add(right, BorderLayout.EAST);
		addWindowListener(new WindowAdapter() {			
			@Override
			public void windowClosing(WindowEvent arg0) {
				//Try to shutdown all worker threads.
				model.shutdownNow();
			}
		});
		//contentPane.add(graphView2);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addController(ActionListener controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}
	
	public void setSimulationPanel(int i) {
		if(i == 0) {
//			simulationPanel.add(awt);
		}
		else if(i == 1) {
//			simulationPanel.add(openGL);
		}
	}

	public void setBtnStartNewSimWindowActionListener(ActionListener a) {
		controlViewCtrl.view.btnStartNew.addActionListener(a);		
	}
}
