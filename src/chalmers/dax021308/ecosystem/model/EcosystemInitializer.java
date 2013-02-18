package chalmers.dax021308.ecosystem.model;

/**
 * Starter class for the Ecosystem program.
 * @author Erik
 *
 */
public class EcosystemInitializer {
	
	public static void main(String[] args) {
		int periodTime = 20;
		EcoWorld eco = new EcoWorld(periodTime, Integer.MAX_VALUE);
		eco.start();
	}
}
