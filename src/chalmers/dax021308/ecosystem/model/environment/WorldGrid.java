package chalmers.dax021308.ecosystem.model.environment;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.util.Position;

/**
 * WorldGrid contains a grid with agents to simplify the locating
 * of other agents in the vicinity of a particular agent. This can be
 * used for e.g. collision detection. The class is thread safe.
 * @author Albin
 */
public class WorldGrid {
	
	private ArrayList<ArrayList<ArrayList<IAgent>>> grid;
	private int rows;
	private int columns;
	private Dimension dimension;
	private int scale;
	private Semaphore lock;
	
	/**
	 * Creates a new WorldGrid.
	 * @param dim - the {@link Dimension} which the grid will cover.
	 * @param scale - the scale of the grid. The grid becomes smaller for larger values. 
	 * If scale % 2 != 0 weird things will happen.
	 */
	public WorldGrid(Dimension dim, int scale) {
		lock = new Semaphore(1);
		dimension = dim;
		this.scale = scale;
		rows = dim.height / scale + 1;
		columns = dim.width / scale + 1;
		
		grid = new ArrayList<ArrayList<ArrayList<IAgent>>>(rows);
		
		for (int i = 0; i < rows; i++) {
			ArrayList<ArrayList<IAgent>> temp = new ArrayList<ArrayList<IAgent>>(columns);
			for (int j = 0; j < columns; j++) {
				temp.add(new ArrayList<IAgent>());
			}
			grid.add(temp);
		}
		
		System.out.println("Dim: "+dimension.height + ", "+dimension.width);
		System.out.println("Rows: "+rows);
		System.out.println("Cols: "+columns);
		System.out.println("Height: "+grid.size());
		System.out.println("Width: "+grid.get(0).size());
		System.out.println("Depth: "+grid.get(0).get(0).size());
//		System.exit(0);
	}
	
	/**
	 * Adds an agent to the grid.
	 * @param agent - the {@link IAgent} to be added.
	 */
	public void add(IAgent agent) {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int row = (int)(agent.getPosition().getY() / scale);
		int column = (int)(agent.getPosition().getX() / scale);
		grid.get(row).get(column).add(agent);
		lock.release();
	}
	
	/**
	 * Removes and agent from the grid.
	 * @param agent - the {@link IAgent} to be removed.
	 */
	public void remove(IAgent agent) {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int row = (int)(agent.getPosition().getY() / scale);
		int column = (int)(agent.getPosition().getX() / scale);
		grid.get(row).get(column).remove(agent);
		lock.release();
	}
	
	/**
	 * Checks if an agent has moved enough to have its position updated in the grid, 
	 * and if so the position is updated.<p> 
	 * The higher the scale of the grid is, the larger the squares will be and the position
	 * doesn't have to be updated as often. 
	 * @param agent - the {@link IAgent} that will be updated.
	 * @param oldPosition - the previous {@link Position} of the agent.
	 * @param newPosition - the new {@link Position} of the agent.
	 * @return true if the position was updated, otherwise false.
	 */
	public boolean updatePosition(IAgent agent, Position oldPosition, Position newPosition) {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int oldRow = (int)(oldPosition.getY() / scale);
		int oldCol = (int)(oldPosition.getX() / scale);
		int newRow = (int)(newPosition.getY() / scale);
		int newCol = (int)(newPosition.getX() / scale);
		
		if (!(oldRow == newRow && oldCol == newCol)) {
			grid.get(oldRow).get(oldCol).remove(agent);
			grid.get(newRow).get(newCol).add(agent);
			lock.release();
			return true;
		} else {
			lock.release();
			return false;
		}
	}
	
	/**
	 * @param p - the {@link Position} that will be checked.
	 * @return A {@link List} of {@link IAgent}s at {@link Position} p.
	 */
	public List<IAgent> get(Position p) {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int row = (int)(p.getY() / scale);
		int col = (int)(p.getX() / scale);
		lock.release();
		return grid.get(row).get(col);
	}
	
	/**
	 * @param positions - {@link List} of {@link Position}s
	 * @return A {@link List} with {@link List}s of {@link IAgent}s for the {@link Position}s in positions.
	 */
	public List<List<IAgent>> get(List<Position> positions) {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<List<IAgent>> result = new ArrayList<List<IAgent>>();
		
		for (int i = 0; i < positions.size(); i++) {
			if (!result.contains(get(positions.get(i)))) {
				result.add(get(positions.get(i)));
			}
		}
		lock.release();
		return result;
	}
	
	public int getColumns() {
		return columns;
	}
	
	public Dimension getDimension() {
		return dimension;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getScale() {
		return scale;
	}
}














