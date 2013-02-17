package ecosystem;

/**
 * Starter class for the Ecosystem program.
 * @author Erik
 *
 */
public class EcosystemInitializer {
	
	public static void main(String[] args) {
		int periodTime = 20;
		Ecosystem eco = new Ecosystem(periodTime);
		eco.start();
	}
}
