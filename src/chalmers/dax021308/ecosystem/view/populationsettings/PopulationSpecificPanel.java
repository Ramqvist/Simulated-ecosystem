package chalmers.dax021308.ecosystem.view.populationsettings;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JCheckBox;
import javax.swing.JTextField;

/**
 * Panel that contains Population specific settings. 
 * Such as grass growth etc.
 * 
 * @author Erik Ramqvist
 *
 */
public class PopulationSpecificPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JLabel lblGeneticSettings;
	private JTextField textField;
	
	public PopulationSpecificPanel() {
		setLayout(new MigLayout("", "[][][][grow][]", "[][][][][][]"));
		
		lblGeneticSettings = new JLabel("Population Specific");
		lblGeneticSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblGeneticSettings, "cell 0 0 4 1");
		
		JLabel lblStotting = new JLabel("Grass growth");
		add(lblStotting, "cell 2 2,alignx trailing");
		
		textField = new JTextField();
		add(textField, "cell 3 2,alignx left");
		textField.setColumns(10);
	}
	
	public void addBooleanAttribute() {
		
	}
	public void addIntegerAttribute() {
		
	}
	public void addDoubleAttribute() {
		
	}

}
