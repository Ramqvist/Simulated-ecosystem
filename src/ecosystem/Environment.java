package ecosystem;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import ecosystem.Ecosystem.OnFinishListener;

/**
 * Environment class.
 * @author Erik
 *
 */
public class Environment implements Runnable {
	private OnFinishListener mListener;
	private Random mRandom = new Random();
	
	public Environment(OnFinishListener listener) {
		this.mListener = listener;

	}

	@Override
	public void run() {
		//Do work here, will run on separate thread.y
		long sleep = 10 + mRandom.nextInt(20);
		Log.print("Environment sleeping " + sleep + " ms");
		try {
			Thread.sleep(sleep);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		Log.print("Environment onFinish().");
		mListener.onFinish();
	}
}