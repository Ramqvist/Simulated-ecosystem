package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import org.lwjgl.LWJGLException;
import org.lwjgl.opencl.CL10;
import org.lwjgl.opencl.CLDevice;
import org.lwjgl.opencl.CLPlatform;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.Range;

import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Gender;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Stat;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * 
 * AbstractAgent with neighbourlist.
 * 
 * @author Henrik Abstract class for handling the dummy methods
 */
public abstract class AbstractAgent implements IAgent {
	protected Position position;
	protected Position nextPosition;
	protected Random ran;
	protected String name;
	protected Color color;
	protected boolean groupBehaviour;
	protected int width;
	protected int height;
	protected int capacity;
	protected int lifeLength;
	protected int energy = 1000; // set specific energy level in subclasses
	protected int trophicLevel;
	protected Vector velocity;
	protected Gender gender;
	protected double fitness;
	protected double maxSpeed;
	protected double visionRange;
	protected double maxAcceleration;
	private boolean isAlive;
	
	/* Neighbour list module variables */
	protected List<IAgent> preyNeighbours;
	protected List<IAgent> predNeighbours;
	protected List<IAgent> neutralNeighbours;
	private int neighbourCounter;
	private MutualTestKernel kernel;
	private Stat<Double> stat;
	private static final int NEIGHBOURS_UPDATE_THRESHOLD = 10;

	protected final static double INTERACTION_RANGE = 10;
	protected final static double WALL_CONSTANT = 2;
	protected static final double VELOCITY_DECAY = 1;
	protected static final double RANDOM_FORCE_MAGNITUDE = 0.05;

	public AbstractAgent(String name, Position p, Color c, int width,
			int height, Vector velocity, double maxSpeed, double visionRange,
			double maxAcceleration) {
		this.name = name;
		position = p;
		color = c;
		this.width = width;
		this.height = height;
		this.velocity = velocity;
		this.maxSpeed = maxSpeed;
		this.visionRange = visionRange;
		this.maxAcceleration = maxAcceleration;
		this.capacity = Integer.MAX_VALUE;
		this.lifeLength = 0;
		ran = new Random();
		this.isAlive = true;
		
		//TODO: STAT REMOVE WHEN FINISHED!
		this.stat = new Stat<Double>();
		
		/* LinkedList for fast changing of Agents, consider ArrayList for less memory */
		preyNeighbours    = new ArrayList<IAgent>(256);
		predNeighbours    = new ArrayList<IAgent>(256);
		neutralNeighbours = new ArrayList<IAgent>(256);
		
		//To update the first time.
		kernel = new MutualTestKernel();
		neighbourCounter = ran.nextInt(NEIGHBOURS_UPDATE_THRESHOLD);
	}

	public AbstractAgent(String name, Position p, Color c, int width,
			int height, Vector velocity, double maxSpeed, double visionRange,
			double maxAcceleration, int capacity, boolean groupBehaviour) {

		this(name, p, c, width, height, velocity, maxSpeed, visionRange,
				maxAcceleration);
		this.capacity = capacity;
		this.groupBehaviour = groupBehaviour;
	}

	/**
	 * Clone constructed. Use this to create a copy.
	 * 
	 * @param a
	 */
	public AbstractAgent(AbstractAgent a) {
		this(a.name, new Position(a.position), a.color, a.width, a.height,
				new Vector(a.velocity), a.maxSpeed, a.visionRange,
				a.maxAcceleration);
	}

	public AbstractAgent(String name, Position pos, Color color, int width,
			int height) {
		this.name = name;
		position = new Position(pos);
		this.color = color;
		this.width = width;
		this.height = height;

		
		/* LinkedList for fast changing of Agents, consider ArrayList for less memory */
		preyNeighbours    = new LinkedList<IAgent>();
		predNeighbours    = new LinkedList<IAgent>();
		neutralNeighbours = new LinkedList<IAgent>();
		
		//To update the first time.
		neighbourCounter = NEIGHBOURS_UPDATE_THRESHOLD;
	}

	@Override
	public Position getPosition() {
		return position;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public Color getColor() {
		return color;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Vector getVelocity() {
		return velocity;
	}

	@Override
	public void setVelocity(Vector velocity) {
		this.velocity = velocity;

	}

	@Override
	public double getFitness() {
		return fitness;
	}

	@Override
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}

	@Override
	public Gender getGender() {
		return gender;
	}

	@Override
	public int getLifeLength() {
		return this.lifeLength;
	}

	public int getEnergy() {
		return energy;
	}
	
	@Override
	public int getTrophicLevel() {
		return trophicLevel;
	}

	@Override
	public IAgent cloneAgent() throws CloneNotSupportedException {
		return (IAgent) clone();
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		AbstractAgent a = new AbstractAgent(name, position, color, width,
				height, velocity, maxSpeed, visionRange, maxAcceleration) {
			@Override
			public void calculateNextPosition(List<IPopulation> predators,
					List<IPopulation> preys, List<IPopulation> neutral,
					Dimension dim, IShape shape) {
			}

			@Override
			public List<IAgent> reproduce(IAgent agent, int populationSize) {
				return Collections.emptyList();
			}

		};
		return a;
	}

	/**
	 * Updates an agents position after all calculations for all agents have
	 * been done.
	 */
	@Override
	public void updatePosition() {
		this.position = new Position(nextPosition);
		this.lifeLength++;
	}
	
	/**
	 * Update the neighbourlist, should be done once in a while.
	 * <p>
	 * Warning, heavy computation!
	 * 
	 * Needs optimizations.
	 * 
	 * @author Erik 
	 */
	public void updateNeighbourList(List<IPopulation> neutral, List<IPopulation> prey, List<IPopulation> pred) {	
		if(neighbourCounter++ < NEIGHBOURS_UPDATE_THRESHOLD) {
			//Don't update just yet.
			return;
		}
//		Log.v("Updating Neighbourlist! Agent: " + toString());
		neighbourCounter = 0;
//		neutralNeighbours = new ArrayList<IAgent>(2*neutralNeighbours.size());
//		predNeighbours = new ArrayList<IAgent>(2*predNeighbours.size());
//		preyNeighbours = new ArrayList<IAgent>(2*preyNeighbours.size());

		neutralNeighbours.clear();
		predNeighbours.clear();
		preyNeighbours.clear();
		
		for(IPopulation p : neutral) {
			for(IAgent a : p.getAgents()) {
				if (a.getPosition().getDistance(position) <= visionRange) {
					neutralNeighbours.add(a);
				}
			}
		}
		
		for(IPopulation p : prey) {
			for(IAgent a : p.getAgents()) {
				if (a.getPosition().getDistance(position) <= visionRange) {
					preyNeighbours.add(a);
				}
			}
		}
		
		for(IPopulation p : pred) {
			for(IAgent a : p.getAgents()) {
				if (a.getPosition().getDistance(position) <= visionRange) {
					predNeighbours.add(a);
				}
			}
		}
	}

	/**
	 * @author Sebbe A random force that the agent gets influenced by. Can be
	 *         interpreted as an estimation error that the agent does in where
	 *         to head.
	 * @return a vector pointing approximately in the same direction as the
	 *         agents velocity.
	 */
	protected Vector randomForce() {
		double randX = -RANDOM_FORCE_MAGNITUDE + 2 * RANDOM_FORCE_MAGNITUDE
				* Math.random();
		double randY = -RANDOM_FORCE_MAGNITUDE + 2 * RANDOM_FORCE_MAGNITUDE
				* Math.random();
		return new Vector(velocity.x + randX, velocity.y + randY);
	}

	/**
	 * @author Sebbe The agent is influences by the mutual interaction force
	 *         because it is subject to attraction and repulsion from other
	 *         individuals that it wants to group with. This force describes the
	 *         relationship between the tendency to steer towards other groups
	 *         of agents, but not be to close to them either.
	 * @param neutral
	 *            the population of neutral agents.
	 * @return a vector with the force that this agent feels from other neutral
	 *         agents in that it interacts with.
	 */
	protected Vector mutualInteractionForce() {
		if(neutralNeighbours.size() == 0) {
			return Vector.EmptyVector();
		}
		long time = System.nanoTime();
		boolean executeCPU = true;
		if(executeCPU) {
			for(int i = 0; i < 10000;i++) {
				mutualInteractionForceCPU();
			}
			double dtime = (0.000001 * (System.nanoTime() - time));
			stat.addObservation(dtime);

			System.out.println("mutualInteractionForce size: " + neutralNeighbours.size() + " Elapsed time CPU: " + dtime + " mean: " + stat.getMean());
			return mutualInteractionForceCPU();
		} else {
			Vector result = mutualInteractionForceJWJGLOpenCL();
//			time = (long) (0.000001 * (System.nanoTime() - time) );
			double dtime = (0.000001 * (System.nanoTime() - time));
			stat.addObservation(dtime);

			CLPlatform platform = CLPlatform.getPlatforms().get(0); 
			// Run our program on the GPU
			List<CLDevice> devices = platform.getDevices(CL10.CL_DEVICE_TYPE_GPU);
			/*for(CLDevice d : devices) {
				Log.v(d.getInfoString(CL10.CL_DEVICE_VENDOR));
				Log.v(d.getInfoInt(CL10.CL_DEVICE_TYPE) + "");
				Log.v("Compute units: " + d.getInfoInt(CL10.CL_DEVICE_MAX_COMPUTE_UNITS) + "");
				Log.v("Compute units: " + d.getInfoInt(CL10.CL_DEVICE_MAX_CLOCK_FREQUENCY) + "");
				Log.v("Name: " + d.getInfoString(CL10.CL_DEVICE_NAME) + "");
				Log.v("Available: " + d.getInfoString(CL10.CL_DEVICE_AVAILABLE) + "");
				Log.v("Memeory size: " + d.getInfoLong(CL10.CL_DEVICE_GLOBAL_MEM_SIZE) + "");
			}*/
			System.out.println("mutualInteractionForce size: " + neutralNeighbours.size() + " Elapsed time GPU: " + dtime + " mean: " + stat.getMean());
			return result;
		}
	}
	
	protected Vector mutualInteractionForceJWJGLOpenCL() {
		final int size = neutralNeighbours.size();
		if(size == 0) {
			return Vector.EmptyVector();
		}
		IAgent agent;
		float[] xPosArray = new float[size];
		float[] yPosArray = new float[size];
	
		for (int i = 0; i < size; i++) {
			agent = neutralNeighbours.get(i);
			if (agent != AbstractAgent.this) {
				Position p = agent.getPosition();
				xPosArray[i] = (float) p.getX();
				yPosArray[i] = (float) p.getY();
			}
		}	
		kernel.setValues(size, (float) INTERACTION_RANGE,(float) getPosition().getX(), (float) getPosition().getY(), xPosArray, yPosArray);
		//kernel.getExecutionTime()
		try {
			kernel.executeMutualKernel();
		} catch (LWJGLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		double mutualInteractionForceX = 0;
		double mutualInteractionForceY = 0;
		for (int i = 0; i < size; i++) {
			mutualInteractionForceX = mutualInteractionForceX + kernel.resultBuffX.get(i);
			mutualInteractionForceY = mutualInteractionForceY + kernel.resultBuffY.get(i);
//			Log.v(kernel.xResult[i] + " " + kernel.yResult[i]);
		}
		final Vector mutualInteractionForce = new Vector(mutualInteractionForceX, mutualInteractionForceY);
		return mutualInteractionForce.multiply((ran.nextDouble() + ran.nextDouble()));
	}
	
	protected Vector mutualInteractionForceGPU() {
		final int size = neutralNeighbours.size();
		if(size == 0) {
			return Vector.EmptyVector();
		}
		IAgent agent;
		double[] xPosArray = new double[size];
		double[] yPosArray = new double[size];
	
		for (int i = 0; i < size; i++) {
			agent = neutralNeighbours.get(i);
			if (agent != AbstractAgent.this) {
				Position p = agent.getPosition();
				xPosArray[i] = p.getX();
				yPosArray[i] = p.getY();
			}
		}	
		MutualInteractionForceKernel kernel = new MutualInteractionForceKernel(size, INTERACTION_RANGE, getPosition().getX(), getPosition().getY(), xPosArray, yPosArray);
		//kernel.getExecutionTime()
		kernel.setExecutionMode(Kernel.EXECUTION_MODE.GPU); // force GPU-mode
		Range range = Range.create(size);
		kernel.execute(range);
		Log.v("Elapsed time: " + kernel.getConversionTime());
		double mutualInteractionForceX = 0;
		double mutualInteractionForceY = 0;
		for (int i = 0; i < size; i++) {
			mutualInteractionForceX = mutualInteractionForceX + kernel.xResult[i];
			mutualInteractionForceY = mutualInteractionForceY + kernel.yResult[i];
//			Log.v(kernel.xResult[i] + " " + kernel.yResult[i]);
		}
		final Vector mutualInteractionForce = new Vector(mutualInteractionForceX, mutualInteractionForceY);
		System.out.println("Execution mode = "+kernel.getExecutionMode());
		//   -Dcom.amd.aparapi.enableShowGeneratedOpenCL=true
	    kernel.dispose();
		return mutualInteractionForce.multiply((ran.nextDouble() + ran.nextDouble()));
	}
	
	protected Vector mutualInteractionForceCPU() {

		final int size = neutralNeighbours.size();
		if(size == 0) {
			return Vector.EmptyVector();
		}
		IAgent agent;
		double[] xPosArray = new double[size];
		double[] yPosArray = new double[size];
	
		for (int i = 0; i < size; i++) {
			agent = neutralNeighbours.get(i);
			if (agent != AbstractAgent.this) {
				Position p = agent.getPosition();
				xPosArray[i] = p.getX();
				yPosArray[i] = p.getY();
			}
		}	
		double mutualInteractionForceX = 0;
		double mutualInteractionForceY = 0;
		double myPosX = getPosition().getX();
		double myPosY = getPosition().getY();
		for (int i = 0; i < size; i++) {
			double newForceX = 0;
			double newForceY = 0;
			double xAgentPosition = xPosArray[i];
			double yAgentPosition = yPosArray[i];
			double v = 0;
			double norm = 0;
			double distance = 0;
			double dX = myPosX - xAgentPosition;
			double dY = myPosY - yAgentPosition;
			
			distance = Math.sqrt(Math.pow(dX, 2.0) + Math.pow(dY, 2.0));
			double Q = 0; // Q is a function of the distance.
			if (distance <= INTERACTION_RANGE) {
				Q = -20.0 * (INTERACTION_RANGE - distance);
			} else {
				Q = 1.0;
			}	
			newForceX = xAgentPosition - myPosX;
			newForceY = yAgentPosition - myPosY;
			norm = Math.sqrt((newForceX*newForceX)+(newForceY*newForceY));
			v = Q / (norm * distance);
			newForceX = newForceX * v;
			newForceY = newForceY * v;
			mutualInteractionForceX = mutualInteractionForceX + newForceX;
			mutualInteractionForceX = mutualInteractionForceY + newForceY;
		}
		final Vector mutualInteractionForce = new Vector(mutualInteractionForceX, mutualInteractionForceY);
		return mutualInteractionForce.multiply((ran.nextDouble() + ran.nextDouble()));
	}
	


	/**
	 * @author Sebbe The tendency of an agent to continue moving forward with
	 *         its velocity.
	 * @return the forward thrust force.
	 */
	protected Vector forwardThrust() {
		double a = 0.1; // Scaling constant
		double x = velocity.x;
		double y = velocity.y;
		double norm = velocity.getNorm();
		Vector forwardThrust = new Vector(a * x / norm, a * y / norm);
		return forwardThrust;
	}

	/**
	 * @author Sebbe This is the force that makes neighbouring agents to
	 *         equalize their velocities and therefore go in the same direction.
	 *         The sphere of incluence is defined as 2*INTERACTION_RANGE at the
	 *         moment.
	 * @param neutral
	 *            the population of neutral agents.
	 * @return a vector with the force influencing the agents to steer in the
	 *         same direction as other nearby agents.
	 */
	protected Vector arrayalForce() {
		Vector arrayalForce = new Vector(0, 0);
		Vector newForce = new Vector();
		double nAgentsInVision = 0;
		int size = neutralNeighbours.size();
		IAgent agent;
		for (int i = 0; i < size; i++) {
			agent = neutralNeighbours.get(i);
			if (agent != this) {
				Position p = agent.getPosition();
				double distance = getPosition().getDistance(p);
				if (distance <= INTERACTION_RANGE * 2) {
					newForce.setVector(0, 0);
					newForce.add(agent.getVelocity());
					newForce.add(velocity);
					double h = 4; // Scaling constant
					newForce.x *= h;
					newForce.y *= h;
					arrayalForce.x = (arrayalForce.x + newForce.x);
					arrayalForce.y = (arrayalForce.y + newForce.y);
					nAgentsInVision = nAgentsInVision + 1.0;
				}
			}
		}
		if (nAgentsInVision > 0) {
			arrayalForce.x /= nAgentsInVision;
			arrayalForce.y /= nAgentsInVision;
		}
		double randomSmoothFactor = ((ran.nextDouble() + ran.nextDouble()) + (ran
				.nextDouble() + ran.nextDouble())) / 2;
		return arrayalForce.multiply(randomSmoothFactor);
	}

	/**
	 * * @author Sebbe The environment force is at the moment defined as
	 * 1/((wall-constant)*(distance to wall))^2. The agents feel the forces from
	 * the wall directly to the left, right, top and bottom.
	 * 
	 * @param dim
	 *            The dimensions of the rectangular environment.
	 * @return A vector with the force that an agent feel from its environment.
	 */
	protected Vector getEnvironmentForce(Dimension dim, IShape shape) {
		/*
		 * The positions below is just an orthogonal projection on to the walls.
		 */
		Position xWallLeft = shape.getXWallLeft(dim, position);
		Position xWallRight = shape.getXWallRight(dim, position);
		Position yWallBottom = shape.getYWallBottom(dim, position);
		Position yWallTop = shape.getYWallTop(dim, position);

		/*
		 * There is a "-1" in the equation just to make it more unlikely that
		 * they actually make it to the wall, despite the force they feel (can
		 * be interpreted as they stop 1 pixel before the wall).
		 */
		Vector environmentForce = new Vector(0, 0);
		double xWallLeftForce = 0;
		double xWallRightForce = 0;
		double yWallBottomForce = 0;
		double yWallTopForce = 0;

		/*
		 * Only interacts with walls that are closer than INTERACTION_RANGE.
		 */
		double distance = 1;
		double leftWallDistance = this.getPosition().getDistance(xWallLeft);
		if (leftWallDistance <= INTERACTION_RANGE) {
			distance = leftWallDistance / WALL_CONSTANT;
			xWallLeftForce = 1 / (distance * distance);
		}

		double rightWallDistance = this.getPosition().getDistance(xWallRight);
		if (rightWallDistance <= INTERACTION_RANGE) {
			distance = rightWallDistance / WALL_CONSTANT;
			xWallRightForce = -1 / (distance * distance);
		}

		double bottomWallDistance = this.getPosition().getDistance(yWallBottom);
		if (bottomWallDistance <= INTERACTION_RANGE) {
			distance = bottomWallDistance / WALL_CONSTANT;
			yWallBottomForce = 1 / (distance * distance);
		}

		double topWallDistance = this.getPosition().getDistance(yWallTop);
		if (topWallDistance <= INTERACTION_RANGE) {
			distance = topWallDistance / WALL_CONSTANT;
			yWallBottomForce = yWallTopForce = -1 / (distance * distance);
		}

		/*
		 * Add the forces from left and right to form the total force from walls
		 * in x-axis. Add the forces from top and bottom to form the total force
		 * from walls in y-axis. Create a force vector of the forces.
		 */
		double xForce = (xWallLeftForce + xWallRightForce);
		double yForce = (yWallBottomForce + yWallTopForce);
		environmentForce.setVector(xForce, yForce);

		return environmentForce;
	}

	/**
	 * Try to consume this agent
	 * <p>
	 * Return true if consumed, otherwise false.
	 * <p>
	 * Thread-safe
	 */
	@Override
	public synchronized boolean consumeAgent() {
		if (isAlive) {
			isAlive = false;
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isAlive() {
		return isAlive;
	}

	/**
	 * Create a new IAgent from the raw parameters.
	 * 
	 * @return
	 */
	public static IAgent createFromFile(String input) {
		String[] inputArray = input.split(";");
		String name = inputArray[0];
		Position pos = new Position(Integer.parseInt(inputArray[1]),
				Integer.parseInt(inputArray[2]));
		Color c = new Color(Integer.parseInt(inputArray[3]),
				Integer.parseInt(inputArray[4]),
				Integer.parseInt(inputArray[5]));
		int width = Integer.parseInt(inputArray[6]);
		int height = Integer.parseInt(inputArray[7]);
		Vector v = new Vector(Integer.parseInt(inputArray[8]),
				Integer.parseInt(inputArray[9]));
		int maxSpeed = Integer.parseInt(inputArray[10]);
		int visionRange = Integer.parseInt(inputArray[11]);
		int maxAcceleration = Integer.parseInt(inputArray[12]);

		AbstractAgent ab = new AbstractAgent(name, pos, c, width, height, v,
				maxSpeed, visionRange, maxAcceleration) {

			@Override
			public List<IAgent> reproduce(IAgent agent, int populationSize) {
				return null;
			}

			@Override
			public void calculateNextPosition(List<IPopulation> predators,
					List<IPopulation> preys, List<IPopulation> neutral,
					Dimension dim, IShape shape) {

			}
		};
		return ab;
	}

	@Override
	public String toBinaryString() {
		StringBuilder sb = new StringBuilder();
		sb.append(name);
		sb.append(';');
		sb.append(position.getX());
		sb.append(';');
		sb.append(position.getY());
		sb.append(';');
		sb.append(color.getRed());
		sb.append(';');
		sb.append(color.getGreen());
		sb.append(';');
		sb.append(color.getBlue());
		sb.append(';');
		sb.append(width);
		sb.append(';');
		sb.append(height);
		sb.append(';');
		sb.append(velocity.x);
		sb.append(';');
		sb.append(velocity.y);
		sb.append(';');
		sb.append(maxSpeed);
		sb.append(';');
		sb.append(visionRange);
		sb.append(';');
		sb.append(maxAcceleration);
		return sb.toString();
	}

}
