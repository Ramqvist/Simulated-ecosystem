package chalmers.dax021308.ecosystem.model;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Ecosystem main class.
 * @author Erik
 *
 */
public class EcoWorld {
	private AtomicBoolean environmentFinished = new AtomicBoolean(false);
	private AtomicBoolean timerFinished       = new AtomicBoolean(false);
	private AtomicBoolean shouldRun       = new AtomicBoolean(false);
	private TimerHandler timer;
	private Environment env;
	private int tickTime;
	/**
	 * Simple object, used for synchronizing the {@link TimerHandler} and the Enviroment {@link OnFinishListener}
	 */
	private Object syncObj = new Object();
	private static final int NUM_THREAD = 1;
	private int numUpdates = 0;
    private ExecutorService executor = Executors.newFixedThreadPool(NUM_THREAD);
	

	private OnFinishListener mOnFinishListener = new OnFinishListener() {
		@Override
		public void onFinish() {
			synchronized (syncObj) {
				Log.print("Environment: Finished.");
				if(timerFinished.get()) {
					Log.print("Environment: Timer is finished, doing Environment update");
					environmentFinished.set(false);
					timerFinished.set(false);
					scheduleEnvironmentUpdate();
				} else {
					Log.print("Environment: Timer NOT finished, waiting...");
					environmentFinished.set(true);
				}
			}
		}
	};
	
	private OnTickUpdate onTickListener = new OnTickUpdate() {
		@Override
		//När timer är klar.
		public void onTick() {
			synchronized (syncObj) {
				Log.print("Timer: Finished.");
				if(environmentFinished.get()) {
					Log.print("Timer: Environment is finished, doing Environment update");
					timerFinished.set(false);
					environmentFinished.set(false);
					scheduleEnvironmentUpdate();
				} else {
					Log.print("Timer: Environment NOT finished, waiting...");
					timerFinished.set(true);
				}
			}
		}
	};
	
	public EcoWorld(int tickTime, int numIterations) {
		this.tickTime = tickTime;
		timer = new TimerHandler();
		env = new Environment(mOnFinishListener);
	}
	
	public void start() {
		shouldRun.set(true);
		scheduleEnvironmentUpdate();
	}
	
	/**
	 * Stops the scheduling algorithms. 
	 * 
	 */
	public void stop() {
		shouldRun.set(true);
		executor.shutdown();
		timer.stop();
	}
	
	/**
	 * Starts the {@link TimerHandler} and executes one Environment iteration.
	 */
	private void scheduleEnvironmentUpdate() {
		Log.print("---- sheduleEnvironmentUpdate() ---- Number of updates:" + ++numUpdates);
		timer.start(tickTime, onTickListener);
		executor.execute(env);
	}
	
	/**
	 * Adjust the tick rate of the next iteration.
	 * The currently executing iteration will not be affected.
	 * @param newTickRate
	 */
	public void adjustTickRate(int newTickRate) {
		
	}
	
	/**
	 * Tick listener for the TimerHandler. Called when timer has expired.
	 * 
	 * @author Erik
	 *
	 */
	public interface OnTickUpdate {
		public void onTick();
	}
	

	/**
	 * Environment onFinish listener. Called when one iteration of the Environment is done.
	 * 
	 * @author Erik
	 *
	 */
	public interface OnFinishListener {
		public void onFinish();
	}
	
}
