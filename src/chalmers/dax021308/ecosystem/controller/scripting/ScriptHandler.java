package chalmers.dax021308.ecosystem.controller.scripting;

import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import chalmers.dax021308.ecosystem.controller.IController;
import chalmers.dax021308.ecosystem.controller.MainWindowController;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.view.ScriptSelector;
import chalmers.dax021308.ecosystem.view.ScriptSelector.OnScriptSelectedListener;

public class ScriptHandler implements IController{
	
	private ScriptSelector ss;

	public ScriptHandler() {
		List<IScript> scriptList = new ArrayList<IScript>();

		/* Add your created script to this list here! */
		scriptList.add(new MultiThreadedPerformanceScript());
		//scriptList.add(new FancyScript());
		
		
		this.ss = new ScriptSelector(scriptList, new OnScriptSelectedListener() {
			@Override
			public void onScriptSelected(IScript s, boolean enableGUI) {
				runScript(s, enableGUI);
				ss.dispose();
			}

		});
	}

	private void runScript(IScript s, boolean enableGUI) {
		EcoWorld e = null;
		if(enableGUI) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception es) {
				es.printStackTrace();
			}
			MainWindowController window = new MainWindowController();
			e = window.model;
		} else {
			e = new EcoWorld();
		}
		s.init(e);
		e.start();
	}
	@Override
	public void init() {
		
	}

	@Override
	public void release() {
		
	}

	@Override
	public void setModel(IModel m) {
		
	}
}
