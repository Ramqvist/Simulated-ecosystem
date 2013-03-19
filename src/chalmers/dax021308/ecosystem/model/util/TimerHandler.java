package chalmers.dax021308.ecosystem.model.util;

import java.util.Timer;
import java.util.TimerTask;

import chalmers.dax021308.ecosystem.model.environment.EcoWorld.OnTickUpdate;

/**
 * Class for controlling the {@link Timer} in a simple way.
 * <p>
 * {@link #stop()} and {@link #start(int, OnTickUpdate)} to controll the Timer.
 * @author Erik
 *
 */
public class TimerHandler {
	
	public Timer t;
	
	public TimerHandler() {
		t = new Timer(false);
	}
	
	/**
	 * Starts the timer with the given time, and with the given listener.
	 * <p>
	 * Will not repeat, only fired once. Call method again to restart.
	 * <p>
	 * @param time
	 * @param tickListener
	 */
	public void start(int time, final OnTickUpdate tickListener) {
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				tickListener.onTick();
			}
		}, time);
	}
	
	/**
	 * Permanently stop the ongoing {@link Timer} and {@link TimerTask} object.
	 * 
	 */
	public void stop() {
		t.cancel();
	}
}
