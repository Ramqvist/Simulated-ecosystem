package chalmers.dax021308.ecosystem.model.environment;

import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class EllipticalObstacle extends AbstractObstacle{
	
	private static double e = 0.01;
	private static double nStep = 6;
	
	public EllipticalObstacle(double width, double height, Position position){
		this.position = position;
		this.width = width;
		this.height = height;
	}
	@Override
	/**
	 * Returns the closes point on the ellipse boundary to a given point p.
	 */
	public Position closestBoundary(Position p) {
		Position agentPos = new Position(p.getX()-this.position.getX(), p.getY()-this.position.getY());
		
		Position bestPos = recursiveBoundarySearch(agentPos, 0, Math.PI/2, 1);
		
//		nStep = 700;	
//		Position bestPos = bruteBoundarySearch(agentPos);

		bestPos.setPosition(bestPos.getX()+this.position.getX(), bestPos.getY()+this.position.getY());
		return bestPos;
	}

	private Position recursiveBoundarySearch(Position agentPos ,double startAngle, double step, int iteration){
		Position elipPos1 = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
		Position elipPos2 = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
		Position bestPos = new Position(width*Math.cos(startAngle), height*Math.sin(startAngle));
		double b = 0;
		for(double i=1; i<=nStep; i++){
			elipPos1 = new Position(width*Math.cos(startAngle+i*step), height*Math.sin(startAngle+i*step));
			elipPos2 = new Position(width*Math.cos(startAngle-i*step), height*Math.sin(startAngle-i*step));
			if(elipPos1.getDistance(agentPos)<bestPos.getDistance(agentPos)){
				bestPos = new Position(elipPos1);
				b = i;
			}
			if(elipPos2.getDistance(agentPos)<bestPos.getDistance(agentPos)){
				bestPos = new Position(elipPos2);
				b = -i;
			}
		}
		double nextStartAngle = startAngle+b*step;
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
			elipPos.setPosition(width*Math.cos(i*2*Math.PI/nStep), height*Math.sin(i*2*Math.PI/nStep));
			if(elipPos.getDistance(agentPos) < bestPos.getDistance(agentPos)) {
				bestPos.setPosition(elipPos);
			}
		}
		return bestPos;
	}
	
	@Override
	public boolean isInObstacle(Position p) {
		if(p.getY() < position.getY()+height && p.getY() > position.getY()-height){
			double y = p.getY()-position.getY();
			double x = width*Math.sqrt(1-(y*y)/(height*height));
			if(p.getX() < position.getX()+x && p.getX() > position.getX()-x) {
				return true;
			}
		}
		return false;
	}
	
}
