package chalmers.dax021308.ecosystem.model.environment;

import java.awt.Dimension;

/**
 * Container class for various simulation settings.
 * 
 * @author Erik Ramqvist
 *
 */
public class SimulationSettings {
	
	/* Static presets profiles */
	public static final SimulationSettings DEFAULT;
	public static final SimulationSettings LARGESIM;
	
	
	/* Shape Constants */
	public static final String SHAPE_SQUARE   = "Square Shape";
	public static final String SHAPE_CIRCLE   = "Circle Shape";
	public static final String SHAPE_TRIANGLE = "Triangle Shape";

	/* Population constants */
	public static final String POP_PIG        = "Pig Population";
	public static final String POP_DUMMYPREY  = "Dummy Prey Population";
	public static final String POP_DEER       = "Deer Population";
	public static final String POP_DEER_GRID  = "Deer Population Grid";
	public static final String POP_GRASS      = "Grass Population";
	public static final String POP_GRASS_GRID = "Grass Population Grid";
	public static final String POP_DUMMYPRED  = "Dummy Predator Population";
	public static final String POP_WOLF       = "Wolf Population";
	public static final String POP_WOLF_GRID  = "Wolf Population Grid";

	/* Population array based on predator-prey model, the view uses these values. */
	public static final String[] PREY_VALUES  = { POP_DEER, POP_DEER_GRID, POP_PIG, POP_DUMMYPREY };
	public static final String[] PRED_VALUES  = { POP_WOLF, POP_WOLF_GRID, POP_DUMMYPRED };
	public static final String[] GRASS_VALUES = { POP_GRASS, POP_GRASS_GRID };
	
	/*Obstacle constants */
	public static final String RECTANGULAR_OBSTACLE = "Rectangular obstacle";
	public static final String ELLIPTICAL_OBSTACLE = "Elliptical obstacle";
	public static final String NO_OBSTACLE = "No obstacle";
	public static final String[] OBSTACLE_VALUES = {NO_OBSTACLE, RECTANGULAR_OBSTACLE, ELLIPTICAL_OBSTACLE};
	
	/* Dimension constants */
	public static final String DIM_SMALL  = "500  x 500";
	public static final String DIM_MEDIUM = "1000 x 1000";
	public static final String DIM_LARGE  = "1500 x 1500";
	public static final String DIM_XLARGE = "2000 x 2000";

	public static final Dimension D_SMALL  = new Dimension(500, 500);
	public static final Dimension D_MEDIUM = new Dimension(1000, 1000);
	public static final Dimension D_LARGE  = new Dimension(1500, 1500);
	public static final Dimension D_XLARGE = new Dimension(2000, 2000);

	public static final String[] DIM_VALUES = { DIM_SMALL, DIM_MEDIUM, DIM_LARGE, DIM_XLARGE };
	
	static {
		DEFAULT = new SimulationSettings(POP_WOLF, 10, POP_DEER, 100, POP_GRASS, 400, SHAPE_SQUARE, ELLIPTICAL_OBSTACLE, 4, false, false, 16, Integer.MAX_VALUE);
		DEFAULT.setSimulationDimension(new Dimension(1000, 1000));
		LARGESIM = new SimulationSettings(POP_WOLF, 100, POP_DEER, 1000, POP_GRASS, 4000, SHAPE_SQUARE, NO_OBSTACLE, 4, false, false, 16, Integer.MAX_VALUE);
		LARGESIM.setSimulationDimension(new Dimension(2200, 2200));
	}
	
	private String predatorModel;
	private int predPopSize;
	private String preyModel;
	private int preyPopSize;
	private String grassModel;
	private int grassPopSize;
	private String shapeModel;
	private String obstacle;
	private Dimension simDimension;
	private String simDimensionConstant;
	private int numThreads;
	private boolean runWithoutTimer;
	private boolean recordSimulation;
	private int delayLength;
	private int numIterations;

	public SimulationSettings(String predatorModel, int predPopSize, String preyModel, int preyPopSize, String grassModel, int grassPopSize, String shapeModel, String obstacle, int numThreads, boolean runWithoutTimer, boolean recordSimulation, int delayLength, int numIterations) {
		this.predatorModel = predatorModel;
		this.predPopSize = predPopSize;
		this.preyModel = preyModel;
		this.preyPopSize = preyPopSize;
		this.grassModel = grassModel;
		this.grassPopSize = grassPopSize;
		this.shapeModel = shapeModel;
		this.obstacle = obstacle;
		this.numThreads = numThreads;
		this.runWithoutTimer = runWithoutTimer;
		this.recordSimulation = recordSimulation;
		this.delayLength = delayLength;
		this.numIterations = numIterations;
	}
	
	public int getDelayLength() {
		return delayLength;
	}
	
	public void setDelayLength(int delayLength) {
		this.delayLength = delayLength;
	}
	
	public int getNumIterations() {
		return numIterations;
	}
	
	public void setNumIterations(int numIterations) {
		this.numIterations = numIterations;
	}

	public boolean isRecordSimulation() {
		return recordSimulation;
	}
	
	public boolean isRunWithoutTimer() {
		return runWithoutTimer;
	}
	
	public void setSimulationDimension(Dimension d) {
		this.simDimension = d;
		WorldGrid.getInstance().init(d, 20);
	}

	public void setSimulationDimension(String simDimensionConstant) {
		this.simDimensionConstant = simDimensionConstant;
	}
	
	public Dimension getSimDimension() {
		return simDimension;
	}
	
	public String getSimDimensionConstant() {
		return simDimensionConstant;
	}

	public String getPredatorModel() {
		return predatorModel;
	}

	public void setPredatorModel(String predatorModel) {
		this.predatorModel = predatorModel;
	}

	public int getPredPopSize() {
		return predPopSize;
	}

	public void setPredPopSize(int predPopSize) {
		this.predPopSize = predPopSize;
	}

	public String getPreyModel() {
		return preyModel;
	}

	public void setPreyModel(String preyModel) {
		this.preyModel = preyModel;
	}

	public int getPreyPopSize() {
		return preyPopSize;
	}

	public void setPreyPopSize(int preyPopSize) {
		this.preyPopSize = preyPopSize;
	}

	public String getGrassModel() {
		return grassModel;
	}

	public void setGrassModel(String grassModel) {
		this.grassModel = grassModel;
	}

	public int getGrassPopSize() {
		return grassPopSize;
	}

	public void setGrassPopSize(int grassPopSize) {
		this.grassPopSize = grassPopSize;
	}

	public String getShapeModel() {
		return shapeModel;
	}

	public void setShapeModel(String shapeModel) {
		this.shapeModel = shapeModel;
	}

	public String getObstacle() {
		return obstacle;
	}

	public void setObstacle(String obstacle) {
		this.obstacle = obstacle;
	}

	public void setNumThreads(int numThreads) {
		this.numThreads = numThreads;
	}

	public int getNumThreads() {
		return numThreads;
	}
	
	
}
