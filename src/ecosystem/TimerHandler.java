package ecosystem;

import java.util.Timer;
import java.util.TimerTask;

import ecosystem.Ecosystem.OnTickUpdate;

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
