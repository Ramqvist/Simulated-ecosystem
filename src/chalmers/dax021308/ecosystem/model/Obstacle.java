package chalmers.dax021308.ecosystem.model;

import java.util.List;

/**
 * Obstacle Class.
 * 
 * @author Henrik
 * 
 */
public class Obstacle implements IObstacle {

	private String obstacleType;
	private List<Pair>[] obstacles;

	public Obstacle(String type, List<Pair>[] obstacles) {
		obstacleType = type;
		this.obstacles = obstacles;
	}

	@Override
	public boolean insideObstacle(Position p) {
		// TODO proper calculation depending on the type of obstacleRange
		return false;
	}

	@Override
	public String getObstacleType() {
		return obstacleType;
	}
}
