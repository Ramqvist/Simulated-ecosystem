package discreteLogisticChaosModel;

import daisy.Environment;

public class BifurcationMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DiscreteLogisticChaosModel DLG = new DiscreteLogisticChaosModel(0.1, 2.5, 60);
		Thread t = new Thread(DLG);
		t.start();
	}
	
}
