package chalmers.dax021308.ecosystem.model.environment.obstacle;

import java.awt.Color;

import chalmers.dax021308.ecosystem.model.environment.SimulationSettings;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.SquareShape;

public abstract class AbstractObstacle implements IObstacle {
	
	protected Position position;
	protected double width;
	protected double height;
	protected Color color;
	
	@Override
	public double getWidth() {
		return width;
	}
	@Override
	public double getHeight() {
		return height;
	}
	@Override
	public Position getPosition() {
		return new Position(position);
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
		sb.append(width);
		sb.append(';');
		sb.append(height);
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

}
