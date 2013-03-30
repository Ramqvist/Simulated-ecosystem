package chalmers.dax021308.ecosystem.model.environment.obstacle;

import java.awt.Color;

import chalmers.dax021308.ecosystem.model.util.Position;

public class EllipticalObstacle extends AbstractObstacle{
	
	private static double e = 0.1;
	private static double nStep = 6;
	
	public EllipticalObstacle(double width, double height, Position position, Color color){
		this.position = position;
		this.a = width;
		this.b = height;
		this.color = color;
	}
	@Override
	/**
	 * Returns the closes point on the ellipse boundary to a given point p.
	 */
	public Position closestBoundary(Position p) {
		Position agentPos = new Position(p.getX()-this.position.getX(), p.getY()-this.position.getY());
		
		if(a<b) {
			agentPos = new Position(-agentPos.getY(), agentPos.getX());
		} else {
			agentPos = new Position(agentPos.getX(), agentPos.getY());
		}
		
		double xSign = Math.signum(agentPos.getX());
		double ySign = Math.signum(agentPos.getY());
		agentPos.setPosition(agentPos.getX()*xSign, agentPos.getY()*ySign);
		
		Position bestPos;
		if(a<b) {
			bestPos = newtonsMethod(agentPos, b, a);
		} else {
			bestPos = newtonsMethod(agentPos, a, b);
		}
		
		bestPos.setPosition(bestPos.getX()*xSign, bestPos.getY()*ySign);
		
		if(a<b) {
			bestPos.setPosition(bestPos.getY(), -bestPos.getX());
		}
		
		
		
//		e = 0.01;
//		Position bestPos = recursiveBoundarySearch(agentPos, 0, Math.PI/2, 1);
		
//		nStep = 700;	
//		Position bestPos = bruteBoundarySearch(agentPos);

		bestPos.setPosition(bestPos.getX()+this.position.getX(), bestPos.getY()+this.position.getY());
		return bestPos;
	}

	private Position recursiveBoundarySearch(Position agentPos ,double startAngle, double step, int iteration){
		Position elipPos1 = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
		Position elipPos2 = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
		Position bestPos = new Position(a*Math.cos(startAngle), b*Math.sin(startAngle));
		double bestI = 0;
		for(double i=1; i<=nStep; i++){
			elipPos1 = new Position(a*Math.cos(startAngle+i*step), b*Math.sin(startAngle+i*step));
			elipPos2 = new Position(a*Math.cos(startAngle-i*step), b*Math.sin(startAngle-i*step));
			if(elipPos1.getDistance(agentPos)<bestPos.getDistance(agentPos)){
				bestPos = new Position(elipPos1);
				bestI = i;
			}
			if(elipPos2.getDistance(agentPos)<bestPos.getDistance(agentPos)){
				bestPos = new Position(elipPos2);
				bestI = -i;
			}
		}
		double nextStartAngle = startAngle+bestI*step;
		if(step<e){
//			System.out.println(iteration);
			return bestPos;
		} else {
			return recursiveBoundarySearch(agentPos, nextStartAngle, (2*step/nStep), iteration+1);
		}
		
	}
	
	private Position bruteBoundarySearch(Position agentPos){
		Position bestPos = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
		Position elipPos = new Position();
		for(int i=0; i<nStep; i++){
			elipPos.setPosition(a*Math.cos(i*2*Math.PI/nStep), b*Math.sin(i*2*Math.PI/nStep));
			if(elipPos.getDistance(agentPos) < bestPos.getDistance(agentPos)) {
				bestPos.setPosition(elipPos);
			}
		}
		return bestPos;
	}
	
	private Position newtonsMethod(Position p, double a, double b){	
		double u = p.getX();
		double v = p.getY();
		
		double t = b*p.getY()-b*b;
		double F0 = Double.MAX_VALUE;
		double F = (a*u/(t+a*a))*(a*u/(t+a*a)) +
		(b*v/(t+b*b))*(b*v/(t+b*b)) - 1;
		double Fprim = -2*a*a*u*u/Math.pow(t+a*a, 3) + -2*b*b*v*v/Math.pow(t+b*b, 3);
		t = t - F/Fprim;
		
		while(F0-F > e) {
			F0 = F;
			F = (a*u/(t+a*a))*(a*u/(t+a*a)) +
				(b*v/(t+b*b))*(b*v/(t+b*b)) - 1;
			Fprim = -2*a*a*u*u/Math.pow(t+a*a, 3) + -2*b*b*v*v/Math.pow(t+b*b, 3);
			t = t - F/Fprim;	
		}
		
		Position closestPos = new Position(a*a*u/(t+a*a),b*b*v/(t+b*b));
		return closestPos;
	}
	
	@Override
	public boolean isInObstacle(Position p) {
		if(p.getY() < position.getY()+b && p.getY() > position.getY()-b){
			double y = p.getY()-position.getY();
			double x = a*Math.sqrt(1-(y*y)/(b*b));
			if(p.getX() < position.getX()+x && p.getX() > position.getX()-x) {
				return true;
			}
		}
		return false;
	}
	@Override
	public Color getColor() {
		return color;
	}
	
}
