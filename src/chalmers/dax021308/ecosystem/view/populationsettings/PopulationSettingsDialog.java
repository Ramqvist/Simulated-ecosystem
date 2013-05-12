package chalmers.dax021308.ecosystem.view.populationsettings;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JTabbedPane;

import chalmers.dax021308.ecosystem.controller.GeneticPanelController;
import chalmers.dax021308.ecosystem.model.genetics.GeneticSettings;
import chalmers.dax021308.ecosystem.model.population.settings.GrassFieldSettings;
import chalmers.dax021308.ecosystem.model.population.settings.GrassSettings;
import chalmers.dax021308.ecosystem.model.population.settings.PredSettings;
import chalmers.dax021308.ecosystem.model.population.settings.PreySettings;
import chalmers.dax021308.ecosystem.view.IView;

/**
 *
 * Dialog for changing population settings.
 * <p>
 * Contains one tab per population.
 *
 * @author Erik Ramqvist
 *
 */
public class PopulationSettingsDialog extends JDialog implements IView {
	private static final long serialVersionUID = -4258143526404863551L;
	private JTabbedPane tabPane = new JTabbedPane();

	public PopulationSettingsDialog(Frame superFrame) {
		super(superFrame);
		init();
	}

	@Override
	public void propertyChange(PropertyChangeEvent arg0) {
	}
/*
	@Override
	public void init() {
		tabPane.addTab("Wolf agent", new PopulationPanel(GeneticSettings.predSettings, PredSettings.instance));
		tabPane.addTab("Deer agent", new PopulationPanel(GeneticSettings.preySettings, PreySettings.instance));
		tabPane.addTab("Grass agent", new PopulationPanel(null, GrassSettings.instance));
		tabPane.addTab("Grass Field Agent", new PopulationPanel(null, GrassFieldSettings.instance));
		add(tabPane);
		pack();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Population settings");
		setIconImage(new ImageIcon("res/Simulated ecosystem icon.png").getImage());
		setResizable(false);
		centerOnScreen(this, true);
		setVisible(true);
	}*/

	@Override
	public void init() {
		tabPane.addTab("Wolf agent", GeneticPanelController.getNewPopulationPanel(GeneticSettings.predSettings, PredSettings.instance));
		tabPane.addTab("Deer agent", GeneticPanelController.getNewPopulationPanel(GeneticSettings.preySettings, PreySettings.instance));
		tabPane.addTab("Grass agent", GeneticPanelController.getNewPopulationPanel(null, GrassSettings.instance));
		tabPane.addTab("Grass Field Agent", GeneticPanelController.getNewPopulationPanel(null, GrassFieldSettings.instance));
		add(tabPane);
		pack();
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Population settings");
		setIconImage(new ImageIcon("res/Simulated ecosystem icon.png").getImage());
		setResizable(false);
		centerOnScreen(this, true);
		setVisible(true);
	}

	@Override
	public void addController(ActionListener controller) {
	}

	@Override
	public void onTick() {
	}

	@Override
	public void release() {
	}

	public void centerOnScreen(final Component c, final boolean absolute) {
	    final int width = c.getWidth();
	    final int height = c.getHeight();
	    final Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
	    int x = (screenSize.width / 2) - (width / 2);
	    int y = (screenSize.height / 2) - (height / 2);
	    if (!absolute) {
	        x /= 2;
	        y /= 2;
	    }
	    c.setLocation(x, y);
	}
}
