package chalmers.dax021308.ecosystem.model.environment;

import chalmers.dax021308.ecosystem.model.util.Position;

public class RectangularObstacle extends AbstractObstacle implements IObstacle{
	
	private static double nStep = 6;
	private static double e = 0.01;
	private static double angleLimit;

	public RectangularObstacle(double width, double height, Position position){
		this.position = position;
		this.width = width;
		this.height = height;
		angleLimit = Math.atan(height/width);
	}
	
	@Override
	public Position closestBoundary(Position p) {
		Position agentPos = new Position(p.getX()-this.position.getX(), p.getY()-this.position.getY());	
		
		nStep = 200;	
		Position bestPos = bruteBoundarySearch(agentPos);

		bestPos.setPosition(bestPos.getX()+this.position.getX(), bestPos.getY()+this.position.getY());
		return bestPos;
	}

	private Position bruteBoundarySearch(Position agentPos){
		Position bestPos = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
		Position rectPos = new Position();
		for(int i=0; i<nStep/4; i++){
			rectPos.setPosition(-width + 8*i/nStep*width, -height);
			if(rectPos.getDistance(agentPos) < bestPos.getDistance(agentPos)) {
				bestPos.setPosition(rectPos);
			}
		}
		for(int i=0; i<nStep/4; i++){
			rectPos.setPosition(width, -height + 8*i/nStep*height);
			if(rectPos.getDistance(agentPos) < bestPos.getDistance(agentPos)) {
				bestPos.setPosition(rectPos);
			}
		}
		for(int i=0; i<nStep/4; i++){
			rectPos.setPosition(-width + 8*i/nStep*width, height);
			if(rectPos.getDistance(agentPos) < bestPos.getDistance(agentPos)) {
				bestPos.setPosition(rectPos);
			}
		}
		for(int i=0; i<nStep/4; i++){
			rectPos.setPosition(-width, -height + 8*i/nStep*height);
			if(rectPos.getDistance(agentPos) < bestPos.getDistance(agentPos)) {
				bestPos.setPosition(rectPos);
			}
		}
		return bestPos;
	}

	@Override
	public boolean isInObstacle(Position p) {
		if(p.getY() < position.getY()+height && p.getY() > position.getY()-height){
			if(p.getX() < position.getX()+width && p.getX() > position.getX()-width) {
				return true;
			}
		}
		return false;
	}

}
