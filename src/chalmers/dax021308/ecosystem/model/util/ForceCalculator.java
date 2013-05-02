package chalmers.dax021308.ecosystem.model.util;

import java.awt.Dimension;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.environment.SurroundingsSettings;
import chalmers.dax021308.ecosystem.model.environment.obstacle.AbstractObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.util.shape.IShape;

/**
 * A class made for containing the methods for the forces which need to be
 * calculated for the agents
 * 
 * @author Henrik
 * 
 */
public class ForceCalculator {
	private static final double RANDOM_FORCE_MAGNITUDE = 0.05;
	private final static double INTERACTION_RANGE = 11;
	private final static double ENVIRONMENT_CONSTANT = 50;
	private final static double OBSTACLE_CONSTANT = 50;
	private final static double EATING_RANGE = 5;
	private final static double FOCUS_RANGE = 100;

	/**
	 * A random force that the agent gets influenced by. Can be interpreted as
	 * an estimation error that the agent does in where to head.
	 * 
	 * @return A vector pointing approximately in the same direction as the
	 *         agents velocity.
	 * @author Sebbe
	 * @param velocity
	 */
	public static Vector randomForce(Vector velocity) {
		double randX = -RANDOM_FORCE_MAGNITUDE + 2 * RANDOM_FORCE_MAGNITUDE
				* Math.random();
		double randY = -RANDOM_FORCE_MAGNITUDE + 2 * RANDOM_FORCE_MAGNITUDE
				* Math.random();
		return new Vector(velocity.x + randX, velocity.y + randY);
	}

	/**
	 * The agent is influences by the mutual interaction force because it is
	 * subject to attraction and repulsion from other individuals that it wants
	 * to group with. This force describes the relationship between the tendency
	 * to steer towards other groups of agents, but not be to close to them
	 * either.
	 * 
	 * @param neutralNeighbours
	 *            - The population of neutral agents.
	 * @param currentAgent
	 *            - The current agent, who the force impacts
	 * @return A vector with the force that this agent feels from other neutral
	 *         agents in that it interacts with.
	 * @author Sebbe
	 */
	public static Vector mutualInteractionForce(List<IAgent> neutralNeighbours,
			IAgent currentAgent) {
		Vector mutualInteractionForce = new Vector();
		Vector newForce = new Vector();
		IAgent agent;
		int nrOfNeighbours = neutralNeighbours.size();
		for (int i = 0; i < nrOfNeighbours; i++) {
			agent = neutralNeighbours.get(i);
			if (agent != currentAgent) {
				Position p = agent.getPosition();
				double distance = currentAgent.getPosition().getDistance(p);
				double Q = 0; // Q is a function of the distance.
				if (distance <= INTERACTION_RANGE) {
					Q = -20 * (INTERACTION_RANGE - distance);
				} else {
					Q = 1;
				}
				newForce.x = p.getX() - currentAgent.getPosition().getX();
				newForce.y = p.getY() - currentAgent.getPosition().getY();
				double norm = newForce.getNorm();
				double v = Q / (norm * distance);
				newForce.x = newForce.x * v;
				newForce.y = newForce.y * v;
				mutualInteractionForce.x = (mutualInteractionForce.x + newForce.x);
				mutualInteractionForce.y = (mutualInteractionForce.y + newForce.y);
			}
		}
		return mutualInteractionForce;
	}

	/**
	 * The environment force is at the moment defined as
	 * 1/((wall-constant)*(distance to wall))^2. The agents feel the forces from
	 * the wall directly to the left, right, top and bottom.
	 * 
	 * @param dim
	 *            - The dimensions of the rectangular environment.
	 * @return A vector with the force that an agent feel from its environment.
	 * @author Sebbe
	 */
	public static Vector getEnvironmentForce(Dimension dim, IShape shape,
			Position position) {
		/*
		 * The positions below is just an orthogonal projection on to the walls.
		 */
		Position xWallLeft = shape.getXWallLeft(dim, position);
		Position xWallRight = shape.getXWallRight(dim, position);
		Position yWallBottom = shape.getYWallBottom(dim, position);
		Position yWallTop = shape.getYWallTop(dim, position);

		Vector environmentForce = new Vector();
		double xWallLeftForce = 0;
		double xWallRightForce = 0;
		double yWallBottomForce = 0;
		double yWallTopForce = 0;

		/*
		 * Only interacts with walls that are closer than INTERACTION_RANGE.
		 */
		double leftWallDistance = position.getDistance(xWallLeft);
		if (leftWallDistance <= INTERACTION_RANGE) {
			xWallLeftForce = 1 / (leftWallDistance * leftWallDistance);
		}

		double rightWallDistance = position.getDistance(xWallRight);
		if (rightWallDistance <= INTERACTION_RANGE) {
			xWallRightForce = -1 / (rightWallDistance * rightWallDistance);
		}

		double bottomWallDistance = position.getDistance(yWallBottom);
		if (bottomWallDistance <= INTERACTION_RANGE) {
			yWallBottomForce = 1 / (bottomWallDistance * bottomWallDistance);
		}

		double topWallDistance = position.getDistance(yWallTop);
		if (topWallDistance <= INTERACTION_RANGE) {
			yWallBottomForce = yWallTopForce = -1
					/ (topWallDistance * topWallDistance);
		}

		/*
		 * Add the forces from left and right to form the total force from walls
		 * in x-axis. Add the forces from top and bottom to form the total force
		 * from walls in y-axis. Create a force vector of the forces.
		 */
		double xForce = (xWallLeftForce + xWallRightForce);
		double yForce = (yWallBottomForce + yWallTopForce);
		environmentForce.setVector(xForce, yForce);

		return environmentForce.multiply(ENVIRONMENT_CONSTANT);
	}

	/**
	 * The tendency of an agent to continue moving forward with its velocity.
	 * 
	 * @param velocity
	 *            The velocity of the agent who calls upon this method
	 * 
	 * @return The forward thrust force.
	 * @author Sebbe
	 */
	public static Vector forwardThrust(Vector velocity) {
		double a = 0.1; // Scaling constant
		double x = velocity.x;
		double y = velocity.y;
		double norm = velocity.getNorm();
		Vector forwardThrust = new Vector(a * x / norm, a * y / norm);
		return forwardThrust;
	}

	/**
	 * This is the force that makes neighbouring agents to equalize their
	 * velocities and therefore go in the same direction. The sphere of
	 * incluence is defined as 2*INTERACTION_RANGE at the moment.
	 * 
	 * @param neutralNeighbours
	 *            - The list of neutral agents
	 * 
	 * @param currentAgent
	 *            - The current agent
	 * 
	 * @return a vector with the force influencing the agents to steer in the
	 *         same direction as other nearby agents.
	 * @author Sebbe
	 */
	public static Vector arrayalForce(List<IAgent> neutralNeighbours,
			IAgent currentAgent) {
		Vector arrayalForce = new Vector();
		Vector newForce = new Vector();
		double nAgentsInVision = 0;
		int nrOfNeighbours = neutralNeighbours.size();
		IAgent agent;
		for (int i = 0; i < nrOfNeighbours; i++) {
			agent = neutralNeighbours.get(i);
			if (agent != currentAgent) {
				Position p = agent.getPosition();
				double distance = currentAgent.getPosition().getDistance(p);
				if (distance <= INTERACTION_RANGE * 2) {
					newForce.setVector(0, 0);
					newForce.add(agent.getVelocity());
					newForce.add(currentAgent.getVelocity());
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
		return arrayalForce;
	}

	/**
	 * This is the force that obstacles have upon the agent
	 * 
	 * @param obstacles
	 *            The list of obstacles
	 * @param position
	 *            The position of the agent
	 * @return
	 */
	public static Vector getObstacleForce(List<IObstacle> obstacles,
			Position position) {
		Vector obstacleForce = new Vector();
		for (IObstacle o : obstacles) {
			if (o.isCloseTo(position, INTERACTION_RANGE)) {
				Position obstaclePos = o.closestBoundary(position);
				double distance = position.getDistance(obstaclePos);
				if (distance <= INTERACTION_RANGE) {
					Vector singleForce = new Vector(position, obstaclePos);
					singleForce.toUnitVector().multiply(
							1 / (distance * distance));
					obstacleForce.add(singleForce);
				}
			}
		}
		return obstacleForce.multiply(OBSTACLE_CONSTANT);
	}

	/**
	 * This is the force that all the preys together have on the agent
	 * 
	 * @param willFocusPreys
	 *            - True if the agent focuses on one prey, otherwise false
	 * @param focusedPrey
	 *            - The prey the agent focuses on, if willFocusPreys is true.
	 * @param currentAgent
	 *            - The current agent
	 * @param preyNeighbours
	 *            - The list of preys
	 * @param visionRange
	 *            - The visionrange of the agent
	 * @param maxAcceleration
	 *            - The maxAcceleration of the agent
	 * @return
	 */	
	public static Vector getPreyForce(boolean willFocusPreys,
			Container<IAgent> focusedPreyContainer, IAgent currentAgent,
			List<IAgent> preyNeighbours, double visionRange,
			double maxAcceleration) {
		if (willFocusPreys && focusedPreyContainer.get() != null
				&& focusedPreyContainer.get().isAlive()) {
			Position p = focusedPreyContainer.get().getPosition();
			double distance = currentAgent.getPosition().getDistance(p);
			// double size = (agent.getHeight() + agent.getWidth()) / 4;
			double size = 0;
			if (distance <= EATING_RANGE - size) {
				if (focusedPreyContainer.get().tryConsumeAgent()) {
					focusedPreyContainer.clear();
					currentAgent.eat();
				}
			} else {
				return new Vector(focusedPreyContainer.get().getPosition(),
						currentAgent.getPosition());
			}
		} else {
			focusedPreyContainer.clear();
		}
		Vector preyForce = new Vector(0, 0);
		IAgent closestFocusPrey = null;
		int nrOfPreys = preyNeighbours.size();
		for (int i = 0; i < nrOfPreys; i++) {
			IAgent a = preyNeighbours.get(i);
			Position p = a.getPosition();
			double preySize = (a.getHeight() + a.getWidth()) / 4;
			double distance = currentAgent.getPosition().getDistance(p)
					- preySize;
			if (a.isLookingTasty(currentAgent, visionRange)) {
				if (distance <= EATING_RANGE) {
					if (a.tryConsumeAgent()) {
						currentAgent.eat();
					}
				} else if (willFocusPreys && distance <= FOCUS_RANGE) {
					if (closestFocusPrey != null && a.isAlive()) {
						if (closestFocusPrey.getPosition().getDistance(
								currentAgent.getPosition()) > a.getPosition()
								.getDistance(currentAgent.getPosition())) {
							closestFocusPrey = a;
						}
					} else {
						closestFocusPrey = a;
					}
				} else if (closestFocusPrey == null) {
					/*
					 * Create a vector that points towards the prey.
					 */
					Vector newForce = new Vector(p, currentAgent.getPosition());
					

					/*
					 * Add this vector to the prey force, with proportion to how
					 * close the prey is. Closer preys will affect the force
					 * more than those far away.
					 */
					double norm = newForce.getNorm();
					newForce.multiply(a.impactForcesBy());
					preyForce.add(newForce.multiply(1 / (norm * distance)));
				}
			}
		}
		double norm = preyForce.getNorm();
		if (norm != 0) {
			preyForce.multiply(maxAcceleration / norm);
		}
		if (willFocusPreys && closestFocusPrey != null) {
			focusedPreyContainer.set(closestFocusPrey);
			return new Vector(closestFocusPrey.getPosition(),
					currentAgent.getPosition());
		}
		return preyForce;
	}
	
	public static Vector getPreyForce(boolean willFocusPreys, SurroundingsSettings surroundings, 
			Container<IAgent> focusedPreyContainer, IAgent currentAgent,
			List<IAgent> preyNeighbours, double visionRange, double focusRange,
			double maxAcceleration, double maxSpeed,
			AgentPath focusedPreyPath, int initial_ttl) {
		
		if (willFocusPreys && focusedPreyContainer.get() != null && focusedPreyContainer.get().isAlive()) {
			Position p = focusedPreyContainer.get().getPosition();
			double distance = currentAgent.getPosition().getDistance(p);
			//double size = (agent.getHeight() + agent.getWidth()) / 4;
			double size = 0;
			if (distance <= EATING_RANGE - size && currentAgent.isHungry()) { //Eat agent
				if (focusedPreyContainer.get().tryConsumeAgent()) {
					focusedPreyContainer.set(null);
					currentAgent.eat();
				}
			} else {
				if(!focusedPreyPath.isEmpty() && focusedPreyPath.isValid()) {
					Position nextPathPosition = focusedPreyPath.peek();
					if(nextPathPosition.getDistance(currentAgent.getPosition()) < maxSpeed) {
						if(focusedPreyPath.size() > 1) {
							//Remove the next path, we are close to it, and go to next.
//							System.out.println("Go to next point in path.");
							focusedPreyPath.pop();
							nextPathPosition = focusedPreyPath.peek();
						} else {
//							System.out.println("Last point in path reached.");
							nextPathPosition = focusedPreyPath.pop();
						}
					}
					focusedPreyPath.decreaseTTL();
					
//					System.out.println(focusedPreyPath.getTTL());
//					System.out.println(position + " to " +nextPathPosition);
					return new Vector(nextPathPosition, currentAgent.getPosition());
				} else {
					if(AbstractObstacle.isInsidePathList(surroundings.getObstacles(), currentAgent.getPosition(), focusedPreyContainer.get().getPosition())) {
						focusedPreyPath.setPath(
								Position.getShortestPath(currentAgent.getPosition(), focusedPreyContainer.get().getPosition(), 
										surroundings.getObstacles(), surroundings.getWorldShape(), 
										surroundings.getGridDimension(), surroundings.getObstacleSafetyDistance())
								,initial_ttl);
						if(focusedPreyPath.isEmpty()) {
//							System.out.println("NULL VECTOR");
							return Vector.emptyVector();
						} else {
//							System.out.println("New Path");
//							System.out.println(currentAgent.getPosition());
//							System.out.println(focusedPreyPath);
							focusedPreyPath.pop();
							focusedPreyPath.pop();
							return new Vector(focusedPreyPath.peek(), currentAgent.getPosition());
						}
					} else {
//						System.out.println("No Path");
						focusedPreyPath.clearPath();
						return new Vector(focusedPreyContainer.get().getPosition(), currentAgent.getPosition());
					}
				}
			}
		}
		
		Vector preyForce = new Vector(0, 0);
		IAgent closestFocusPrey = null;
		int nrOfPreys = preyNeighbours.size();
		for (int i = 0; i < nrOfPreys; i++) {
			IAgent a = preyNeighbours.get(i);
			Position p = a.getPosition();
//			double preySize = (a.getHeight() + a.getWidth()) / 4;
			double distance = currentAgent.getPosition().getDistance(p); // - preySize;
			if (a.isLookingTasty(currentAgent, visionRange)) {
				if (distance <= EATING_RANGE && currentAgent.isHungry()) {
					if (a.tryConsumeAgent()) {
						currentAgent.eat();
					}
				} else if (willFocusPreys && distance <= focusRange) {
					if (closestFocusPrey != null && a.isAlive()) {
						if (closestFocusPrey.getPosition().getDistance(
								currentAgent.getPosition()) > a.getPosition()
								.getDistance(currentAgent.getPosition())) {
							closestFocusPrey = a;
						}
					} else {
						closestFocusPrey = a;
					}
				} else if (closestFocusPrey == null) {
					/*
					 * Create a vector that points towards the prey.
					 */
					Vector newForce = new Vector(p, currentAgent.getPosition());
	
					/*
					 * Add this vector to the prey force, with proportion to how
					 * close the prey is. Closer preys will affect the force
					 * more than those far away.
					 */
					double norm = newForce.getNorm();
					preyForce.add(newForce.multiply(1 / (norm * distance)));
				}
			}
		}
		double norm = preyForce.getNorm();
		if (norm != 0) {
			preyForce.multiply(maxAcceleration / norm);
		}
		if (willFocusPreys && closestFocusPrey != null) {
			focusedPreyContainer.set(closestFocusPrey);
			if(AbstractObstacle.isInsidePathList(surroundings.getObstacles(), currentAgent.getPosition(), focusedPreyContainer.get().getPosition())) {
				focusedPreyPath.setPath(
						Position.getShortestPath(currentAgent.getPosition(), focusedPreyContainer.get().getPosition(), 
								surroundings.getObstacles(), surroundings.getWorldShape(), 
								surroundings.getGridDimension(), surroundings.getObstacleSafetyDistance())
						,initial_ttl);
				if(focusedPreyPath.isEmpty()) {
					return Vector.emptyVector();
				} else {
//					System.out.println("New focused prey needs path");
					focusedPreyPath.pop();
					focusedPreyPath.pop();
					return new Vector(focusedPreyPath.peek(), currentAgent.getPosition());
				}
			} else {
				focusedPreyPath.clearPath();
				return new Vector(focusedPreyContainer.get().getPosition(), currentAgent.getPosition());
			}
		}
		return preyForce;
	}
}
