package chalmers.dax021308.ecosystem.model.environment;

import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class EllipticalObstacle extends AbstractObstacle{
	
	private static final double e = 0.001;
	private static final double nStep = 4;
	
	private double width;
	private double height;
	
	public EllipticalObstacle(double width, double height, Position position){
		this.position = position;
		this.width = width;
		this.height = height;
	}
	@Override
	public Position closestBoundary(Position p) {
		Position agentPos = new Position(p.getX()-this.position.getX(), p.getY()-this.position.getY());
		Position bestPos = recursiveBoundarySearch(agentPos, Double.MAX_VALUE, 0, Math.PI/2);
		bestPos.setPosition(bestPos.getX()+this.position.getX(), bestPos.getY()+this.position.getY());
		return bestPos;
	}

	private Position recursiveBoundarySearch(Position agentPos, double lastDistance ,double startAngle, double step){
	
		Position elipPos = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
		Position bestPos = elipPos;
		Position nextBestPos = elipPos;
		double bestI = 0;
		double nextBestI = 0;
		for(double i=0; i<nStep; i++){
			elipPos = new Position(width*Math.cos(startAngle+i*step), height*Math.sin(startAngle+i*step));
			if(elipPos.getDistance(agentPos)<bestPos.getDistance(agentPos)){
				nextBestPos = bestPos;
				nextBestI = bestI;
				bestPos = elipPos;
				bestI = i;
			} else if (elipPos.getDistance(agentPos) < nextBestPos.getDistance(agentPos)) {
				nextBestPos = elipPos;
				nextBestI = i;
			}
		}
		
		System.out.println(nextBestI-bestI);
		double distance = elipPos.getDistance(agentPos);
		if(lastDistance-distance<e){
			return elipPos;
		} else {
			return recursiveBoundarySearch(agentPos, distance, startAngle+bestI*step, (nextBestI-bestI)*(step/nStep));
		}
		
	}
}
