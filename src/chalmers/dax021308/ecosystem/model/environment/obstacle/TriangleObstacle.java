package chalmers.dax021308.ecosystem.model.environment.obstacle;

import java.awt.Color;

import chalmers.dax021308.ecosystem.model.util.Position;

public class TriangleObstacle extends AbstractObstacle {
	
	public TriangleObstacle(double width, double height, Position position, Color color){
		this.position = position;
		this.a = width;
		this.b = height;
		this.color = color;
	}

	@Override
	public Position closestBoundary(Position p) {
		Position agentPos = new Position(p.getX()-this.position.getX(), p.getY()-this.position.getY());
		
		double xSign = Math.signum(agentPos.getX());
		agentPos.setPosition(agentPos.getX()*xSign, agentPos.getY());
		
		double x = agentPos.getX();
		double y = agentPos.getY();
		
		Position bestPos = new Position();
		if(y <= -b){
			if(x >= a){
				bestPos = new Position(a, -b);
			} else {
				bestPos = new Position(agentPos.getX(), -b);
			}
			
			bestPos.setPosition(bestPos.getX()*xSign, bestPos.getY());
			return bestPos.setPosition(bestPos.getX()+this.position.getX(), bestPos.getY()+this.position.getY());
		}
		
		double yLimHigh = b + x*a/(2*b);
		double yLimLow = -b - a*a/(2*b) + x*a/(2*b);
		
		if(y >= yLimHigh) {
			bestPos = new Position(0,b);
		} else if(y <= yLimLow){
			bestPos = new Position(a,-b);
		} else {
			double k = 2*b/a;
			double t = (k*x + y - b)/(k*k +1);
			double newX = x - t*k;
			double newY = y-t;
			bestPos = new Position(newX, newY);
		}
		
		bestPos.setPosition(bestPos.getX()*xSign, bestPos.getY());
		return bestPos.setPosition(bestPos.getX()+this.position.getX(), bestPos.getY()+this.position.getY());
	}

	@Override
	public boolean isInObstacle(Position p) {
		Position agentPos = new Position(p.getX()-this.position.getX(), p.getY()-this.position.getY());
		
		double xSign = Math.signum(agentPos.getX());
		agentPos.setPosition(agentPos.getX()*xSign, agentPos.getY());
		
		double x = agentPos.getX();
		double y = agentPos.getY();
		
		if(y > b || y < -b){
			return false;
		}
		
		
		if(x > a) {
			return false;
		} 
		
		double yVal = b - 2*b/a*x;
		if(y > yVal){
			return false;
		}
		
		return true;
	}

	@Override
	public boolean isCloseTo(Position p, double interactionRange) {
		return true;
	}

}
