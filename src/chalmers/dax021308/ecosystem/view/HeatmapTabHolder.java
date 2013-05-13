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
	private List<IPopulation> popList;
	private HeatMapView heatMap;

	public HeatmapTabHolder(IModel m) {
		this.m = m;
		m.addObserver(this);
	}

	@Override
	public void propertyChange(PropertyChangeEvent evt) {
		if(evt.getPropertyName() == EcoWorld.EVENT_SETTINGS_CHANGED) {
			if(evt.getNewValue() instanceof SimulationSettings) {
				SimulationSettings s = (SimulationSettings) evt.getNewValue();
				popList = s.getFinalPopulations();
				removeAll();
				heatMap = new HeatMapView(m, s.getSimDimension(), new Dimension(50,50), popList);
				int nPops = popList.size();
				if(nPops>0) {
					addTab(popList.get(0).getName(), heatMap);
					for(int i=1; i < nPops; i++) {
						addTab(popList.get(i).getName(), null);
					}
				}

			}
		}
	}

	@Override
	public void setSelectedIndex(int index) {
		super.setSelectedIndex(index);
		if(popList.size() >= index + 1){
			heatMap.setPopulationNameToShow(popList.get(index).getName());
		}

	}

	@Override
	public void init() {
	}

	@Override
	public void addController(ActionListener controller) {

	}

	@Override
	public void release() {
		if(heatMap != null) {
			heatMap.release();
		}
		m.removeObserver(this);
	}

}
