package chalmers.dax021308.ecosystem.view.populationsettings;

import java.awt.Dimension;

import javax.swing.JPanel;

import chalmers.dax021308.ecosystem.model.genetics.GeneticSettings;
import chalmers.dax021308.ecosystem.model.population.settings.CommonSettings;

import net.miginfocom.swing.MigLayout;

public class PopulationPanel extends JPanel {
	private static final long serialVersionUID = -6239580894018795289L;

	public PopulationPanel(GeneticSettings s, CommonSettings c) {
		setLayout(new MigLayout("", "[grow]", "[grow][grow][][grow]"));
		
		CommonSettingsPanel panel = new CommonSettingsPanel(c);
		add(panel, "cell 0 0,grow");
		
		GeneticPanel panel_1 = new GeneticPanel(s);
		add(panel_1, "cell 0 2,grow");
		
		setSize(new Dimension (700, 700));
	}

}
