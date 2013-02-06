package daisy;

public class DaisyMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Environment e = new Environment(1000, 2000, 0.75, 2000, (1/0.9064)-1, 20, 0.1);
		Thread t = new Thread(e);
		t.start();
	}

}
