package chalmers.dax021308.ecosystem.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;

import sun.misc.Cleaner;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.environment.EcoWorld;
import chalmers.dax021308.ecosystem.model.environment.IObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.model.util.Position;

public class SimulationView extends JPanel implements IView {
	
	private static final long serialVersionUID = 1585638837620985591L;
	private JFrame frame;
	private List<IPopulation> newPops;
	private List<IObstacle> newObs;
	private Random ran = new Random();
	private Dimension gridDimension;
	private Timer fpsTimer;
	private int updates;
	private int fps;
	private boolean showFPS;
	/**
	 * Create the panel.
	 */
	public SimulationView(EcoWorld model, Dimension size, boolean showFPS) {
		model.addObserver(this);
		frame = new JFrame("Simulation View");
		frame.add(this);
		frame.setSize(size);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		this.setBackground(Color.white);
		gridDimension = size;
		this.showFPS = showFPS;
		if(showFPS) {
			fpsTimer = new Timer();
			fpsTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					fps = updates;
					updates = 0;
				}
			}, 1000, 1000);
		}
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String eventName = event.getPropertyName();
		if(eventName == EcoWorld.EVENT_STOP) {
			//Model has stopped. Maybe hide view?
			//frame.setVisible(false);
		} else if(eventName == EcoWorld.EVENT_TICK) {
			//Tick notification recived from model. Do something with the data.
			if(event.getNewValue() instanceof List<?>) {
				this.newPops = (List<IPopulation>) event.getNewValue();
			}
			if(event.getOldValue() instanceof List<?>) {
				this.newObs = (List<IObstacle>) event.getOldValue();
			}
			removeAll();
			repaint();
			revalidate();
		}
	}
	
	@Override
    public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(showFPS) {
			updates++;
			Log.v(fps + "");
			char[] fpsChar;
			if(fps > 1000) {
				fpsChar = new char[8];
				fpsChar[0] = '9';
				fpsChar[1] = '9';
				fpsChar[2] = '9';
				fpsChar[3] = ' ';
				fpsChar[4] = 'f';
				fpsChar[5] = 'p';
				fpsChar[6] = 's';
			} else if(fps > 100) {
				fpsChar = new char[7];
				fpsChar[1] = Character.forDigit( (fps / 100) , 10);
				fpsChar[1] = Character.forDigit( (fps % 100) / 10, 10);
				fpsChar[2] = Character.forDigit(fps % 10, 10);
				fpsChar[3] = ' ';
				fpsChar[4] = 'f';
				fpsChar[5] = 'p';
				fpsChar[6] = 's';
			} else if(fps > 10) {
				fpsChar = new char[6];
				fpsChar[0] = Character.forDigit(fps / 10, 10);
				fpsChar[1] = Character.forDigit(fps % 10, 10);
				fpsChar[2] = ' ';
				fpsChar[3] = 'f';
				fpsChar[4] = 'p';
				fpsChar[5] = 's';		
			} else {
				fpsChar = new char[5];
				fpsChar[0] = Character.forDigit(fps, 10);
				fpsChar[1] = ' ';
				fpsChar[2] = 'f';
				fpsChar[3] = 'p';
				fpsChar[4] = 's';
			}
			g.drawChars(fpsChar, 0, fpsChar.length, 15, 30);
		}
		Log.v("invalidate");
		for(IPopulation pop : newPops) {
			for(IAgent a : pop.getAgents()) {
				Position p = a.getPosition();
				g.setColor(a.getColor());
		        g.fillOval((int)(p.getX()), (int) (frame.getSize().getHeight() - p.getY()), a.getHeight(), a.getWidth());
			}
		}
		
		g.setColor(Color.black);
		
		int xLeft = 0;
		int xRight = (int)gridDimension.getWidth();
		int yBot = (int)(frame.getSize().getHeight());
		int yTop = (int)(frame.getSize().getHeight())-(int)gridDimension.getHeight();
		g.drawLine(xLeft, yBot, 
				   xLeft, yTop);
		g.drawLine(xLeft, yTop, 
				   xRight, yTop);
		g.drawLine(xRight, yTop,
				   xRight, yBot);
		g.drawLine(xRight, yBot,
				   xLeft, yBot);
    }
	
	/**
	 * Sets the FPS counter visible or not visible
	 * 
	 * @param visible
	 */
	public void setFPSCounterVisible(boolean visible) {
		if(showFPS && !visible) {
				fpsTimer.cancel();
				showFPS = visible;
		} else if(!showFPS && visible) {
			fpsTimer = new Timer();
			fpsTimer.schedule(new TimerTask() {
				@Override
				public void run() {
					fps = updates;
					updates = 0;
				}
			}, 1000, 1000);
			showFPS = true;
		}
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addController(ActionListener controller) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onTick() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void release() {
		// TODO Auto-generated method stub
		
	}

}
