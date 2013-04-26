package chalmers.dax021308.ecosystem.model.environment.obstacle;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Stat;

public class RectangularObstacleTimeTest {
	
	public static List<Position> positions;
	private static int nIterations = 10000;
	private static Dimension dim = new Dimension(1000,1000);
	private static Stat<Double> timeSample = new Stat<Double>();
	private static int sampleSize = 100;
	
	public static void main(String[] args) {
		
		RectangularObstacle ro = new RectangularObstacle(200, 100, new Position(500,500), Color.blue);
		
		for(int obs = 0; obs < sampleSize; obs++) {
			
			positions = new ArrayList<Position>();
			for(int i=0; i < nIterations; i++) {
				double x = Math.random()*dim.getWidth();
				double y = Math.random()*dim.getHeight();
				Position p = new Position(x,y);
				while(ro.isInObstacle(p,0)){
					x = Math.random()*dim.getWidth();
					y = Math.random()*dim.getHeight();
					p = new Position(x,y);
				}
				positions.add(p);
			}
			
			//Start time
			long start = System.nanoTime();
			for(Position p: positions) {
				if(ro.isCloseTo(p, 10)) {
					ro.closestBoundary(p);
				}
			}
			long end = System.nanoTime();
			double elapsedTime = end*0.000001 - start*0.000001;
			//End time
			
			timeSample.addObservation(elapsedTime);
			System.out.println("Iteration: " + obs + " finished.");
		}
		System.out.println(timeSample);
	}
	
}
