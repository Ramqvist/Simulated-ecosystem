package chalmers.dax021308.ecosystem.model.environment;

import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class EllipticalObstacle extends AbstractObstacle{
	
	private static final double e = 0.001;
	private static final double nStep = 100;
	
	public EllipticalObstacle(double width, double height, Position position){
		this.position = position;
		this.width = width;
		this.height = height;
	}
	@Override
	public Position closestBoundary(Position p) {
		Position agentPos = new Position(p.getX()-this.position.getX(), p.getY()-this.position.getY());
		Position bestPos = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
		Position elipPos = new Position();
		for(int i=0; i<nStep; i++){
			elipPos.setPosition(width*Math.cos(i*2*Math.PI/nStep), height*Math.sin(i*2*Math.PI/nStep));
			if(elipPos.getDistance(agentPos) < bestPos.getDistance(agentPos)) {
				bestPos.setPosition(elipPos);
			}
		}
		bestPos.setPosition(bestPos.getX()+this.position.getX(), bestPos.getY()+this.position.getY());
		return bestPos;
	}

	
	//Not working properly
	private Position recursiveBoundarySearch(Position agentPos, double lastDistance ,double startAngle, double step){
	
		Position elipPos = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
		Position bestPos = new Position(Double.MAX_VALUE, Double.MAX_VALUE);;
		Position nextBestPos = new Position(Double.MAX_VALUE, Double.MAX_VALUE);;
		double b = 0;
		double s = 0;
		for(double i=0; i<nStep; i++){
			elipPos = new Position(width*Math.cos(startAngle+i*step), height*Math.sin(startAngle+i*step));
			if(elipPos.getDistance(agentPos)<bestPos.getDistance(agentPos)){
				nextBestPos = new Position(bestPos);
				s = b;
				bestPos = new Position(elipPos);
				b = i;
			} else if (elipPos.getDistance(agentPos) < nextBestPos.getDistance(agentPos)) {
				nextBestPos = new Position(elipPos);
				s = i;
			}
		}
		double direction = Math.signum(s-b);
		if(b==0 && s == nStep-1) {
			direction = -1;
		} else if (b == nStep-1 && s == 0) {
			direction = 1;
		}
		double distance = bestPos.getDistance(agentPos);
		if(lastDistance-distance<e){
			return bestPos;
		} else {
			return recursiveBoundarySearch(agentPos, distance, startAngle+b*step, direction*(step/nStep));
		}
		
	}
}
