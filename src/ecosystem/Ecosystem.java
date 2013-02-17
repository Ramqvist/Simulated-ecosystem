package ecosystem;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Ecosystem main class.
 * @author Erik
 *
 */
public class Ecosystem {
	private AtomicBoolean environmentFinished = new AtomicBoolean(false);
	private AtomicBoolean timerFinished       = new AtomicBoolean(false);
	private TimerHandler timer;
	private Environment env;
	private int periodTime;
	private Object syncObj = new Object();
	private static final int NUM_THREAD = 1;
	private int numUpdates = 0;
    private ExecutorService executor = Executors.newFixedThreadPool(NUM_THREAD);
	
	private OnFinishListener mOnFinishListener = new OnFinishListener() {
		@Override
		//När Environment är klar.
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
	
	public Ecosystem(int periodTime) {
		this.periodTime = periodTime;
		timer = new TimerHandler();
		env = new Environment(mOnFinishListener);
	}
	
	public void start() {
		scheduleEnvironmentUpdate();
	}
	
	public void stop() {
		executor.shutdown();
		timer.stop();
	}
	
	private void scheduleEnvironmentUpdate() {
		Log.print("---- sheduleEnvironmentUpdate() ---- Number of updates:" + ++numUpdates);
		timer.start(periodTime, onTickListener);
		executor.execute(env);
	}
	
	public interface OnTickUpdate {
		public void onTick();
	}
	
	public interface OnFinishListener {
		public void onFinish();
	}
	
}
