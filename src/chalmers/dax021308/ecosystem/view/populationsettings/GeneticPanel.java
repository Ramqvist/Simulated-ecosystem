package chalmers.dax021308.ecosystem.view.populationsettings;

import javax.swing.JPanel;
import net.miginfocom.swing.MigLayout;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JCheckBox;

public class GeneticPanel extends JPanel {
	public GeneticPanel() {
		setLayout(new MigLayout("", "[][][][][]", "[][][][][]"));
		
		JLabel lblGeneticSettings = new JLabel("Genetic Settings");
		lblGeneticSettings.setFont(new Font("Tahoma", Font.PLAIN, 16));
		add(lblGeneticSettings, "cell 0 0 4 1");
		
		JLabel lblActiveOnBirt = new JLabel("Active on birth");
		add(lblActiveOnBirt, "cell 3 1,alignx center");
		
		JLabel lblMutable = new JLabel("Mutable");
		add(lblMutable, "cell 4 1");
		
		JLabel lblStotting = new JLabel("Stotting");
		add(lblStotting, "cell 2 2,alignx right");
		
		JCheckBox chckbxNewCheckBox = new JCheckBox("");
		add(chckbxNewCheckBox, "cell 3 2,alignx center");
		
		JCheckBox chckbxMutable = new JCheckBox("");
		add(chckbxMutable, "cell 4 2,alignx center");
		
		JLabel lblGroupBehavoir = new JLabel("Group behavoir");
		add(lblGroupBehavoir, "cell 2 3");
		
		JCheckBox chckbxGroupBehavior = new JCheckBox("");
		add(chckbxGroupBehavior, "cell 3 3,alignx center");
		
		JCheckBox checkBox = new JCheckBox("");
		add(checkBox, "cell 4 3,alignx center");
	}
	private static final long serialVersionUID = 1L;

}
