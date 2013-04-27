package chalmers.dax021308.ecosystem.view.populationsettings;

import java.awt.Dimension;

import javax.swing.JPanel;

import net.miginfocom.swing.MigLayout;

public class PopulationPanel extends JPanel {
	private static final long serialVersionUID = -6239580894018795289L;

	public PopulationPanel() {
		setLayout(new MigLayout("", "[grow]", "[grow][grow][][grow]"));
		
		CommonSettingsPanel panel = new CommonSettingsPanel();
		add(panel, "cell 0 0,grow");
		
		GeneticPanel panel_1 = new GeneticPanel();
		add(panel_1, "cell 0 2,grow");
		
		JPanel panel_2 = new PopulationSpecificPanel();
		add(panel_2, "cell 0 3,grow");
		setSize(new Dimension (700, 700));
	}

}
