package chalmers.dax021308.daisy.discreteLogisticChaosModel;


public class BifurcationMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DiscreteLogisticChaosModel DLG = new DiscreteLogisticChaosModel(0.1, 2.6, 150);
		Thread t = new Thread(DLG);
		t.start();
	}
	
}
