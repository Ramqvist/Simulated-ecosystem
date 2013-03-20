package chalmers.dax021308.ecosystem.model.environment;

import java.awt.Dimension;

/**
 * Container class for various simulation settings.
 * 
 * @author Erik
 *
 */
public class SimulationSettings {
	public static final SimulationSettings DEFAULT;
	public static final SimulationSettings LARGESIM;
	
	static {
		DEFAULT = new SimulationSettings(EcoWorld.POP_WOLF, 10, EcoWorld.POP_DEER, 100, EcoWorld.POP_GRASS, 400, EcoWorld.SHAPE_SQUARE, EcoWorld.ELLIPTICAL_OBSTACLE);
		LARGESIM = new SimulationSettings(EcoWorld.POP_WOLF, 100, EcoWorld.POP_DEER, 1000, EcoWorld.POP_GRASS, 4000, EcoWorld.SHAPE_SQUARE, EcoWorld.NO_OBSTACLE);
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

	public SimulationSettings(String predatorModel, int predPopSize, String preyModel, int preyPopSize, String grassModel, int grassPopSize, String shapeModel, String obstacle) {
		this.predatorModel = predatorModel;
		this.predPopSize = predPopSize;
		this.preyModel = preyModel;
		this.preyPopSize = preyPopSize;
		this.grassModel = grassModel;
		this.grassPopSize = grassPopSize;
		this.shapeModel = shapeModel;
		this.obstacle = obstacle;
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
	
	
}
