package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.Gender;
import chalmers.dax021308.ecosystem.model.util.Log;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * 
 * @author Henrik Abstract class for handling the dummy methods
 */
public abstract class AbstractAgent implements IAgent {
	protected Position position;
	protected Position nextPosition;
	protected Random ran;
	protected String name;
	protected Color color;
	protected int width;
	protected int height;
	protected int capacity;
	protected Vector velocity;
	protected Gender gender;
	protected double fitness;
	protected double maxSpeed;
	protected double visionRange;
	protected double maxAcceleration;
	protected boolean groupBehaviour;
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
		ran = new Random();
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
	public void updatePosition() {
		this.position = new Position(nextPosition);
	}

	@Override
	public Gender getGender() {
		return gender;
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
					Dimension dim) {
			}

			@Override
			public List<IAgent> reproduce(IAgent agent, int populationSize) {
				return Collections.emptyList();
			}

		};
		return a;
	}

	/**
	 * A random force that the agent gets influenced by.
	 * Can be interpreted as an estimation error that the
	 * agent does in where to head.
	 * @return a vector pointing approximately in the
	 * same direction as the agents velocity.
	 */
	protected Vector randomForce(){
		double randX = -RANDOM_FORCE_MAGNITUDE+ 2*RANDOM_FORCE_MAGNITUDE*Math.random();
		double randY = -RANDOM_FORCE_MAGNITUDE+ 2*RANDOM_FORCE_MAGNITUDE*Math.random();
		return new Vector(velocity.x+randX, velocity.y+randY);
	}
	
	/**
	 * @author Sebbe
	 * The agent is influences by the mutual interaction force 
	 * because it is subject to attraction and repulsion 
	 * from other individuals that it wants to group with.
	 * This force describes the relationship between the tendency
	 * to steer towards other groups of agents, 
	 * but not be to close to them either.
	 * @param neutral the population of neutral agents.
	 * @return a vector with the force that this agent feels from other neutral
	 * agents in that it interacts with.
	 */
	protected Vector mutualInteractionForce(List<IPopulation> neutral) {
		Vector mutualInteractionForce = new Vector(0,0);
		Vector newForce = new Vector(0,0);
		IPopulation pop;
		int popSize = neutral.size();
		for (int j = 0; j < popSize; j++) {
			pop = neutral.get(j);
			int size = pop.getAgents().size();
			List<IAgent> agents = pop.getAgents();
			IAgent agent;
			for (int i = 0; i < size; i++) {
				agent = agents.get(i);
				if (agent != this) {
					Position p = agent.getPosition();
					double distance = getPosition().getDistance(p);
					double Q = 0; //Q is a function of the distance.
					if (distance <= visionRange) {
						if(distance <= INTERACTION_RANGE){
							Q = -10*(INTERACTION_RANGE-distance);
						} else {
							Q = 3;
						}
					}			
					newForce.x = p.getX()-this.getPosition().getX();
					newForce.y = p.getY()-this.getPosition().getY();
					double norm = newForce.getNorm();
					double v = Q/(norm*distance);
					newForce.x = newForce.x * v;
					newForce.y = newForce.y * v;
					mutualInteractionForce.x = ( mutualInteractionForce.x + newForce.x ) * ( ran.nextDouble()  + ran.nextDouble() ); 
					mutualInteractionForce.y = ( mutualInteractionForce.y + newForce.y ) * ( ran.nextDouble()  + ran.nextDouble() );
				}
			}
		}
		return mutualInteractionForce;
	}

	/**
	 * @author Sebbe
	 * The tendency of an agent to continue moving forward with its velocity.
	 * @return the forward thrust force.
	 */
	protected Vector forwardThrust(){
		double a = 0.5;
		double x = velocity.x;
		double y = velocity.y;
		double norm = velocity.getNorm();
		Vector forwardThrust = new Vector(a*x/norm,a*y/norm);
		return forwardThrust;
	}
	
	/**
	 * @author Sebbe
	 * This is the force that makes neighbouring agents 
	 * to equalize their velocities and therefore go in
	 * the same direction. The sphere of incluence is
	 * defined as 2*INTERACTION_RANGE at the moment.
	 * @param neutral the population of neutral agents.
	 * @return a vector with the force influencing the agents
	 * to steer in the same direction as other nearby agents.
	 */
	protected Vector arrayalForce(List<IPopulation> neutral){
		Vector arrayalForce = new Vector(0,0);
		Vector newForce = new Vector();
		IPopulation pop;
		int popSize = neutral.size();
		double nAgentsInVision = 0;
		for (int j = 0; j < popSize; j++) {
			pop = neutral.get(j);
			int size = pop.getAgents().size();
			List<IAgent> agents = pop.getAgents();
			IAgent agent;
			for (int i = 0; i < size; i++) {
				agent = agents.get(i);
				if (agent != this) {
					Position p = agent.getPosition();
					double distance = getPosition().getDistance(p);
					if (distance <= INTERACTION_RANGE*2) {
						newForce.setVector(0,0);
						newForce.add(agent.getVelocity());
						newForce.add(velocity);
						double h = 10;
						newForce.x *= h;
						newForce.y *= h;
						double randomSmoothFactor =  (( ran.nextDouble()  + ran.nextDouble() ) + ( ran.nextDouble()  + ran.nextDouble() ) ) / 2;
						arrayalForce.x = ( arrayalForce.x + newForce.x ) * randomSmoothFactor;
						arrayalForce.y = ( arrayalForce.y + newForce.y ) * randomSmoothFactor;
						nAgentsInVision = nAgentsInVision + 1.0;
					}
				}
			}
		}
		if(nAgentsInVision>0){
			arrayalForce.x /= nAgentsInVision;
			arrayalForce.y /= nAgentsInVision;
		}
		return arrayalForce;
	}
	
	/**
	 * * @author Sebbe The environment force is at the moment defined as
	 * 1/(wall-constant*(distance to wall)^2). The agents feel the forces from
	 * the wall directly to the left, right, top and bottom.
	 * 
	 * @param dim
	 *            The dimensions of the rectangular environment.
	 * @return A vector with the force that an agent feel from its environment.
	 */
	protected Vector getEnvironmentForce(Dimension dim) {
		/*
		 * The positions below is just an orthogonal projection on to the walls.
		 */
		Position xWallLeft = new Position(0, this.getPosition().getY());
		Position xWallRight = new Position(dim.getWidth(), this.getPosition()
				.getY());
		Position yWallBottom = new Position(this.getPosition().getX(), 0);
		Position yWallTop = new Position(this.getPosition().getX(),
				dim.getHeight());

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
			distance = leftWallDistance/WALL_CONSTANT;
			xWallLeftForce = 1/(distance*distance);
		}

		double rightWallDistance = this.getPosition().getDistance(xWallRight);
		if (rightWallDistance <= INTERACTION_RANGE) {
			distance = rightWallDistance/WALL_CONSTANT;
			xWallRightForce = -1/(distance*distance);
		}

		double bottomWallDistance = this.getPosition().getDistance(yWallBottom);
		if (bottomWallDistance <= INTERACTION_RANGE) {
			distance = bottomWallDistance/WALL_CONSTANT;
			yWallBottomForce = 1/(distance*distance);
		}

		double topWallDistance = this.getPosition().getDistance(yWallTop);
		if (topWallDistance <= INTERACTION_RANGE) {
			distance = topWallDistance/WALL_CONSTANT;
			yWallBottomForce = yWallTopForce = -1/(distance*distance);
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
					Dimension dim) {

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
