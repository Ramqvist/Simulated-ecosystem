package chalmers.dax021308.ecosystem.model.environment;

import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

import chalmers.dax021308.ecosystem.model.agent.AbstractAgent;
import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.population.AbstractPopulation;
import chalmers.dax021308.ecosystem.model.population.DeerPopulation;
import chalmers.dax021308.ecosystem.model.population.DeerPopulationGrid;
import chalmers.dax021308.ecosystem.model.population.DummyPredatorPopulation;
import chalmers.dax021308.ecosystem.model.population.DummyPreyPopulation;
import chalmers.dax021308.ecosystem.model.population.GrassPopulation;
import chalmers.dax021308.ecosystem.model.population.GrassPopulationGrid;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.population.PigPopulation;
import chalmers.dax021308.ecosystem.model.population.RabbitPopulation;
import chalmers.dax021308.ecosystem.model.population.WolfPopulation;
import chalmers.dax021308.ecosystem.model.population.WolfPopulationGrid;
import chalmers.dax021308.ecosystem.model.util.CircleShape;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.SquareShape;
import chalmers.dax021308.ecosystem.model.util.Stat;
import chalmers.dax021308.ecosystem.model.util.TimerHandler;
import chalmers.dax021308.ecosystem.model.util.TriangleShape;

/**
 * Ecosystem main class.
 * <p>
 * Recieves notifications from the {@link TimerHandler} and the
 * {@link IEnvironment}.
 * <p>
 * Use the proper constructor for the wanted behavior of EcoWorld.
 * 
 * 
 * @author Erik Ramqvist
 * 
 */
public class EcoWorld implements IModel {

	//Please don't auto-indent this class, thanks.
	
	/* Property change events constants */
	public static final String EVENT_TICK               = "chalmers.dax021308.ecosystem.model.Ecoworld.event_tick";
	public static final String EVENT_STOP               = "chalmers.dax021308.ecosystem.model.Ecoworld.event_stop";
	public static final String EVENT_START              = "chalmers.dax021308.ecosystem.model.Ecoworld.event_start";
	public static final String EVENT_PAUSE              = "chalmers.dax021308.ecosystem.model.Ecoworld.event_pause";
	public static final String EVENT_RECORDING_FINISHED = "chalmers.dax021308.ecosystem.model.Ecoworld.event_recordingstarted";
	public static final String EVENT_RECORDING_STARTED  = "chalmers.dax021308.ecosystem.model.Ecoworld.event_recordingfinished";
	public static final String EVENT_DIMENSIONCHANGED   = "chalmers.dax021308.ecosystem.model.Ecoworld.event_dimensionchanged";
	public static final String EVENT_DELAY_CHANGED      = "chalmers.dax021308.ecosystem.model.Ecoworld.event_delaychanged";
	public static final String EVENT_SHAPE_CHANGED      = "chalmers.dax021308.ecosystem.model.Ecoworld.event_shape_changed";
	public static final String EVENT_ITERATION_FINISHED = "chalmers.dax021308.ecosystem.model.Ecoworld.event_iterationfinished";
	
	/* Shape Constants */
	public static final String SHAPE_SQUARE   = "Square Shape";
	public static final String SHAPE_CIRCLE   = "Circle Shape";
	public static final String SHAPE_TRIANGLE = "Triangle Shape";

	/* Population constants */
	public static final String POP_PIG        = "Pig Population";
	public static final String POP_RABBIT     = "Rabbit Population";
	public static final String POP_DUMMYPREY  = "Dummy Prey Population";
	public static final String POP_DEER       = "Deer Population";
	public static final String POP_DEER_GRID  = "Deer Population Grid";
	public static final String POP_GRASS      = "Grass Population";
	public static final String POP_GRASS_GRID = "Grass Population Grid";
	public static final String POP_DUMMYPRED  = "Dummy Predator Population";
	public static final String POP_WOLF       = "Wolf Population";
	public static final String POP_WOLF_GRID  = "Wolf Population Grid";

	/* Population array based on predator-prey model, the view uses these values. */
	public static final String[] PREY_VALUES  = { POP_DEER, POP_DEER_GRID, POP_PIG, POP_RABBIT, POP_DUMMYPREY };
	public static final String[] PRED_VALUES  = { POP_WOLF, POP_WOLF_GRID, POP_DUMMYPRED };
	public static final String[] GRASS_VALUES = { POP_GRASS, POP_GRASS_GRID };
	
	public static final String RECTANGULAR_OBSTACLE = "Rectangular obstacle";
	public static final String ELLIPTICAL_OBSTACLE = "Elliptical obstacle"; 
	public static final String[] OBSTACLE_VALUES = {RECTANGULAR_OBSTACLE, ELLIPTICAL_OBSTACLE};
	
	/* Dimension constants */
	public static final String DIM_SMALL  = "500  x 500";
	public static final String DIM_MEDIUM = "1000 x 1000";
	public static final String DIM_LARGE  = "1500 x 1500";
	public static final String DIM_XLARGE = "2000 x 2000";

	private static final Dimension D_SMALL  = new Dimension(500, 500);
	private static final Dimension D_MEDIUM = new Dimension(1000, 1000);
	private static final Dimension D_LARGE  = new Dimension(1500, 1500);
	private static final Dimension D_XLARGE = new Dimension(2000, 2000);

	public static final String[] DIM_VALUES = { DIM_SMALL, DIM_MEDIUM, DIM_LARGE, DIM_XLARGE };

	/* State variables */
	private AtomicBoolean environmentFinished = new AtomicBoolean(false);
	private AtomicBoolean timerFinished       = new AtomicBoolean(false);
	private AtomicBoolean shouldRun           = new AtomicBoolean(false);
	private boolean runWithoutTimer;
	private boolean recordSimulation;
	private boolean skipBoolean;

	/* Simulation settings */
	private int numIterations;
	private TimerHandler timer;
	private IEnvironment env;
	private int tickTime;
	private PropertyChangeSupport observers;
	private List<IPopulation> recycledPopulationList;

	/* Time measurements variables (in ns).*/
	private long startIterationTime;
	private long elapsedTime;
	private ArrayList<Integer> recordedTime = new ArrayList<Integer>(512);

	/**
	 * Each list in the list contains one snapshot of frame;
	 */
	private List<List<IPopulation>> recordedSimulation;
	/**
	 * Simple object, used for synchronizing the {@link TimerHandler} and the
	 * {@link IEnvironment} {@link OnFinishListener}
	 */
	private Object syncObject = new Object();
	private int numUpdates = 0;
	private Dimension d;
	private ExecutorService executor;

	private OnFinishListener mOnFinishListener = new OnFinishListener() {


		@Override
		public void onFinish(List<IPopulation> popList, List<IObstacle> obsList) {
			if (!shouldRun.get()) {
				return;
			}
			elapsedTime = System.nanoTime() - startIterationTime;
			// Fire state changed to observers, notify there has been an update.
			if (recordSimulation) {
				recordedSimulation.add(AbstractPopulation.clonePopulationList(popList));
			} else {
				//Send out the new cloned population list and obstacle list.
//				recycledPopulationList = AbstractPopulation.clonePopulationListWithRecycledList(recycledPopulationList, popList);
				observers.firePropertyChange(EVENT_TICK, obsList, AbstractPopulation.clonePopulationList(popList));
				observers.firePropertyChange(EVENT_ITERATION_FINISHED, null, elapsedTime);
			}
			if (runWithoutTimer) {
				scheduleEnvironmentUpdate();
			} else {
				synchronized (syncObject) {
					// Log.v("Environment: Finished.");
					if (timerFinished.get()) {
						// Log.v("Environment: Timer is finished, doing Environment update");
						environmentFinished.set(false);
						timerFinished.set(false);
						scheduleEnvironmentUpdate();
					} else {
						// Log.v("Environment: Timer NOT finished, waiting...");
						environmentFinished.set(true);
					}
				}
			}
		}
	};

	private OnTickUpdate onTickListener = new OnTickUpdate() {
		@Override
		// När timer är klar.
		public void onTick() {
			if (!shouldRun.get()) {
				return;
			}
			synchronized (syncObject) {
				// Log.v("Timer: Finished.");
				if (environmentFinished.get()) {
					// Log.v("Timer: Environment is finished, doing Environment update");
					timerFinished.set(false);
					environmentFinished.set(false);
					scheduleEnvironmentUpdate();
				} else {
					// Log.v("Timer: Environment NOT finished, waiting...");
					timerFinished.set(true);
				}
			}
		}
	};
	private int numThreads;

	/**
	 * Start EcoWorld with a tick-timer.
	 * 
	 * @param tickTime
	 *            Minimum time it will take for one tick to complete.
	 * @param numIterations
	 *            Number of iterations before the program finishes.
	 * 
	 * @param d
	 *            Dimension of the simulation.
	 */
	public EcoWorld(Dimension d, int tickTime, int numIterations,
			boolean recordSimulation) {
		this.tickTime = tickTime;
		this.timer = new TimerHandler();
		this.d = d;
		this.recordSimulation = recordSimulation;
		if (recordSimulation) {
			// For recording in half fps.
			if (skipBoolean) {
				recordedSimulation = new ArrayList<List<IPopulation>>(
						numIterations);
				skipBoolean = false;
			} else {
				skipBoolean = true;
			}
		}

		WorldGrid.getInstance().init(d, 20);

		this.runWithoutTimer = false;
		this.numIterations = numIterations;

		this.observers = new PropertyChangeSupport(this);
	}

	public void setRecordSimulation(boolean recordSimulation) {
		this.recordSimulation = recordSimulation;
	}

	public synchronized void setNumIterations(int numIterations) {
		this.numIterations = numIterations;
	}

	public synchronized void setSimulationDimension(Dimension d) {
		this.d = d;
		observers.firePropertyChange(EVENT_DIMENSIONCHANGED, null, d);
		WorldGrid.getInstance().init(d, 20);
	}

	public synchronized void setSimulationDimension(String dimConstant) {
		if (dimConstant == DIM_XLARGE) {
			d = D_XLARGE;
		} else if (dimConstant == DIM_LARGE) {
			d = D_LARGE;
		} else if (dimConstant == DIM_MEDIUM) {
			d = D_MEDIUM;
		} else if (dimConstant == DIM_SMALL) {
			d = D_SMALL;
		}
		observers.firePropertyChange(EVENT_DIMENSIONCHANGED, null, d);
		WorldGrid.getInstance().init(d, 20);
	}

	/**
	 * Start EcoWorld WITHOUT a tick-timer.
	 * <p>
	 * EcoWorld simulation will run as fast as it can, without delays.
	 * 
	 * @param numIterations
	 *            Number of iterations before the program finishes.
	 * 
	 * @param d
	 *            Dimension of the simulation.
	 */
	public EcoWorld(Dimension d, int numIterations) {
		this(d, 0, numIterations, false);
		this.runWithoutTimer = true;
	}

	/**
	 * Start EcoWorld WITHOUT a tick-timer.
	 * <p>
	 * EcoWorld simulation will run as fast as it can, without delays. For a
	 * very long time.
	 * 
	 * @param d
	 *            Dimension of the simulation.
	 * 
	 */
	public EcoWorld(Dimension d) {
		this(d, Integer.MAX_VALUE);
	}

	public void createInitialPopulations(String predatorModel, int predPop,
			String preyModel, int preyPop, String grassModel, int grassPop,
			String shapeModel, String Obstacle) throws IllegalArgumentException {
		List<IPopulation> populations = new ArrayList<IPopulation>();
		
		/*
		 * Creating obstacles here for test. This should be done in a proper way later.
		 */
		List<IObstacle> obstacles = new ArrayList<IObstacle>();
		
		

		IPopulation prey = null;
		IPopulation pred = null;
		IPopulation grass = null;
		IShape shape = null;
		
		if(Obstacle == ELLIPTICAL_OBSTACLE){
			obstacles.add(new EllipticalObstacle(200, 150, new Position(500,500)));
		} else if (Obstacle == RECTANGULAR_OBSTACLE) {
			obstacles.add(new RectangularObstacle(200, 150, new Position(320,500)));
		}
		
		if (shapeModel == SHAPE_SQUARE) {
			shape = new SquareShape();
			observers.firePropertyChange(EVENT_SHAPE_CHANGED, null, shape);
		} else if (shapeModel == SHAPE_CIRCLE) {
			shape = new CircleShape();
			observers.firePropertyChange(EVENT_SHAPE_CHANGED, null, shape);
		}else if (shapeModel == SHAPE_TRIANGLE){
			shape = new TriangleShape();
			observers.firePropertyChange(EVENT_SHAPE_CHANGED, null, shape);
		}
		if (predatorModel == POP_DUMMYPRED) {
			pred = new DummyPredatorPopulation(d, predPop, Color.red, 3,
					0.75, 275, shape);
		} else if (predatorModel == POP_WOLF) {
			pred = new WolfPopulation("Wolves", d, predPop, Color.red, 3,
					0.8, 250, true, shape, obstacles);
		} else if (predatorModel == POP_WOLF_GRID) {
			pred = new WolfPopulationGrid("Wolves", d, predPop, Color.red, 3,
					0.8, 250, true, shape);
		}

		if (preyModel == POP_DEER) {
			prey = new DeerPopulation("Deers", d, preyPop, Color.blue, 2.0, 2,
					200, true, shape, obstacles);
		} else if (preyModel == POP_DEER_GRID) {
			prey = new DeerPopulationGrid("Deers", d, preyPop, Color.blue, 2.0,
					2, 200, true, shape);
		} else if (preyModel == POP_RABBIT) {
			prey = new RabbitPopulation("Rabbits", d, preyPop, Color.lightGray,
					10, 10, 200, shape);
		} else if (preyModel == POP_DUMMYPREY) {
			prey = new DummyPreyPopulation(d, preyPop, Color.blue, 2.2, 2, 250, shape);
		} else if (preyModel == POP_PIG) {
			prey = new PigPopulation("Filthy Pigs", d, preyPop, Color.pink,
					2.0, 1.5, 225, shape);
		}

		if (grassModel == POP_GRASS) {
			grass = new GrassPopulation("Grass", d, grassPop, new Color(69,139,00), 1,
					1, 0, 1500, shape, obstacles);
		} else if (grassModel == POP_GRASS_GRID) {
			grass = new GrassPopulationGrid("Grass", d, grassPop, new Color(69,139,00),
					1, 1, 0, 1500, shape);
		}

		if (prey == null || pred == null || grass == null || shape == null) {
			throw new IllegalArgumentException("Wrong populations set.");
		}

		prey.addPredator(pred);
		prey.addPrey(grass);

		pred.addPrey(prey);
		populations.add(grass);
		populations.add(prey);
		populations.add(pred);

		if (recordSimulation) {
			recordedSimulation = new ArrayList<List<IPopulation>>(
					numIterations / 2);
		}
		this.env = new SquareEnvironment2(populations, obstacles,
				mOnFinishListener, d.height, d.width, numThreads);
	}

	private List<IObstacle> readObsticlesFromFile() {
		List<IObstacle> obsList = new ArrayList<IObstacle>();
		obsList.add(new EllipticalObstacle(0,0, new Position()));
		return obsList;
	}

	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

	public int getNumThreads() {
		return numThreads;
	}

	/**
	 * Start the EcoWorld simulation program.
	 * <p>
	 * If already started {@link IllegalStateException} will be thrown.
	 * 
	 */
	public void start() throws IllegalStateException {
		synchronized (syncObject) {
			if (!shouldRun.get()) {
				executor = Executors.newSingleThreadExecutor();
				this.timer = new TimerHandler();
				shouldRun.set(true);
				scheduleEnvironmentUpdate();
				Log.i("EcoWorld started.");
				if (recordSimulation) {
					observers.firePropertyChange(EVENT_RECORDING_FINISHED,
							null, null);
				} else {
					observers.firePropertyChange(EVENT_START, null, null);
				}
			} else {
				throw new IllegalStateException("EcoWorld already started.");
			}
		}
	}

	/**
	 * Stops the scheduling algorithms.
	 * <p>
	 * Warning! WILL affect ongoing execution!
	 * <p>
	 * If already stopped {@link IllegalStateException} will be thrown.
	 * 
	 */
	public void pause() throws IllegalStateException {
		synchronized (syncObject) {
			if (shouldRun.get()) {
				shouldRun.set(false);
				executor.shutdownNow();
				timer.stop();
				numUpdates = 0;
				Log.i("EcoWorld paused.");
				observers.firePropertyChange(EVENT_PAUSE, null, null);
				if (recordSimulation) {
					observers.firePropertyChange(EVENT_RECORDING_FINISHED,
							null, null);
				}
			} else {
				throw new IllegalStateException("EcoWorld already paused.");
			}
		}
	}

	/**
	 * Stops the scheduling algorithms.
	 * <p>
	 * Warning! WILL affect ongoing execution!
	 * <p>
	 * If already stopped {@link IllegalStateException} will be thrown.
	 * 
	 */
	public void stop() throws IllegalStateException {
		synchronized (syncObject) {
			if (shouldRun.get()) {
				shouldRun.set(false);
				executor.shutdownNow();
				timer.stop();
				numUpdates = 0;
				Log.i("EcoWorld stopped.");
				observers.firePropertyChange(EVENT_STOP, null, null);
				if (recordSimulation) {
					observers.firePropertyChange(EVENT_RECORDING_FINISHED,
							null, null);
				}
			} else {
				throw new IllegalStateException("EcoWorld already stopped");
			}
		}
	}

	/**
	 * Plays the recorded simulation (if any). Assumes the recording is recorded
	 * in half fps.
	 * <P>
	 * Uses internal {@link TimerHandler} for smooth playing.
	 */
	public void playRecordedSimulation(final List<List<IPopulation>> recordedSim) {
		final TimerHandler t = new TimerHandler();

		t.start(17, new OnTickUpdate() {
			@Override
			public void onTick() {
				if (recordedSim.size() > 0) {
					List<IPopulation> popList = recordedSim.get(0);
					recordedSim.remove(0);
					observers.firePropertyChange(EVENT_TICK,
							Collections.emptyList(), popList);
					t.start(32, this);
				} else {
					t.stop();
					observers.firePropertyChange(EVENT_STOP,
							Collections.emptyList(), Collections.emptyList());
				}
			}
		});
	}

	/**
	 * Starts the {@link TimerHandler} and executes one Environment iteration.
	 * <p>
	 * (Gets the ball running.)
	 */
	private void scheduleEnvironmentUpdate() {
		if (numIterations-- > 0) {
			if (!runWithoutTimer) {
				timer.start(tickTime, onTickListener);
			}
			StringBuffer sb = new StringBuffer(
					"---- Simulation model Update ---- Number of updates: ");
			sb.append(++numUpdates);
			if (startIterationTime != 0) {
				sb.append(" - Iteration time:");
				sb.append((long) (0.000001 * elapsedTime));
				sb.append(" ms.");
				recordedTime.add((int) (0.000001 * elapsedTime));
				sb.append(" mean value: ");
				sb.append(Stat.mean(recordedTime));
				sb.append(" sample variance: ");
				sb.append(Stat.sampleVariance(recordedTime));
			}
			Log.v(sb.toString());
			executor.execute(env);
			startIterationTime = System.nanoTime();
		} else {
			stop();
			if (recordSimulation) {
				playRecordedSimulation(recordedSimulation);
			}
		}
	}

	/**
	 * Adjust the tick rate of the next iteration. The currently executing
	 * iteration will not be affected.
	 * 
	 * @param newTickRate
	 */
	public void setDelayLength(int newDelay) {
		synchronized (syncObject) {
			this.tickTime = newDelay;
		}
		observers.firePropertyChange(EVENT_DELAY_CHANGED, null, newDelay);
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

	public Dimension getSize() {
		return d;
	}

	/**
	 * Environment onFinish listener. Called when one iteration of the
	 * Environment is done.
	 * 
	 * @author Erik
	 * 
	 */
	public interface OnFinishListener {
		public void onFinish(List<IPopulation> popList,
				List<IObstacle> obstacleList);
	}

	@Override
	public void addObserver(PropertyChangeListener listener) {
		observers.addPropertyChangeListener(listener);
	}

	@Override
	public void removeObserver(PropertyChangeListener listener) {
		observers.removePropertyChangeListener(listener);
	}


	/**
	 * Plays the loaded simulation, or throws {@link IllegalStateException} if
	 * not any recording is loaded.
	 */
	public void playRecentLoadedSimulation() throws IllegalStateException {
		if (recordedSimulation != null && !recordedSimulation.isEmpty()) {
			playRecordedSimulation(recordedSimulation);
		} else {
			throw new IllegalStateException("No recording loaded");
		}
	}

	/**
	 * Reads the recording from the given filePath.
	 * <p>
	 * Untested! Unfinished!
	 * 
	 * @param filePath
	 * @return True if success, otherwise false.
	 */
	public boolean readRecordFromDisk(String filePath) {
		String frameDivider = "FRAME";
		String populationDivider = "POPULATION";
		String agentDivider = "AGENT";
		File f = new File(filePath);
		if (!f.exists()) {
			return false;
		}
		if (!f.canRead()) {
			return false;
		}
		try {
			FileInputStream fileStream = new FileInputStream(f);
			Charset utf8 = Charset.forName("UTF-8");
			BufferedReader br = new BufferedReader(new InputStreamReader(
					fileStream, utf8));
			List<List<IPopulation>> readInput = parseFile(br);
			recordedSimulation = readInput;
			br.close();
			fileStream.close();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}

	/**
	 * Helper method for readRecordFromDisk.
	 * 
	 * Unfinished! Untested!
	 * @param br
	 * @return
	 */
	private List<List<IPopulation>> parseFile(BufferedReader br) {
		String frameDivider = "FRAME";
		String populationDivider = "POPULATION";
		String agentDivider = "AGENT";

		List<List<IPopulation>> result = new ArrayList<List<IPopulation>>();

		List<IPopulation> currentFrame = null;
		IPopulation currentPop = null;

		String input = null;
		try {
			input = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		while (input != null) {
			try {
				input = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (input.startsWith(frameDivider)) {
				if (currentFrame != null) {
					result.add(currentFrame);
				}
				currentFrame = new ArrayList<IPopulation>();
			} else if (input.startsWith(populationDivider)) {
				if (currentPop != null) {
					currentFrame.add(currentPop);
				}
				String[] inputArr = input.split(";", 2);
				currentPop = AbstractPopulation.createFromFile(inputArr[1]);
			} else if (input.startsWith(agentDivider)) {
				if (currentPop != null) {
					String[] inputArr = input.split(";", 2);
					IAgent newIAgent = AbstractAgent
							.createFromFile(inputArr[1]);
					currentPop.getAgents().add(newIAgent);
				}
			}
		}
		return result;
	}

	/**
	 * Saves the given recording to the filePath
	 * 
	 * Unfinished! Untested!
	 * 
	 * @param record
	 * @param filePath
	 * @return
	 */
	public boolean dumpRecordToDisk(List<List<IPopulation>> record,
			String filePath) {
		String frameDivider = "FRAME";
		String populationDivider = "POPULATION";
		String agentDivider = "AGENT";
		File f = new File(filePath);
		if (f.exists()) {
			f.delete();
		}
		try {
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		for (List<IPopulation> popList : record) {
			pw.println(frameDivider);
			for (IPopulation p : popList) {
				pw.println(populationDivider + ';' + p.toBinaryString());
				for (IAgent a : p.getAgents()) {
					pw.println(agentDivider + ';' + a.toBinaryString());
				}
			}
		}
		pw.close();
		return true;
	}
}
