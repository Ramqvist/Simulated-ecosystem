package chalmers.dax021308.ecosystem.model.environment.obstacle;

import java.awt.Color;
import java.util.List;

import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.util.Position;

public abstract class AbstractObstacle implements IObstacle {
	
	protected Position position;
	protected double a;
	protected double b;
	protected Color color;
	
	@Override
	public double getWidth() {
		return a;
	}
	@Override
	public double getHeight() {
		return b;
	}
	@Override
	public Position getPosition() {
		return new Position(position);
	}
	
	@Override
	public Color getColor(){
		return this.color;
	}
	
	public String toBinaryString() {
		StringBuilder sb = new StringBuilder();
		if(this instanceof EllipticalObstacle) {
		sb.append(SimulationSettings.OBSTACLE_ELLIPTICAL); //TODO: Needs shape here.
		} else if(this instanceof RectangularObstacle) {
			sb.append(SimulationSettings.OBSTACLE_RECTANGULAR); //TODO: Needs shape here.
		} else {
			sb.append(SimulationSettings.OBSTACLE_NONE);
			return sb.toString();
		}
		sb.append(';');
		sb.append(roundTwoDecimals(position.getX()));
		sb.append(';');
		sb.append(roundTwoDecimals(position.getY()));
		sb.append(';');
		sb.append(a);
		sb.append(';');
		sb.append(b);
		sb.append(';');
		sb.append(color.getRed());
		sb.append(';');
		sb.append(color.getGreen());
		sb.append(';');
		sb.append(color.getBlue());
		return sb.toString();
	}
	
	public static AbstractObstacle createFromFile(String input) {
		String[] inputArray = input.split(";");
		String shape = inputArray[0];
		AbstractObstacle obs = null;
		if (shape.equals(SimulationSettings.OBSTACLE_RECTANGULAR)) {
			obs = new RectangularObstacle(Double.parseDouble(inputArray[3]), Double.parseDouble(inputArray[4]),
					new Position( Double.parseDouble(inputArray[1]),  Double.parseDouble(inputArray[2])),
					new Color(Integer.parseInt(inputArray[5]),Integer.parseInt(inputArray[6]), Integer.parseInt(inputArray[7])));
		} else if (shape.equals(SimulationSettings.OBSTACLE_ELLIPTICAL)) {
			obs = new EllipticalObstacle(Double.parseDouble(inputArray[3]), Double.parseDouble(inputArray[4]),
					new Position( Double.parseDouble(inputArray[1]),  Double.parseDouble(inputArray[2])),
					new Color(Integer.parseInt(inputArray[5]),Integer.parseInt(inputArray[6]), Integer.parseInt(inputArray[7])));
		} else if (shape.equals(SimulationSettings.OBSTACLE_NONE)) {
			return null;
		}
		return obs;
	}
	
	public static double roundTwoDecimals(double num) {
		double result = num * 100;
		result = Math.round(result);
		result = result / 100;
		return result;
	}
	
	public static boolean isInsideObstacleList(List<IObstacle> obsList, Position p) {
		if(obsList == null) {
			return false;
		}
		for(IObstacle o : obsList) {
			if(o.isInObstacle(p)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isInsidePath(Position start, Position end) {
		Position current = new Position(start);
		double path_threshold = 5.0;
		while(current.getDistance(end) >= path_threshold ) {
			if(isInObstacle(current)) {
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
	
	public static boolean isInsidePathList(List<IObstacle> obsList, Position start, Position end) {
		for(IObstacle o : obsList) {
			if(o.isInsidePath(start, end)) {
				return true;
			}
		}
		return false;
	}

}
