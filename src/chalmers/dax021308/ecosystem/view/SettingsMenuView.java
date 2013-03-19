package chalmers.dax021308.ecosystem.view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.miginfocom.swing.MigLayout;

public class SettingsMenuView extends JFrame {
	private final ButtonGroup shapeButtonGroup = new ButtonGroup();
	private final ButtonGroup delayButtonGroup = new ButtonGroup();
	private final ButtonGroup graphicsButtonGroup = new ButtonGroup();
	private int graphics; 


	/**
	 * Create the frame.
	 */
	public SettingsMenuView(final MainWindow mw) {
		setTitle("Simulation settings");
		setBounds(100, 100, 450, 357);
		getContentPane().setLayout(new MigLayout("", "[][grow][]", "[][][][][][][][][][][][][][][][][]"));
		
		JLabel lblShapeOfUniverse = new JLabel("Shape of Universe");
		lblShapeOfUniverse.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblShapeOfUniverse, "cell 0 0");
		
		JLabel lblNumberOfIterations = new JLabel("Number of Iterations");
		lblNumberOfIterations.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblNumberOfIterations, "cell 1 0");
		
		JRadioButton rdbtnSquare = new JRadioButton("Square");
		rdbtnSquare.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//SQUARE
				System.out.println("Square shaped universe");
			}
		});
		shapeButtonGroup.add(rdbtnSquare);
		getContentPane().add(rdbtnSquare, "cell 0 1");
		
		JSpinner spinnerIterations = new JSpinner(new SpinnerNumberModel(0, 0, 1000, 1)); //vilket spann? nu 0-1000
		spinnerIterations.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				//NUMBER OF ITERATIONS
				System.out.println("number of iterations set");
			}
		});
		getContentPane().add(spinnerIterations, "cell 1 1");
		
		JRadioButton rdbtnCircle = new JRadioButton("Circle");
		rdbtnCircle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//CIRCLE
				System.out.println("Circle shaped universe");
			}
		});
		shapeButtonGroup.add(rdbtnCircle);
		getContentPane().add(rdbtnCircle, "cell 0 2");
		
		JRadioButton rdbtnTriangle = new JRadioButton("Triangle");
		rdbtnTriangle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//TRIANGLE
				System.out.println("Triangle shaped universe");
			}
		});
		shapeButtonGroup.add(rdbtnTriangle);
		getContentPane().add(rdbtnTriangle, "cell 0 3");
		
		JLabel lblObstacles = new JLabel("Obstacles");
		lblObstacles.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblObstacles, "cell 1 3");
		
		final JSlider sliderObstacles = new JSlider();  //vilket spann?
		sliderObstacles.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(!sliderObstacles.getValueIsAdjusting()) {
					//OBSTACLES
					System.out.println("obstacles set");
				}
			}
		});
		getContentPane().add(sliderObstacles, "cell 1 4");
		
		JLabel lblDelay = new JLabel("Delay");
		lblDelay.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblDelay, "cell 0 5");
		
		JRadioButton rdbtnDelayOn = new JRadioButton("On");
		rdbtnDelayOn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DELAY ON
				System.out.println("delay on");
			}
		});
		delayButtonGroup.add(rdbtnDelayOn);
		getContentPane().add(rdbtnDelayOn, "cell 0 6");
		
		JLabel lblGraphicsEngine = new JLabel("Graphics Engine");
		lblGraphicsEngine.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblGraphicsEngine, "cell 1 6");
		
		JRadioButton rdbtnOff = new JRadioButton("Off");
		rdbtnOff.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//DELAY OFF
				System.out.println("delay off");
			}
		});
		delayButtonGroup.add(rdbtnOff);
		getContentPane().add(rdbtnOff, "cell 0 7");
		
		JRadioButton rdbtnJavaAWT = new JRadioButton("Java AWT");
		rdbtnJavaAWT.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//JAVA AWT
				System.out.println("awt graphics");
				graphics = 0;
			}
		});
		graphicsButtonGroup.add(rdbtnJavaAWT);
		getContentPane().add(rdbtnJavaAWT, "cell 1 7");
		
		JRadioButton rdbtnOpenGL = new JRadioButton("OpenGL");
		rdbtnOpenGL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				//OPEN GL
				System.out.println("openGL graphics");
				graphics = 1;
			}
		});
		graphicsButtonGroup.add(rdbtnOpenGL);
		getContentPane().add(rdbtnOpenGL, "cell 1 8");
		
		JLabel lblDelayLength = new JLabel("Delay length");
		lblDelayLength.setFont(new Font("Tahoma", Font.PLAIN, 12));
		getContentPane().add(lblDelayLength, "cell 0 9");
		
		final JSlider sliderDelayLength = new JSlider();  //vilket spann?
		sliderDelayLength.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent e) {
				if(!sliderDelayLength.getValueIsAdjusting()) {
					//DELAY LENGTH
					System.out.println("delay length set");
				}
			}
		});
		getContentPane().add(sliderDelayLength, "cell 0 10");
		
		JButton btnOk = new JButton("OK");
		btnOk.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				mw.setSimulationPanel(graphics);
				SettingsMenuView.this.dispose();
			}
		});
		getContentPane().add(btnOk, "cell 1 10");

	}

}
