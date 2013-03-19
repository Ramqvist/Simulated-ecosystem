package chalmers.dax021308.ecosystem.main;

import chalmers.dax021308.ecosystem.controller.MainWindowController;

/**
 * Starter class for the Ecosystem program.
 * @author Erik
 *
 */
public class EcosystemInitializer {
	
	
	public static void main(String[] args) {
		/*try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}*/
		
		new MainWindowController();
	}
}
