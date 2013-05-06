package chalmers.dax021308.ecosystem.main;

import javax.swing.UIManager;

import chalmers.dax021308.ecosystem.controller.ScriptingController;

/**
 * Special Main method for using the script controller.
 * 
 * @author Erik Ramqvist
 *
 */
public class ScriptingInitializer {
	
	public static void main(String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch(Exception e) {
			e.printStackTrace();
		}
		new ScriptingController();
	}
}
