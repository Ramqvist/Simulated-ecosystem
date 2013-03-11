package chalmers.dax021308.ecosystem.model.environment;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Semaphore;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.model.util.Position;

/**
 * WorldGrid contains a grid with agents to simplify the locating
 * of other agents in the vicinity of a particular agent. This can be
 * used for e.g. collision detection. The class is thread safe.
 * <p>Tip for use: (1) add agents to grid, (2) get agents at desired position,
 * (3) do something with the agent, (4) update the position for every
 * agent involved. 
 * 
 * <p>
 * Concurrent version of WorldGrid, with better concurrency support. 
 * Several readers are allowed. But only 1 writer. 
 * The algorithm is from Concurrent Control with "Readers" and "Writers" P.J. Courtois,* F. H, 1971
 * http://cs.nyu.edu/~lerner/spring10/MCP-S10-Read04-ReadersWriters.pdf
 * //Erik
 * <p>
 * @author Albin
 */
public class WorldGridConcurrent {
	
	private ArrayList<ArrayList<ArrayList<IAgent>>> grid;
	private int rows;
	private int columns;
	private Dimension dimension;
	private int scale;
	public static WorldGridConcurrent worldGrid;
	
	/* Concurrency variables */
	private Semaphore r = new Semaphore(1);
	private Semaphore w = new Semaphore(1);
	
	private Semaphore mutex1     = new Semaphore(1);
	private Semaphore mutex2     = new Semaphore(1);
	private Semaphore mutex3     = new Semaphore(1);
	
	private int numReaders = 0;
	private int numWriters = 0;
	
	/**
	 * The first instance will be empty, call init() to set up.
	 * @return An instance of WorldGrid.
	 */
	public static WorldGridConcurrent getInstance() {
		if (worldGrid != null) {
			return worldGrid;
		} else {
			worldGrid = new WorldGridConcurrent();
			return worldGrid;
		}
	}
	
	private WorldGridConcurrent() {
		
	}
	
	private void startRead() {
		try {
			mutex3.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			r.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		try {
			mutex1.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		numReaders++;
		if (numReaders == 1) {
			try {
				w.acquire();
			} catch (InterruptedException e) {
			}
		}
		mutex1.release();
		r.release();
		mutex3.release();
	}

	private void stopRead() {
		try {
			mutex1.acquire();
		} catch (InterruptedException e) {e.printStackTrace();}
				numReaders--;
				if(numReaders == 0) {
					w.release();
				}
			mutex1.release();
	}
	
	private void startWrite() {
		try {
			mutex2.acquire();
		} catch (InterruptedException e) {
		}
		numWriters++;
		if (numWriters == 1) {
			try {
				r.acquire();
			} catch (InterruptedException e) {
			}
		}
		mutex2.release();
		try {
			w.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void stopWrite() {
		w.release();
		try {
			mutex2.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		numWriters--;
		if (numWriters == 0) {
			r.release();
		}
		mutex2.release();
	}
	
	/**
	 * Initializes the singleton. Only call this once.
	 * @param dim - The {@link Dimension} the grid will cover. 
	 * @param scale - The scale of the grid. scale % 2 == 0 must be true.
	 */
	public void init(Dimension dim, int scale) {
		startWrite();
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
		stopWrite();
	}
	
	/**
	 * Adds an agent to the grid.
	 * @param agent - the {@link IAgent} to be added.
	 */
	public void add(IAgent agent) {
		startWrite();
		int row = (int)(agent.getPosition().getY() / scale);
		int column = (int)(agent.getPosition().getX() / scale);
		grid.get(row).get(column).add(agent);
		stopWrite();
	}
	
	/**
	 * Adds agents from a list to the world grid
	 * @param agents - the list of {@link IAgent}s to be added.
	 */
	public void addAll(List<IAgent> agents) {
		startWrite();
		for(IAgent a: agents){
			int row = (int)(a.getPosition().getY() / scale);
			int column = (int)(a.getPosition().getX() / scale);
			grid.get(row).get(column).add(a);
		}
		stopWrite();
	}
	
	/**
	 * Removes and agent from the grid.
	 * @param agent - the {@link IAgent} to be removed.
	 */
	public void remove(IAgent agent) {
		startWrite();
		int row = (int)(agent.getPosition().getY() / scale);
		int column = (int)(agent.getPosition().getX() / scale);
		grid.get(row).get(column).remove(agent);
		stopWrite();
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
		startWrite();
		int oldRow = (int)(oldPosition.getY() / scale);
		int oldCol = (int)(oldPosition.getX() / scale);
		int newRow = (int)(newPosition.getY() / scale);
		int newCol = (int)(newPosition.getX() / scale);
		if (!(oldRow == newRow && oldCol == newCol)) {
			grid.get(oldRow).get(oldCol).remove(agent);
			grid.get(newRow).get(newCol).add(agent);
			stopWrite();
			return true;
		} else {
			stopWrite();
			return false;
		}
	}
	
	/**
	 * @param center - The {@link Position} of the current {@link IAgent}. 
	 * @param visionRange - The vision range of the {@link IAgent}.
	 * @return The surrounding {@link IAgent}s within the vision range.
	 */
	public List<List<IAgent>> get(Position center, double visionRange) {
		startRead();
		List<List<IAgent>> result = new ArrayList<List<IAgent>>();
		int range = (int)(visionRange / scale);
		int row = (int)(center.getY() / scale);
		int col = (int)(center.getX() / scale);
		
		for (int i = row - range; i <= row + range; i++) {
			for (int j = col - range; j <= col + range; j++) {
				if (i >= 0 && i < rows && j >= 0 && j < columns) {
					result.add(grid.get(i).get(j));
				}
			}
		}
		stopRead();
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
	
	public int getNumberOfAgents() {
		startRead();
		int sum = 0;
		for (int i = 0; i < grid.size(); i++) {
			for (int j = 0; j < grid.get(i).size(); j++) {
				sum += grid.get(i).get(j).size();
			}
		}
		stopRead();
		return sum;
	}
}














