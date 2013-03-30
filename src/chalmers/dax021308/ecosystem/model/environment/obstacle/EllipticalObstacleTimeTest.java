package chalmers.dax021308.ecosystem.model.environment.obstacle;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.util.Position;

public class EllipticalObstacleTimeTest {
	
	public static List<Position> positions;
	private static int nIterations = 100000;
	private static Dimension dim = new Dimension(1000,1000);
	
	public static void main(String[] args) {
		
		EllipticalObstacle eo = new EllipticalObstacle(200, 100, new Position(500,500), Color.blue);
		positions = new ArrayList<Position>();
		for(int i=0; i < nIterations; i++) {
			double x = Math.random()*dim.getWidth();
			double y = Math.random()*dim.getHeight();
			Position p = new Position(x,y);
			while(eo.isInObstacle(p)){
				x = Math.random()*dim.getWidth();
				y = Math.random()*dim.getHeight();
				p = new Position(x,y);
			}
			positions.add(p);
		}
		
		//Start time
		long start = System.nanoTime();
		for(Position p: positions) {
			eo.closestBoundary(p);
		}
		long end = System.nanoTime();
		double elapsedTime = roundTwoDecimals(end*0.000001 - start*0.000001);
		//End time
		System.out.println(elapsedTime);
	}
	
	public static double roundTwoDecimals(double d) {
		double result = d * 100;
		result = Math.round(result);
		result = result / 100;
		return result;
	}

}
