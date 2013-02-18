package chalmers.dax021308.ecosystem.model;

/**
 * Obstacle Class.
 * 
 * @author Henrik
 * 
 */
public class Obstacle implements IObstacle {
	private String obstacleType;

	// Int[][] makes the representation weird since we want start and finish of
	private int obstacleRange[][];

	// boolean makes lookup easy, harder to initiate, also makes the arrays
	// really big and require a lot of memory
	private boolean obstacleRangeBool[][];

	// Easiest way is probably creating a new Pair<L,R> class and making some
	// sort of List<Pair>[]. This way it will be decently intuitive to create
	// and do lookups in, but wont eat up memory space

	public Obstacle() {
		// Doesn't make much sense to have an obstacle without specifying
		// size/name and not knowing the size of the environment
		this(new int[100][100], "Unspecified Obstacle");
	}

	public Obstacle(int[][] range) {
		this(range, "Unspecified Obstacle");
	}

	public Obstacle(String type) {
		this(new int[100][100], type);
	}

	public Obstacle(int[][] range, String type) {
		obstacleRange = range;
		obstacleType = type;
	}

	@Override
	public boolean insideObstacle(Position p) {
		// If using boolean:
		return obstacleRangeBool[p.getX()][p.getY()];
	}
}
