package chalmers.dax021308.ecosystem.view.populationsettings;


import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import chalmers.dax021308.ecosystem.controller.GeneticPanelController;
import chalmers.dax021308.ecosystem.model.genetics.GeneticSettings.GeneSpecification;
import chalmers.dax021308.ecosystem.model.util.Stat;
import net.miginfocom.swing.MigLayout;


/**
 * Panel for Genetic settings.
 * <p>
 * Dynamically adds gui elements depending on the values  in the given GeneticSettings.
 *
 * @author Loanne Berggren
 * Remade Erik's version
 */
public class GeneticPanel2 extends JPanel {

	private int currentRow = 0;

	public GeneticPanel2() {
		setLayout(new MigLayout("", "[][][65.00][65.00][65]", "[][][][][]"));

		JLabel lblGeneticSettings = new JLabel("Genetic Settings");
		lblGeneticSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblGeneticSettings, "cell 0 0 4 1");

		add(new JLabel("Active on birth"), "cell 3 1,alignx center");
		add(new JLabel("Mutable"), "cell 4 1");
		currentRow = 1;
	}
	private static final long serialVersionUID = 1L;

	public void addNewBooleanComponents(JLabel name, JCheckBox chkbxActiveBirth, JCheckBox chckbxMutable){
		currentRow++;
		add(name, "cell 2 "+currentRow+",alignx right");
		add(chkbxActiveBirth, "cell 3 "+currentRow+",alignx center");
		add(chckbxMutable, "cell 4 "+currentRow+",alignx left");
	}

	public void addDoubleHeaders(){
		currentRow++;
		add(new JLabel(" "), "cell 2 "+currentRow+",alignx right");
		currentRow++;
		add(new JLabel("Start"), "cell 3 "+currentRow+",alignx center");
		add(new JLabel("Min"), "cell 4 "+currentRow+",alignx center");
		add(new JLabel("Max"), "cell 5 "+currentRow+",alignx center");
		add(new JLabel("Mutable"), "cell 6 "+currentRow+",alignx center");
		add(new JLabel("Random Start"), "cell 7 "+currentRow+",alignx center");
		currentRow++;
	}

	public void addNewDoubleComponents(JLabel title, JTextField tfStartValue,
			JTextField tfMinValue, JTextField tfMaxValue, JCheckBox chckbxMutable, JCheckBox chkbxRandomStart){
		currentRow++;
		add(title, "cell 2 "+currentRow+",alignx right");
		add(tfStartValue, "cell 3 "+currentRow+",growx");
		add(tfMinValue, "cell 4 "+currentRow+",growx");
		add(tfMaxValue, "cell 5 "+currentRow+",growx");
		add(chckbxMutable, "cell 6 "+currentRow+",alignx center");
		add(chkbxRandomStart, "cell 7 "+currentRow+",alignx center");
	}

}
