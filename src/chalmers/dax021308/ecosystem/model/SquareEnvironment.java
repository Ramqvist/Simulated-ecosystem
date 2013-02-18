package chalmers.dax021308.ecosystem.model;

import java.util.ArrayList;
import java.util.List;

/**
 * SquareEnvironment Class. Represents a environment in the shape of a square
 * 
 * @author Henrik
 * 
 */
public class SquareEnvironment implements IEnvironment {
	List<IPopulation> populations;
	List<IObstacle> obstacles;

	public SquareEnvironment() {
		this(new ArrayList<IPopulation>(), new ArrayList<IObstacle>());
	}

	public SquareEnvironment(List<IPopulation> populations,
			List<IObstacle> obstacles) {
		this.populations = populations;
		this.obstacles = obstacles;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean isFree(Position p) {
		// TODO Auto-generated method stub
		return false;
	}

}
