package chalmers.dax021308.ecosystem.controller.scripting;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.UIManager;

import chalmers.dax021308.ecosystem.controller.IController;
import chalmers.dax021308.ecosystem.controller.MainWindowController;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IModel;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.view.ScriptSelector;
import chalmers.dax021308.ecosystem.view.ScriptSelector.OnScriptSelectedListener;

/**
 * Class that handles the connection to the Scripts and the view starting them. 
 * <p>
 * Add your own script to the list and start it using the ScriptInitializer class in the main package.
 * <p> 
 * Don't forget to copy the run configuration (VM Arguments) from the normal one to ScriptInitializer.
 * 
 * @author Erik Ramqvist
 *
 */
public class ScriptHandler implements IController{
	
	private ScriptSelector ss;
	private boolean shutdownOnFinish;

	public ScriptHandler() {
		List<IScript> scriptList = new ArrayList<IScript>();

		/* Add your created script to this list here! */	
		scriptList.add(new MultiThreadedPerformanceScript());
		scriptList.add(new OptimalMapSelectionScript());
		scriptList.add(new MySillyScript());
		//scriptList.add(new MyFancyScript());
		
		this.ss = new ScriptSelector(scriptList, new OnScriptSelectedListener() {
			@Override
			public void onScriptSelected(IScript s, boolean enableGUI, boolean minimalGUI, boolean shutdown) {
				runScript(s, enableGUI, minimalGUI);
				ss.dispose();
				ScriptHandler.this.shutdownOnFinish = shutdown;
				//TODO: Add minimalistic gui option.
			}

		});
	}

	private void runScript(IScript s, boolean enableGUI, boolean minimalGUI) {
		EcoWorld e = null;
		if(enableGUI) {
			try {
				UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			} catch(Exception es) {
				es.printStackTrace();
			}
			MainWindowController window = new MainWindowController();
			e = window.model;
			if(minimalGUI) {
				window.window.toggleFullscreen();
				window.window.releaseAllButOpenGL();
			}
		} else {
			e = new EcoWorld();
		}
		s.init(e, new OnFinishedScriptListener() {
			
			@Override
			public void onFinishScript() {
				if(shutdownOnFinish) {
					try {
						shutdown();
					} catch (RuntimeException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		});
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
	
	/**
	 * Shutdowns your computer! Herp derp!
	 * 
	 * @throws RuntimeException
	 * @throws IOException
	 */
	public static void shutdown() throws RuntimeException, IOException {
	    String shutdownCommand;
	    String operatingSystem = System.getProperty("os.name");
	    Log.v(operatingSystem);

	    if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
	        shutdownCommand = "shutdown -h now";
	    }
	    else if (operatingSystem.startsWith("Windows")) {
	        shutdownCommand = "shutdown.exe -s -t 0";
	    }
	    else {
	        throw new RuntimeException("Unsupported operating system.");
	    }

	    Runtime.getRuntime().exec(shutdownCommand);
	    System.exit(0);
	}
	
	public interface OnFinishedScriptListener {
		public void onFinishScript();
	}
}
