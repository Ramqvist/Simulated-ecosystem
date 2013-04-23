package chalmers.dax021308.ecosystem.controller.mapeditor;

import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.List;

import javax.swing.ListModel;
import javax.swing.event.ListDataListener;

import chalmers.dax021308.ecosystem.controller.IController;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.MapFileHandler;
import chalmers.dax021308.ecosystem.model.environment.mapeditor.SimulationMap;
import chalmers.dax021308.ecosystem.view.mapeditor.ChooseMapDialog;
import chalmers.dax021308.ecosystem.view.mapeditor.NewMapDialog;

public class ChooseMapDialogController implements IController {
	
	private final ChooseMapDialog view;
	private OnLoadedMapSelectedListener listener;
		
	
	private ActionListener finishActionListener = new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			listener.onSelect(view.mapList.getSelectedValue());
			view.dispose();
		}
	};
	
	public ChooseMapDialogController(Frame superFrame, OnLoadedMapSelectedListener listener) {
		this.listener = listener;
		view = new ChooseMapDialog(superFrame);
		view.btnLoadMap.addActionListener(finishActionListener);
		view.mapList.setModel(new ListModel<SimulationMap>() {
			List<SimulationMap> maps = MapFileHandler.readMapsFromMapsFolder();
			@Override
			public int getSize() {
				if(maps != null) {
					return maps.size();
				}
				return 0;
			}

			@Override
			public SimulationMap getElementAt(int index) {
				if(maps != null) {
				return maps.get(index);
				} else {
					return null;
				}
			}

			@Override
			public void addListDataListener(ListDataListener l) {
				
			}

			@Override
			public void removeListDataListener(ListDataListener l) {
				
			}
			
		});
	}
	
	@Override
	public void init() {
		
	}

	@Override
	public void release() {
		view.dispose();
		listener = null;
	}

	@Override
	public void setModel(IModel m) {
		
	}


	public interface OnLoadedMapSelectedListener {
		public void onSelect(SimulationMap	map);
	}

}
