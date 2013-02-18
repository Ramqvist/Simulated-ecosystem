package chalmers.dax021308.ecosystem.model;

import java.util.Timer;
import java.util.TimerTask;

import chalmers.dax021308.ecosystem.model.EcoWorld.OnTickUpdate;


public class TimerHandler {
	
	public Timer t;
	
	public TimerHandler() {
		t = new Timer(false);
	}
	
	
	public void start(int time, final OnTickUpdate tickListener) {
		t.schedule(new TimerTask() {
			@Override
			public void run() {
				tickListener.onTick();
			}
		}, time);
	}
	
	public void stop() {
		t.cancel();
	}
}
