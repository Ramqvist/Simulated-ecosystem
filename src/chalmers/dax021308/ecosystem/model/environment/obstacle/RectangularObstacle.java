package chalmers.dax021308.ecosystem.model.environment.obstacle;

import java.awt.Color;

import chalmers.dax021308.ecosystem.model.util.Position;

public class RectangularObstacle extends AbstractObstacle implements IObstacle{
	
	private static double nStep = 200;

	public RectangularObstacle(double width, double height, Position position, Color color){
		this.position = position;
		this.a = width;
		this.b = height;
		this.color = color;
	}
	
	@Override
	public Position closestBoundary(Position p) {
		Position agentPos = new Position(p.getX()-this.position.getX(), p.getY()-this.position.getY());	
		
		double xSign = Math.signum(agentPos.getX());
		double ySign = Math.signum(agentPos.getY());
		agentPos.setPosition(agentPos.getX()*xSign, agentPos.getY()*ySign);
		
		double x = agentPos.getX();
		double y = agentPos.getY();
		Position bestPos;
		
		if(x < a) {
			bestPos = new Position(x,b);
		} else if (y < b) {
			bestPos = new Position(a,y);
		} else {
			bestPos = new Position(a,b);
		}
		
		bestPos.setPosition(bestPos.getX()*xSign, bestPos.getY()*ySign);
		bestPos.setPosition(bestPos.getX()+this.position.getX(), bestPos.getY()+this.position.getY());
		
		return bestPos;
	}

	@Override
	public boolean isInObstacle(Position p) {
		if(p.getY() < position.getY()+b && p.getY() > position.getY()-b){
			if(p.getX() < position.getX()+a && p.getX() > position.getX()-a) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isCloseTo(Position p, double interactionRange){
		double radius = Math.sqrt(a*a + b*b);
		if(this.position.getDistance(p) <= radius + interactionRange) {
			return true;
		}
		return false;
	}

	@Override
	public Color getColor() {
		return color;
	}

}
