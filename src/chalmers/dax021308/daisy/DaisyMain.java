package chalmers.dax021308.daisy;

/**
 * 
 * @author Albin Bramstång
 */
public class DaisyMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Environment e = new Environment(100, 100, 2000, 0.9, 1000, 0.12, 10, 50, 0.1);
		Thread t = new Thread(e);
		t.start();
	}

}
