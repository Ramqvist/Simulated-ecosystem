package chalmers.dax021308.ecosystem.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Ecosystem main class.
 * <p>
 * Recieves notifications from the {@link TimerHandler} and the {@link IEnvironment}.
 * 
 * @author Erik
 *
 */
public class EcoWorld {
	private AtomicBoolean environmentFinished = new AtomicBoolean(false);
	private AtomicBoolean timerFinished       = new AtomicBoolean(false);
	private AtomicBoolean shouldRun       	  = new AtomicBoolean(false);
	private boolean runWithoutTimer;
	private int numIterations;
	private TimerHandler timer;
	private Environment env;
	private int tickTime;
	private PropertyChangeSupport observers;
	/**
	 * Simple object, used for synchronizing the {@link TimerHandler} and the {@link IEnvironment} {@link OnFinishListener}
	 */
	private Object syncObject = new Object();
	private static final int NUM_THREAD = 1;
	private int numUpdates = 0;
    private ExecutorService executor = Executors.newFixedThreadPool(NUM_THREAD);
    
    public static final String EVENT_TICK = "chalmers.dax021308.ecosystem.model.event_tick";
    public static final String EVENT_STOP = "chalmers.dax021308.ecosystem.model.event_stop";
	

	private OnFinishListener mOnFinishListener = new OnFinishListener() {
		@Override
		public void onFinish(List<IPopulation> popList, List<Obstacle> obsList) {
			//Fire state changed to observers, notify there has been an update.
			observers.firePropertyChange(EVENT_TICK, obsList, popList);
			if(runWithoutTimer) {
				scheduleEnvironmentUpdate();				
			} else {
				synchronized (syncObject) {
					Log.v("Environment: Finished.");
					if(timerFinished.get()) {
						Log.v("Environment: Timer is finished, doing Environment update");
						environmentFinished.set(false);
						timerFinished.set(false);
						scheduleEnvironmentUpdate();
					} else {
						Log.v("Environment: Timer NOT finished, waiting...");
						environmentFinished.set(true);
					}
				}
			}
		}
	};
	
	private OnTickUpdate onTickListener = new OnTickUpdate() {
		@Override
		//När timer är klar.
		public void onTick() {
			synchronized (syncObject) {
				Log.v("Timer: Finished.");
				if(environmentFinished.get()) {
					Log.v("Timer: Environment is finished, doing Environment update");
					timerFinished.set(false);
					environmentFinished.set(false);
					scheduleEnvironmentUpdate();
				} else {
					Log.v("Timer: Environment NOT finished, waiting...");
					timerFinished.set(true);
				}
			}
		}
	};
	
	public EcoWorld(int tickTime, int numIterations) {
		this.tickTime = tickTime;
		this.timer = new TimerHandler();
		this.env = new Environment(mOnFinishListener);
		this.runWithoutTimer = false;
		this.numIterations = numIterations; 
		this.observers = new PropertyChangeSupport(this);
	}
	
	public EcoWorld(int numIterations) {
		this(0, numIterations);
		this.runWithoutTimer = true;
	}
	
	public EcoWorld() {
		this(Integer.MAX_VALUE);
	}
	
	/**
	 * Start the EcoWorld simulation program.
	 * 
	 */
	public void start() {
		shouldRun.set(true);
		scheduleEnvironmentUpdate();
		Log.i("EcoWorld started.");
	}
	
	/**
	 * Stops the scheduling algorithms. 
	 * <p>
	 * Warning! Will not affect ongoing execution!
	 * 
	 */
	public void stop() {
		shouldRun.set(false);
		executor.shutdown();
		timer.stop();
		numUpdates = 0;
		Log.i("EcoWorld stopped.");
	}
	
	/**
	 * Forces the ongoing execution to stop!
	 * <p>
	 * Warning! Untested method, might not work.
	 * 
	 */
	public void forceStop() {
		shouldRun.set(false);
		executor.shutdownNow();
		timer.stop();
		numUpdates = 0;
		Log.i("EcoWorld stopped.");
	}
	
	/**
	 * Starts the {@link TimerHandler} and executes one Environment iteration.
	 */
	private void scheduleEnvironmentUpdate() {
		if(numIterations-- >= 0) {
			if(!runWithoutTimer) {
				timer.start(tickTime, onTickListener);
			}
			Log.v("---- sheduleEnvironmentUpdate() ---- Number of updates:" + ++numUpdates);
			executor.execute(env);				
		} else {
			stop();
		}
	}
	
	/**
	 * Adjust the tick rate of the next iteration.
	 * The currently executing iteration will not be affected.
	 * @param newTickRate
	 */
	public void adjustTickRate(int newTickRate) {
		this.tickTime = newTickRate;
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
	
	public void setRunWithoutTimer(boolean runWithoutTimer) {
		this.runWithoutTimer = runWithoutTimer;
	}
	

	/**
	 * Environment onFinish listener. Called when one iteration of the Environment is done.
	 * 
	 * @author Erik
	 *
	 */
	public interface OnFinishListener {
		public void onFinish(List<IPopulation> popList, List<Obstacle> obsList);
	}

	public void addObserver(PropertyChangeListener listener) {
		observers.addPropertyChangeListener(listener);
	}
	
	public void removeObserver(PropertyChangeListener listener) {
		observers.removePropertyChangeListener(listener);
	}
	
}