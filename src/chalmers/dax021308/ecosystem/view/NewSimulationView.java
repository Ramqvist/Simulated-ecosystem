package chalmers.dax021308.ecosystem.view;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.util.Log;

import java.awt.Dimension;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JToggleButton;
import javax.swing.JCheckBox;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.AbstractListModel;
import javax.swing.JRadioButton;
import javax.swing.ListSelectionModel;

/**
 * Window for starting a new simulation.
 * 
 * @author Erik
 * 
 */
public class NewSimulationView {

	private JFrame frmSimulatedEcosystem;
	private JTextField textfield_Iterationdelay;
	private JTextField tvPredPopSize;
	private JTextField tvPreyPopSize;
	private JTextField tvGrassPopSize;
	private EcoWorld model;
	private JList predList = new JList();;
	private JList preyList = new JList();;
	private JList grassList = new JList();
	private JCheckBox chckbxRecordSimulation;
	private JTextField tvNumIterations;
	private JList listSimulationDim;
	private JCheckBox checkBoxLimitIterations;
	private JRadioButton rdbtn2Threads;
	private JRadioButton rdbtn4Threads;
	private JRadioButton rdbtn8Threads;
	private JRadioButton rdbtnSquare;
	private JRadioButton rdbtnCircle;
	private JRadioButton rdbtnTriangle;


	/**
	 * Create the application.
	 * 
	 * @param model
	 */
	public NewSimulationView(EcoWorld model) {
		this.model = model;
		initialize();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frmSimulatedEcosystem.setLocation(
				dim.width / 2 - frmSimulatedEcosystem.getSize().width / 2,
				dim.height / 2 - frmSimulatedEcosystem.getSize().height / 2);
		frmSimulatedEcosystem.setVisible(true);
	}

	private void startSimulation() {
		try {
			model.setNumIterations(Integer.MAX_VALUE);
			model.setSimulationDimension((String) listSimulationDim
					.getSelectedValue());
			int tickDelay = Integer
					.parseInt(textfield_Iterationdelay.getText());

			if (rdbtn2Threads.isSelected()) {
				model.setNumThreads(2);
			} else if (rdbtn4Threads.isSelected()) {
				model.setNumThreads(4);
			} else {
				model.setNumThreads(8);
			}

			if (tickDelay < 1) {
				model.setRunWithoutTimer(true);
			} else {
				model.setRunWithoutTimer(false);
				model.setDelayLength(tickDelay);
			}
			if (checkBoxLimitIterations.isSelected()) {
				int iterations = Integer.parseInt(tvNumIterations.getText());
				model.setNumIterations(iterations);
			} else {
				model.setNumIterations(Integer.MAX_VALUE);
			}
			model.setRecordSimulation(chckbxRecordSimulation.isSelected());
			// Should the shape really be set here?
			// TODO fix an input value for shape and not just a squareshape
			String shape = null;
			if(rdbtnCircle.isSelected()) {
				shape = EcoWorld.SHAPE_CIRCLE;
			} else if (rdbtnSquare.isSelected()){
				shape = EcoWorld.SHAPE_SQUARE;
			} else {
				shape = EcoWorld.SHAPE_TRIANGLE;
			}
			model.createInitialPopulations(
					(String) predList.getSelectedValue(),
					Integer.parseInt(tvPredPopSize.getText()),
					(String) preyList.getSelectedValue(),
					Integer.parseInt(tvPreyPopSize.getText()),
					(String) grassList.getSelectedValue(),
					Integer.parseInt(tvGrassPopSize.getText()), shape);
			try {
				model.start();
			} catch (IllegalStateException e) {
				Log.v(e.toString());
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(frmSimulatedEcosystem,
					"Something didnt go quite well there. Have some coffee.");
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmSimulatedEcosystem = new JFrame();
		frmSimulatedEcosystem.setResizable(false);
		frmSimulatedEcosystem.setAlwaysOnTop(true);
		frmSimulatedEcosystem.setTitle("Start new Simulation");
		frmSimulatedEcosystem.setIconImage(new ImageIcon("res/Simulated ecosystem icon.png").getImage());
		frmSimulatedEcosystem.setBounds(100, 100, 638, 663);
		frmSimulatedEcosystem.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JButton btnRunSim = new JButton("Start new");
		btnRunSim.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				startSimulation();
				frmSimulatedEcosystem.dispose();
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				frmSimulatedEcosystem.dispose();
			}
		});

		JLabel lblPrededators = new JLabel("Prededators");
		lblPrededators.setFont(new Font("Tahoma", Font.PLAIN, 14));

		final JSlider slider_delaylength = new JSlider();
		slider_delaylength.setValue(16);
		slider_delaylength.setSnapToTicks(true);
		slider_delaylength.setPaintTicks(true);
		slider_delaylength.setPaintLabels(true);

		textfield_Iterationdelay = new JTextField();
		textfield_Iterationdelay.setText("16");
		textfield_Iterationdelay.setColumns(10);

		final JLabel lblIterationDelay = new JLabel("Iteration delay");
		slider_delaylength.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				textfield_Iterationdelay.setText(slider_delaylength.getValue()
						+ "");
			}
		});
		predList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		predList.setValueIsAdjusting(true);
		predList.setSelectedIndices(new int[] { 3 });
		predList.setModel(new AbstractListModel() {
			String[] values = EcoWorld.PRED_VALUES;

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		predList.setSelectedIndex(0);
		preyList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		preyList.setValueIsAdjusting(true);
		preyList.setSelectedIndices(new int[] { 3 });
		preyList.setModel(new AbstractListModel() {
			String[] values = EcoWorld.PREY_VALUES;

			public int getSize() {
				return values.length;
			}

			public Object getElementAt(int index) {
				return values[index];
			}
		});
		preyList.setSelectedIndex(0);

		JLabel lblPreys = new JLabel("Preys");
		lblPreys.setFont(new Font("Tahoma", Font.PLAIN, 14));
		grassList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		grassList.setValueIsAdjusting(true);
		grassList.setSelectedIndices(new int[] { 3 });
		grassList.setModel(new AbstractListModel() {
			public int getSize() {
				return EcoWorld.GRASS_VALUES.length;
			}

			public Object getElementAt(int index) {
				return EcoWorld.GRASS_VALUES[index];
			}
		});
		grassList.setSelectedIndex(0);

		JLabel lblVegatablePopulation = new JLabel("Vegatable population");
		lblVegatablePopulation.setFont(new Font("Tahoma", Font.PLAIN, 14));

		tvPredPopSize = new JTextField();
		tvPredPopSize.setText("10");
		tvPredPopSize.setColumns(10);

		tvPreyPopSize = new JTextField();
		tvPreyPopSize.setText("100");
		tvPreyPopSize.setColumns(10);

		tvGrassPopSize = new JTextField();
		tvGrassPopSize.setText("400");
		tvGrassPopSize.setColumns(10);

		JLabel lblInitialSize = new JLabel("Initial size");

		JLabel label = new JLabel("Initial size");

		JLabel label_1 = new JLabel("Initial size");

		JRadioButton rdbtnNewRadioButton = new JRadioButton(
				"Use OpenGLSimulationView");
		rdbtnNewRadioButton.setSelected(true);

		chckbxRecordSimulation = new JCheckBox("Record simulation");

		final JSlider sliderPreySize = new JSlider();
		sliderPreySize.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				tvPreyPopSize.setText(sliderPreySize.getValue() + "");
			}
		});
		sliderPreySize.setMaximum(400);
		sliderPreySize.setValue(100);

		final JSlider sliderGrassSize = new JSlider();
		sliderGrassSize.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				tvGrassPopSize.setText(sliderGrassSize.getValue() + "");
			}
		});
		sliderGrassSize.setMaximum(1200);
		sliderGrassSize.setValue(400);

		final JSlider sliderPredSize = new JSlider();
		sliderPredSize.setValue(10);
		sliderPredSize.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				tvPredPopSize.setText(sliderPredSize.getValue() + "");
			}
		});

		final JLabel lblSimulationIteration = new JLabel(
				"Simulation iterations");
		lblSimulationIteration.setEnabled(false);

		tvNumIterations = new JTextField();
		tvNumIterations.setEnabled(false);
		tvNumIterations.setText("10000");
		tvNumIterations.setColumns(10);
		final JSlider sliderNumIterations = new JSlider();
		sliderNumIterations.setEnabled(false);
		sliderNumIterations.setMinimum(1);
		sliderNumIterations.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				tvNumIterations.setText(sliderNumIterations.getValue() + "");
			}
		});
		sliderNumIterations.setMaximum(20000);
		sliderNumIterations.setValue(10000);
		sliderNumIterations.setSnapToTicks(true);
		sliderNumIterations.setPaintTicks(true);
		sliderNumIterations.setPaintLabels(true);

		listSimulationDim = new JList();
		listSimulationDim.setValueIsAdjusting(true);
		listSimulationDim.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listSimulationDim.setSelectedIndices(new int[] { 3 });
		listSimulationDim.setModel(new AbstractListModel() {
			public int getSize() {
				return EcoWorld.DIM_VALUES.length;
			}

			public Object getElementAt(int index) {
				return EcoWorld.DIM_VALUES[index];
			}
		});
		listSimulationDim.setSelectedIndex(1);

		JLabel lblSimulationDimension = new JLabel("Simulation dimension");
		lblSimulationDimension.setFont(new Font("Tahoma", Font.PLAIN, 14));

		checkBoxLimitIterations = new JCheckBox("Limit number of Iterations");
		checkBoxLimitIterations.setFont(new Font("Tahoma", Font.PLAIN, 14));
		checkBoxLimitIterations.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent ce) {
				if (checkBoxLimitIterations.isSelected()) {
					tvNumIterations.setEnabled(true);
					lblSimulationIteration.setEnabled(true);
					sliderNumIterations.setEnabled(true);
				} else {
					tvNumIterations.setEnabled(false);
					lblSimulationIteration.setEnabled(false);
					sliderNumIterations.setEnabled(false);
				}
			}
		});

		JLabel lblNewLabel = new JLabel("Amount of concurrent worker threads");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));

		rdbtn2Threads = new JRadioButton("2 threads");
		rdbtn2Threads.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtn4Threads.setSelected(false);
				rdbtn8Threads.setSelected(false);
				rdbtn2Threads.setSelected(true);
			}
		});

		rdbtn4Threads = new JRadioButton("4 threads");
		rdbtn4Threads.setSelected(true);
		rdbtn4Threads.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtn2Threads.setSelected(false);
				rdbtn8Threads.setSelected(false);
				rdbtn4Threads.setSelected(true);
			}
		});
		rdbtn8Threads = new JRadioButton("8 threads");
		rdbtn8Threads.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtn2Threads.setSelected(false);
				rdbtn4Threads.setSelected(false);
				rdbtn8Threads.setSelected(true);
			}
		});
		
		JLabel lblNewLabel_1 = new JLabel("Simulation shape");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		
		rdbtnSquare = new JRadioButton("Square");
		rdbtnSquare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnCircle.setSelected(false);
				rdbtnSquare.setSelected(true);
				rdbtnTriangle.setSelected(false);
			}
		});
		
		rdbtnCircle = new JRadioButton("Circle");
		rdbtnCircle.setSelected(true);
		rdbtnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnCircle.setSelected(true);
				rdbtnSquare.setSelected(false);
				rdbtnTriangle.setSelected(false);
			}
		});
		
		rdbtnTriangle = new JRadioButton("Triangle");
		rdbtnTriangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				rdbtnCircle.setSelected(false);
				rdbtnSquare.setSelected(false);
				rdbtnTriangle.setSelected(true);
			}
		});
		GroupLayout groupLayout = new GroupLayout(
				frmSimulatedEcosystem.getContentPane());
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPrededators, GroupLayout.DEFAULT_SIZE, 622, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblPreys, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(grassList, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(sliderGrassSize, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addGroup(groupLayout.createSequentialGroup()
													.addPreferredGap(ComponentPlacement.RELATED)
													.addComponent(label_1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
												.addComponent(tvGrassPopSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
									.addComponent(lblVegatablePopulation, GroupLayout.PREFERRED_SIZE, 155, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(predList, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(lblInitialSize, GroupLayout.DEFAULT_SIZE, 121, Short.MAX_VALUE)
											.addGroup(groupLayout.createSequentialGroup()
												.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
													.addComponent(sliderPredSize, Alignment.LEADING, 0, 0, Short.MAX_VALUE)
													.addComponent(tvPredPopSize, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE))
												.addPreferredGap(ComponentPlacement.RELATED))))
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(preyList, GroupLayout.PREFERRED_SIZE, 118, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addComponent(sliderPreySize, 0, 0, Short.MAX_VALUE)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(label)
												.addPreferredGap(ComponentPlacement.RELATED, 74, Short.MAX_VALUE))
											.addComponent(tvPreyPopSize, 112, 112, 112)))))
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addComponent(checkBoxLimitIterations, GroupLayout.PREFERRED_SIZE, 255, GroupLayout.PREFERRED_SIZE)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(sliderNumIterations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.UNRELATED)
										.addComponent(tvNumIterations, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))
									.addComponent(lblSimulationIteration, GroupLayout.PREFERRED_SIZE, 175, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblNewLabel_1)
									.addComponent(rdbtnSquare)
									.addComponent(rdbtnCircle)
									.addComponent(rdbtnTriangle))
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(rdbtnNewRadioButton)
										.addContainerGap())
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblSimulationDimension, GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
										.addContainerGap())
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(listSimulationDim, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
										.addContainerGap())
									.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
											.addGroup(groupLayout.createSequentialGroup()
												.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 140, GroupLayout.PREFERRED_SIZE)
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(btnRunSim, GroupLayout.DEFAULT_SIZE, 208, Short.MAX_VALUE))
											.addComponent(lblIterationDelay)
											.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
												.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
												.addGroup(groupLayout.createSequentialGroup()
													.addComponent(slider_delaylength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
													.addPreferredGap(ComponentPlacement.UNRELATED)
													.addComponent(textfield_Iterationdelay, GroupLayout.PREFERRED_SIZE, 57, GroupLayout.PREFERRED_SIZE))))
										.addGap(5))
									.addGroup(groupLayout.createSequentialGroup()
										.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
											.addComponent(rdbtn2Threads, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(rdbtn8Threads, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
											.addComponent(rdbtn4Threads, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE))
										.addContainerGap())
									.addGroup(groupLayout.createSequentialGroup()
										.addComponent(chckbxRecordSimulation)
										.addContainerGap()))))))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblPrededators)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(predList, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(lblInitialSize)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(tvPredPopSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(sliderPredSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
							.addGap(37)
							.addComponent(lblPreys, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblIterationDelay)
							.addGap(6)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(slider_delaylength, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textfield_Iterationdelay, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(18)
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtn2Threads)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtn4Threads)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtn8Threads)))
					.addGap(6)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(preyList, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(label)
								.addGap(1)
								.addComponent(tvPreyPopSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addGap(2)
								.addComponent(sliderPreySize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(rdbtnNewRadioButton)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(checkBoxLimitIterations)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblSimulationIteration)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(sliderNumIterations, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(lblNewLabel_1))
								.addComponent(tvNumIterations, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnSquare)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnCircle)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnTriangle)
					.addGap(27)
					.addComponent(chckbxRecordSimulation)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblVegatablePopulation, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSimulationDimension, GroupLayout.PREFERRED_SIZE, 17, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(20)
							.addComponent(tvGrassPopSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(sliderGrassSize, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(grassList, GroupLayout.PREFERRED_SIZE, 104, GroupLayout.PREFERRED_SIZE)
							.addComponent(label_1)
							.addComponent(listSimulationDim, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)))
					.addGap(11)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnRunSim, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE))
					.addContainerGap())
		);
		frmSimulatedEcosystem.getContentPane().setLayout(groupLayout);
	}
}
