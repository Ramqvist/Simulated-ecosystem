package chalmers.dax021308.ecosystem.model.environment.obstacle;

import java.awt.Color;

import chalmers.dax021308.ecosystem.model.util.Position;

public class EllipticalObstacle extends AbstractObstacle{
	
	private static double e = 0.1;
	private static double nStep = 6;
	
	/**
	 * The ellipse is defined as (x/width)^2 + (y/height)^2 + 1 = 0. Therefore width and height is the half
	 * of the total width and height of the ellipse.
	 * @param width the total width of the ellipse/2
	 * @param height the total height of the ellipse/2
	 * @param position 
	 * @param color
	 */
	public EllipticalObstacle(double width, double height, Position position, Color color, double angle){
		this.position = position;
		this.width = width;
		this.height = height;
		this.color = color;
		this.angle = angle;
	}
	@Override
	/**
	 * Returns the closes point on the ellipse boundary to a given point p.
	 */
	public Position closestBoundary(Position p) {
		
		
		Position agentPos = toObstacleCoordinates(p);

		if(width<height) {
			agentPos = new Position(-agentPos.getY(), agentPos.getX());
		} else {
			agentPos = new Position(agentPos.getX(), agentPos.getY());
		}
		
		double xSign = Math.signum(agentPos.getX());
		double ySign = Math.signum(agentPos.getY());
		agentPos.setPosition(agentPos.getX()*xSign, agentPos.getY()*ySign);
		
		Position bestPos;
		if(width<height) {
			bestPos = newtonsMethod(agentPos, height, width);
		} else {
			bestPos = newtonsMethod(agentPos, width, height);
		}
		
		bestPos.setPosition(bestPos.getX()*xSign, bestPos.getY()*ySign);
		
		if(width<height) {
			bestPos.setPosition(bestPos.getY(), -bestPos.getX());
		}
		
		
		
//		e = 0.01;
//		Position bestPos = recursiveBoundarySearch(agentPos, 0, 2*Math.PI/nStep, 1);
		
//		nStep = 700;	
//		Position bestPos = bruteBoundarySearch(agentPos);

		return fromObstacleCoordinates(bestPos);
	}

	/**
	 * Not used. Newtons is better.
	 * @param agentPos
	 * @param startAngle
	 * @param step
	 * @param iteration
	 * @return
	 */
	private Position recursiveBoundarySearch(Position agentPos ,double startAngle, double step, int iteration){
		Position elipPos1 = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
		Position elipPos2 = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
		Position bestPos = new Position(width*Math.cos(startAngle), height*Math.sin(startAngle));
		double bestI = 0;
		for(double i=1; i<=nStep/2; i++){
			elipPos1 = new Position(width*Math.cos(startAngle+i*step), height*Math.sin(startAngle+i*step));
			elipPos2 = new Position(width*Math.cos(startAngle-i*step), height*Math.sin(startAngle-i*step));
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
	
	/**
	 * Not used. Newtons is better.
	 * @param agentPos
	 * @return
	 */
	private Position bruteBoundarySearch(Position agentPos){
		Position bestPos = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
		Position elipPos = new Position();
		for(int i=0; i<nStep; i++){
			elipPos.setPosition(width*Math.cos(i*2*Math.PI/nStep), height*Math.sin(i*2*Math.PI/nStep));
			if(elipPos.getDistance(agentPos) < bestPos.getDistance(agentPos)) {
				bestPos.setPosition(elipPos);
			}
		}
		return bestPos;
	}
	
	/**
	 * Given a position, this funtion finds the closest point on the ellipse boundary to the given position.
	 * The function finds the best position using Newton's method for approximating a rational function.
	 * @param p the position.
	 * @param a The width of the ellipse.
	 * @param b the height of the ellipse.
	 * @return
	 */
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
	public boolean isInObstacle(Position p, double sd) {
		double px = p.getX()-position.getX();
		double py = p.getY()-position.getY();
		
		if(px<width+sd && px > -width-sd){
			double y = (height+sd)*Math.sqrt(1-px*px/((width+sd)*(width+sd)));
			if(py<y && py > -y) {
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * Returns the color of the obstacle.
	 */
	@Override
	public Color getColor() {
		return color;
	}
	
	/**
	 * Returns true of the given position is inside the enveloping circle of the obstacles, 
	 * plus the interaction range.
	 */
	@Override
	public boolean isCloseTo(Position p, double interactionRange){
		double ab = Math.max(width, height);
		if(this.position.getDistance(p) <= ab + interactionRange) {
			return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("EllipticalObstacle Width: ");
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
		return new EllipticalObstacle(newWidth, newHeight, newPos, new Color(color.getRGB()),0);
	}
}
