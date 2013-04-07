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
		Position start = new Position(1.0, 1.0);
		Position end = new Position(40.0, 40.0);
		System.out.println("Distance: " + start.getDistance(end));
		//Threshold for using PriorityQueue is 45, lower use HashSet!
		long time;
		double elapsed;
		List<Position> result;
		time = System.nanoTime();
		result = Position.getShortestPathHashSet(start, end);
		elapsed = (System.nanoTime() - time)*0.000001;
		System.out.println("HashSet Completed in: " + elapsed + " ms. Positions: " + result.size() + " Result: " + result);
		time = System.nanoTime();
		result = Position.getShortestPathPriorityQueue(start, end);
		elapsed = (System.nanoTime() - time)*0.000001;
		System.out.println("PriorityQueue Completed in: " + elapsed + " ms. Positions: " + result.size() + " Result: " + result);

//		time = System.nanoTime();
//		result = Position.getShortestPath(start, end);
//		elapsed = (System.nanoTime() - time)*0.000001;
//		System.out.println("Optimal solution Completed in: " + elapsed + " ms. Positions: " + result.size() + " Result: " + result);
//		
	}
		
		
	
	public static void main(String[] args) {
		new ShortestPathTester();
	}
}
