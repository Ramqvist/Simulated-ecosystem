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
 * <p>Tip for use: (1) add agents to grid, (2) get agents at desired position,
 * (3) do something with the agent, (4) update the position for every
 * agent involved. 
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
	 * @param center - The {@link Position} of the current {@link IAgent}. 
	 * @param visionRange - The vision range of the {@link IAgent}.
	 * @return The surrounding {@link IAgent}s within the vision range.
	 */
	public List<List<IAgent>> get(Position center, double visionRange) {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		List<List<IAgent>> result = new ArrayList<List<IAgent>>();
		int range = (int)Math.pow((visionRange / scale), 2);
		int row = (int)(center.getY() / scale);
		int col = (int)(center.getX() / scale);
		
		for (int i = row - range; i <= row + range; i++) {
			for (int j = col - range; j <= col + range; j++) {
				if (i >= 0 && i < rows && j >= 0 && j < columns) {
					result.add(grid.get(i).get(j));
				}
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
	
	public int getSize() {
		try {
			lock.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		int sum = 0;
		for (int i = 0; i < grid.size(); i++) {
			for (int j = 0; j < grid.get(i).size(); j++) {
				for (int k = 0; k < grid.get(i).get(j).size(); k++) {
					sum++;
				}
			}
		}
		lock.release();
		return sum;
	}
}














