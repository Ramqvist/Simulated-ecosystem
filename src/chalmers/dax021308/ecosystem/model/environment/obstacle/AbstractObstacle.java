package chalmers.dax021308.ecosystem.model.environment.obstacle;

import java.awt.Color;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

/**
 * Abstract class for obstacles
 * 
 * @author Sebastian Anerud, Erik Ramqvist
 *
 */
public abstract class AbstractObstacle implements IObstacle {
 	
	/*Obstacle constants */
	public static final String OBSTACLE_RECTANGULAR = "Rectangular obstacle";
	public static final String OBSTACLE_ELLIPTICAL  = "Elliptical obstacle";
	public static final String OBSTACLE_TRIANGLE  	= "Triangle obstacle";
	public static final String OBSTACLE_RIVERS 		= "Rivers obstacle";
	public static final String OBSTACLE_TUBE  		= "Tube obstacle";
	public static final String OBSTACLE_NONE        = "No obstacle";
	public static final String[] OBSTACLE_VALUES 	= {OBSTACLE_NONE, OBSTACLE_RECTANGULAR, OBSTACLE_ELLIPTICAL, OBSTACLE_TRIANGLE, OBSTACLE_RIVERS, OBSTACLE_TUBE};

	protected Position position;
	protected double width;
	protected double height;
	protected double angle;
	protected Color color;
	
	/**
	 * returns the width of the obstacle.
	 */
	@Override
	public double getWidth() {
		return width;
	}
	/**
	 * returns the height of the obstacle.
	 */
	@Override
	public double getHeight() {
		return height;
	}
	
	@Override
	public void setWidth(double width) {
		this.width = width;
	}
	
	
	@Override
	public void setHeight(double height) {
		this.height = height;
	}
	
	/**
	 * returns the position of the obstacle
	 */
	@Override
	public Position getPosition() {
		return new Position(position);
	}
	
	/**
	 * Moves the obstacle +x and +y distance.
	 */
	@Override
	public void moveObstacle(double x, double y) {
		this.position = new Position(position.getX() + x, position.getY() + y);
	}
	
	/**
	 * Sets the position of the obstacle to (x,y).
	 */
	@Override
	public void setPosition(Position p) {
		this.position = p;
	}
	
	/**
	 * returns the color of the obstacle
	 */
	@Override
	public Color getColor(){
		return this.color;
	}
	
	@Override
	public double getAngle(){
		return this.angle;
	}
	
	/**
	 * Sets the color of the obstacle
	 */
	public void setColor(Color c) {
		this.color = c;
	}
	
	public Position toObstacleCoordinates(Position p){
		Position newPos =  new Position(p.getX()-this.position.getX(), p.getY()-this.position.getY());
		return newPos.setPosition(Math.cos(angle)*newPos.getX() + Math.sin(angle)*newPos.getY(), 
				-Math.sin(angle)*newPos.getX() + Math.cos(angle)*newPos.getY());
	}
	
	public Position fromObstacleCoordinates(Position p) {
		Position newPos = new Position(p); 
		newPos.setPosition(Math.cos(-angle)*newPos.getX() + Math.sin(-angle)*newPos.getY(), 
				-Math.sin(-angle)*newPos.getX() + Math.cos(-angle)*newPos.getY());
		return new Position(newPos.getX()+this.position.getX(), newPos.getY()+this.position.getY());	
	}
	
	/**
	 * Used to export an obstacle.
	 */
	public String toBinaryString() {
		StringBuilder sb = new StringBuilder();
		if(this instanceof EllipticalObstacle) {
		sb.append(OBSTACLE_ELLIPTICAL); //TODO: Needs shape here.
		} else if(this instanceof RectangularObstacle) {
			sb.append(OBSTACLE_RECTANGULAR); //TODO: Needs shape here.
		} else if(this instanceof TriangleObstacle) {
			sb.append(OBSTACLE_TRIANGLE);
		} else {
			sb.append(OBSTACLE_NONE);
			return sb.toString();
		}
		sb.append(';');
		sb.append(roundTwoDecimals(position.getX()));
		sb.append(';');
		sb.append(roundTwoDecimals(position.getY()));
		sb.append(';');
		sb.append(width);
		sb.append(';');
		sb.append(height);
		sb.append(';');
		sb.append(color.getRed());
		sb.append(';');
		sb.append(color.getGreen());
		sb.append(';');
		sb.append(color.getBlue());
		sb.append(';');
		sb.append(angle);
		return sb.toString();
	}
	
	/**
	 * Used to import an obstacle.
	 * @param input
	 * @return
	 */
	public static AbstractObstacle createFromFile(String input) {
		String[] inputArray = input.split(";");
		String shape = inputArray[0];
		AbstractObstacle obs = null;
		if (shape.equals(OBSTACLE_RECTANGULAR)) {
			obs = new RectangularObstacle(Double.parseDouble(inputArray[3]), Double.parseDouble(inputArray[4]),
					new Position( Double.parseDouble(inputArray[1]),  Double.parseDouble(inputArray[2])),
					new Color(Integer.parseInt(inputArray[5]),Integer.parseInt(inputArray[6]), 
							Integer.parseInt(inputArray[7])),Double.parseDouble(inputArray[8]));
		} else if (shape.equals(OBSTACLE_ELLIPTICAL)) {
			obs = new EllipticalObstacle(Double.parseDouble(inputArray[3]), Double.parseDouble(inputArray[4]),
					new Position( Double.parseDouble(inputArray[1]),  Double.parseDouble(inputArray[2])),
					new Color(Integer.parseInt(inputArray[5]),Integer.parseInt(inputArray[6]),
							Integer.parseInt(inputArray[7])),Double.parseDouble(inputArray[8]));
		} else if (shape.equals(OBSTACLE_TRIANGLE)) {
			obs = new TriangleObstacle(Double.parseDouble(inputArray[3]), Double.parseDouble(inputArray[4]),
					new Position( Double.parseDouble(inputArray[1]),  Double.parseDouble(inputArray[2])),
					new Color(Integer.parseInt(inputArray[5]),Integer.parseInt(inputArray[6]),
							Integer.parseInt(inputArray[7])),Double.parseDouble(inputArray[8]));
		}  else if (shape.equals(OBSTACLE_NONE)) {
			return null;
		}
		return obs;
	}
	
	/**
	 * Rounds a double to two decimals.
	 * @param num
	 * @return
	 */
	public static double roundTwoDecimals(double num) {
		double result = num * 100;
		result = Math.round(result);
		result = result / 100;
		return result;
	}
	
	/**
	 * checks if a position is inside the list of obstacles.
	 * @param obsList The list of obstacles.
	 * @param p the position.
	 * @return true if the position is inside any of the obstacles. 
	 * False if the position is not inside any of the obstacles.
	 */
	public static boolean isInsideObstacleList(List<IObstacle> obsList, Position p) {
		return AbstractObstacle.isInsideObstacleList(obsList, p, 0);
	}
	
	/**
	 * checks if a position is inside the list of obstacles.
	 * @param obsList The list of obstacles.
	 * @param p the position.
	 * @return true if the position is inside any of the obstacles. 
	 * False if the position is not inside any of the obstacles.
	 */
	public static boolean isInsideObstacleList(List<IObstacle> obsList, Position p, double safetyDistance) {
		if(obsList == null) {
			return false;
		}
		for(IObstacle o : obsList) {
			if(o.isInObstacle(p, safetyDistance)) {
				return true;
			}
		}
		return false;
	}
	
//	@Override
//	public boolean isInsidePath(Position start, Position end) {
//		Position current = new Position(start);
//		double path_threshold = 5.0;
//		while(current.getDistance(end) >= path_threshold ) {
//			if(isInObstacle(current)) {
//				return true;
//			}
//			if(end.getX() > current.getX()) {
//				current.setX(current.getX() + path_threshold);
//			} else if(end.getX() < current.getX()) {
//				current.setX(current.getX() - path_threshold);
//			}
//			if(end.getY() > current.getY()) {
//				current.setY(current.getY() + path_threshold);
//			} else if(end.getY() < current.getY()) {
//				current.setY(current.getY() - path_threshold);
//			}
//		}
//		return false;
//	}
	
	/**
	 * Checks of a linear path is cutting the obstacle at any point.
	 * @return true if the path cuts the obstacle. False otherwise.
	 */
	public boolean isInsidePath(Position start, Position end) {
		Position current = new Position(start);
		double path_threshold = 5.0;
		while(current.getDistance(end) >= path_threshold ) {
			if(isInObstacle(current, 0)) {
				return true;
			}
			if(end.getX() > current.getX()) {
				current.setX(current.getX() + path_threshold);
			} else if(end.getX() < current.getX()) {
				current.setX(current.getX() - path_threshold);
			}
			if(end.getY() > current.getY()) {
				current.setY(current.getY() + path_threshold);
			} else if(end.getY() < current.getY()) {
				current.setY(current.getY() - path_threshold);
			}
		}
		return false;
	}
	
//	public static boolean isInsidePathList(List<IObstacle> obsList, Position start, Position end) {
//		for(IObstacle o : obsList) {
//			if(o.isInsidePath(start, end)) {
//				return true;
//			}
//		}
//		return false;
//	}
	
	/**
	 * Checks if a path is inside a list of obstacles.
	 */
	public static boolean isInsidePathList(List<IObstacle> obsList, Position start, Position end) {
		
		double stepConstant = 5;
		double dirX = end.getX() - start.getX();
		double dirY = end.getY() - start.getY();
		double distance = Math.sqrt(dirX*dirX + dirY*dirY);
		dirX /= distance;
		dirY /= distance;
		int nIterations = (int) (distance/stepConstant);
		
		Position currentPos = new Position(start);
		double currentX = currentPos.getX();
		double currentY = currentPos.getY();
		
		for(int i=0;i<nIterations;i++){
			currentX += dirX;
			currentY += dirY;
			currentPos.setPosition(currentX, currentY);
			for(IObstacle o : obsList) {
				if(o.isInObstacle(currentPos, 0)) {
//					System.out.println("Completed in: " + i + " iterations.");
					return true;
				}
			}
			
		}
		
		return false;
	}

}
