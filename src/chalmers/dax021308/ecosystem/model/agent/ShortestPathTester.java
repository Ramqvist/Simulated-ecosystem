package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.awt.Dimension;
import java.util.List;
import java.util.PriorityQueue;

import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class ShortestPathTester {
	
	public ShortestPathTester() {
//		Position start = new Position(5.0, 5000.0);
//		Position end = new Position(5.0, -32.0);
		Position start = new Position(5244.0, 581.0);
		Position end = new Position(-525.0, 5321.0);
		long time = System.nanoTime();
		List<Position> result = Position.getShortestPathPriorityQueue(start, end);
		double elapsed = (System.nanoTime() - time)*0.000001;
		System.out.println("PriorityQueue Completed in: " + elapsed + " ms. Result: " + result);
		time = System.nanoTime();
		result = Position.getShortestPathHashSet(start, end);
		elapsed = (System.nanoTime() - time)*0.000001;
		System.out.println("HashSet Completed in: " + elapsed + " ms. ");
		
	}
		
		
	
	public static void main(String[] args) {
		new ShortestPathTester();
	}
}
