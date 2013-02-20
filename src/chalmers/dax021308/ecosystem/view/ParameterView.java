package chalmers.dax021308.ecosystem.view;

import javax.swing.JPanel;
import javax.swing.JLabel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JRadioButton;
import java.awt.Font;
import javax.swing.ButtonGroup;

public class ParameterView extends JPanel {
	private final ButtonGroup shapeButtonGroup = new ButtonGroup();
	private final ButtonGroup obstacleButtonGroup = new ButtonGroup();

	/**
	 * Create the panel.
	 */
	public ParameterView() {
		setLayout(new MigLayout("", "[]", "[][][][][][][][][]"));
		
		JLabel lblShapeOfUniverse = new JLabel("Shape of Universe");
		lblShapeOfUniverse.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblShapeOfUniverse, "cell 0 0");
		
		JRadioButton rdbtnNewRadioButton = new JRadioButton("Square");
		shapeButtonGroup.add(rdbtnNewRadioButton);
		add(rdbtnNewRadioButton, "cell 0 1");
		
		JRadioButton rdbtnNewRadioButton_1 = new JRadioButton("Circle");
		shapeButtonGroup.add(rdbtnNewRadioButton_1);
		add(rdbtnNewRadioButton_1, "cell 0 2");
		
		JRadioButton rdbtnNewRadioButton_2 = new JRadioButton("Triangle");
		shapeButtonGroup.add(rdbtnNewRadioButton_2);
		add(rdbtnNewRadioButton_2, "cell 0 3");
		
		JLabel lblObstacles = new JLabel("Obstacles");
		lblObstacles.setFont(new Font("Tahoma", Font.PLAIN, 14));
		add(lblObstacles, "cell 0 5");
		
		JRadioButton rdbtnNewRadioButton_3 = new JRadioButton("None");
		obstacleButtonGroup.add(rdbtnNewRadioButton_3);
		add(rdbtnNewRadioButton_3, "cell 0 6");
		
		JRadioButton rdbtnNewRadioButton_4 = new JRadioButton("Few");
		obstacleButtonGroup.add(rdbtnNewRadioButton_4);
		add(rdbtnNewRadioButton_4, "cell 0 7");
		
		JRadioButton rdbtnNewRadioButton_5 = new JRadioButton("Many");
		obstacleButtonGroup.add(rdbtnNewRadioButton_5);
		add(rdbtnNewRadioButton_5, "cell 0 8,aligny baseline");

	}

}
