package chalmers.dax021308.ecosystem.model.environment.obstacle;

import java.awt.Color;
import java.awt.Dimension;
import java.util.Random;

import chalmers.dax021308.ecosystem.model.util.Position;

public class RectangularObstacle extends AbstractObstacle implements IObstacle{
	
	private static double nStep = 200;

	public RectangularObstacle(double width, double height, Position position, Color color, double angle, boolean transparent){
		this.position = position;
		this.width = width;
		this.height = height;
		this.color = color;
		this.angle = angle;
		this.transparent = transparent;
	}
	
	@Override
	public Position closestBoundary(Position p) {
		Position agentPos = toObstacleCoordinates(p);
		
		double xSign = Math.signum(agentPos.getX());
		double ySign = Math.signum(agentPos.getY());
		agentPos.setPosition(agentPos.getX()*xSign, agentPos.getY()*ySign);
		
		double x = agentPos.getX();
		double y = agentPos.getY();
		Position bestPos;
		
		if(x < width) {
			bestPos = new Position(x,height);
		} else if (y < height) {
			bestPos = new Position(width,y);
		} else {
			bestPos = new Position(width,height);
		}
		
		bestPos.setPosition(bestPos.getX()*xSign, bestPos.getY()*ySign);
		
		
		return fromObstacleCoordinates(bestPos);
	}

	@Override
	public boolean isInObstacle(Position p, double sd) {
		Position pos = toObstacleCoordinates(p);
		if(pos.getY() <height+sd && pos.getY() > -height-sd){
			if(pos.getX() < width+sd && pos.getX() > -width-sd) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isCloseTo(Position p, double interactionRange){
		double radius = Math.sqrt(width*width + height*height);
		if(this.position.getDistance(p) <= radius + interactionRange) {
			return true;
		}
		return false;
	}

	@Override
	public Color getColor() {
		return color;
	}
	

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("Rectangular Width: ");
		sb.append(width);
		sb.append(" Height: ");
		sb.append(height);
		sb.append(' ');
		return sb.toString();
	}
	

	/**
	 * Scale the obstacle to a specific scalar value. 
	 * And return a clone of the newly scaled obstacle
	 */
	@Override
	public IObstacle scale(double scaleX, double scaleY) {
		Position newPos = new Position(position.getX() * scaleX, position.getY() * scaleY);
		double newWidth = width * scaleX;
		double newHeight = height * scaleY;
		return new RectangularObstacle(newWidth, newHeight, newPos, new Color(color.getRGB()), angle, transparent);
	}

	public static IObstacle getRandomObstacle(Dimension simDim) {
		Random ran = new Random();
		Position randomPos = new Position(ran.nextInt(simDim.width), ran.nextInt(simDim.height));
		double randomWidth = ran.nextInt(simDim.width / 4);
		double randomHeight = ran.nextInt(simDim.height / 4);
		double randomAngle = Math.PI*Math.random();
		Color randomColor = new Color(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255));
		return new RectangularObstacle(randomWidth, randomHeight, randomPos, randomColor , randomAngle, false);
	}
}
