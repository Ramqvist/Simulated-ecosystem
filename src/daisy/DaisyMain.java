package daisy;

public class DaisyMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Environment e = new Environment(100, 100, 0.5, 500, (1/0.5)-1);
		Thread t = new Thread(e);
		t.start();
	}

}
