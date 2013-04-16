package chalmers.dax021308.ecosystem.model.util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.environment.obstacle.RectangularObstacle;
import static org.junit.Assert.*;
import static java.lang.Double.*;


public class PositionTest {
	
	@Test
	public void basicTest() {
		Position p = new Position(5 , 5);
		Position q = new Position(p);
		assertEquals(doubleToLongBits(q.getX()), doubleToLongBits(5d));
		assertEquals(doubleToLongBits(q.getY()), doubleToLongBits(5d));
		
		p.setX(7);
		p.setY(8);
		assertEquals(doubleToLongBits(p.getX()), doubleToLongBits(7d));
		assertEquals(doubleToLongBits(p.getY()), doubleToLongBits(8d));
		
		p.setPosition(14, 15);
		assertEquals(doubleToLongBits(p.getX()), doubleToLongBits(14d));
		assertEquals(doubleToLongBits(p.getY()), doubleToLongBits(15d));
		
		p.setPosition(q);
		assertEquals(doubleToLongBits(p.getX()), doubleToLongBits(5d));
		assertEquals(doubleToLongBits(p.getY()), doubleToLongBits(5d));
	}
	
	@Test
	public void getDistanceTest() {
		Position origin = new Position();
		Position p1 = new Position(2, 2);
		Position p2 = new Position(-2, 2);
		Position p3 = new Position(-2, -2);
		Position p4 = new Position(2, -2);
		
		double controlDistance = Math.sqrt(2*2 + 2*2);
		long ctrl = doubleToLongBits(controlDistance);
		
		assertEquals(doubleToLongBits(origin.getDistance(p1)), ctrl);
		assertEquals(doubleToLongBits(origin.getDistance(p2)), ctrl);
		assertEquals(doubleToLongBits(origin.getDistance(p3)), ctrl);
		assertEquals(doubleToLongBits(origin.getDistance(p4)), ctrl);
	}
	
	@Test
	public void addVectorTest() {
		Vector v = new Vector(3, 4);
		Position p = new Position(1, 2);
		p.addVector(v);
		assertEquals(doubleToLongBits(p.getX()), doubleToLongBits(4d));
		assertEquals(doubleToLongBits(p.getY()), doubleToLongBits(6d));
		
		p = new Position();
		v.multiply(-1);
		p.addVector(v);
		assertEquals(doubleToLongBits(p.getX()), doubleToLongBits(-3d));
		assertEquals(doubleToLongBits(p.getY()), doubleToLongBits(-4d));
	}
	
	@Test
	public void positionPlusVectorTest() {
		Position p = new Position(5, 10);
		Vector v = new Vector(1, 1);
		Position q = Position.positionPlusVector(p, v);
		assertEquals(doubleToLongBits(q.getX()), doubleToLongBits(6d));
		assertEquals(doubleToLongBits(q.getY()), doubleToLongBits(11d));
	}
	
	@Test
	public void equalsTest() {
		Position p = new Position();
		Position q = new Position();
		Position r = new Position(1, 1);
		Position s = new Position(0, 1);
		assertTrue(p.equals(p));
		assertFalse(p.equals(null));
		assertTrue(p.equals(q));
		assertFalse(p.equals(r));
		assertFalse(p.equals(new Integer(0)));
		assertFalse(p.equals(s));
	}
	
	@Test
	public void hashCodeTest() {
		Position p = new Position();
		Position q = new Position();
		Position r = new Position(2, 2);
		assertEquals(p.hashCode(), q.hashCode());
		assertNotSame(p.hashCode(), r.hashCode());
	}
}





