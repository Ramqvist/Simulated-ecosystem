package chalmers.dax021308.ecosystem.view;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;

import javax.swing.JTabbedPane;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.population.IPopulation;

public class HeatmapTabHolder extends JTabbedPane implements IView {
	private static final long serialVersionUID = 1L;
	private IModel m;

	public HeatmapTabHolder(IModel m) {
		this.m = m;
		m.addObserver(this);
	}
	
	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == EcoWorld.EVENT_SETTINGS_CHANGED) {
			if(evt.getNewValue() instanceof SimulationSettings) {
				SimulationSettings s = (SimulationSettings) evt.getNewValue();
				List<IPopulation> popList = s.getFinalPopulations();
				removeAll();
				for(IPopulation p : popList) {
					addTab(p.getName(), new HeatMapView(m, s.getSimDimension(), new Dimension(50,50), 3, p.getName()));
				}
			}
		}
	}

	@Override
	public void init() {
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
	
}
