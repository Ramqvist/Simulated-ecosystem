package chalmers.dax021308.ecosystem.model.util;

import java.awt.Color;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

import chalmers.dax021308.ecosystem.model.environment.obstacle.AbstractObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.RectangularObstacle;
import chalmers.dax021308.ecosystem.model.util.shape.IShape;
import chalmers.dax021308.ecosystem.model.util.shape.SquareShape;

public class ShortestPathTest {


	private static List<IObstacle> obsList = new ArrayList<IObstacle>();
	private static IShape shape = new SquareShape();

	static {
		obsList.add(new RectangularObstacle(200, 50, new Position(250, 400), Color.GRAY, 0, false));
		obsList.add(new RectangularObstacle(50, 200, new Position(400, 250), Color.GRAY, 0, false));
	}


	@Test
	public void shortestPathTest1() {
		Position start = new Position(125, 320);
		Position end = new Position(765, 565);
		Dimension dim = new Dimension(1000, 1000);
		if(AbstractObstacle.isInsideObstacleList(obsList, start) || AbstractObstacle.isInsideObstacleList(obsList, end)) {
			Log.e("Either positions is inside obstacle.");
		}
		JPSPathfinder finder = new JPSPathfinder(obsList, shape, dim);
		List<Position> path = finder.getShortestPath(start, end, 0);
		Position lastPostion = path.get(path.size() - 1);
		Position firstPosition = path.get(0);
		Assert.assertTrue(lastPostion.equals(end));
		Assert.assertTrue(firstPosition.equals(start));
	}


	@Test
	public void shortestPathTest2() {
		Position end = new Position(125, 320);
		Position start = new Position(765, 565);
		Dimension dim = new Dimension(1000, 1000);
		if(AbstractObstacle.isInsideObstacleList(obsList, start) || AbstractObstacle.isInsideObstacleList(obsList, end)) {
			Log.e("Either positions is inside obstacle.");
		}
		JPSPathfinder finder = new JPSPathfinder(obsList, shape, dim);
		List<Position> path = finder.getShortestPath(start, end, 0);
		Position lastPostion = path.get(path.size() - 1);
		Position firstPosition = path.get(0);
		Assert.assertTrue(lastPostion.equals(end));
		Assert.assertTrue(firstPosition.equals(start));
	}
}
