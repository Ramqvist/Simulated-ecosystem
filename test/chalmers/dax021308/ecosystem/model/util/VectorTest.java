package chalmers.dax021308.ecosystem.model.util;

import org.junit.Test;
import  static org.junit.Assert.*;

public class VectorTest {

	@Test
	public void test() {
		Vector v1 = new Vector();
		Vector v2 = new Vector(0, 0);
		assertEquals(v1, v2);
		
		Position p1 = new Position(5, 10);
		Position p2 = new Position(8, 5);
		v1 = new Vector(p1, p2);
		v2.setVector(5-8, 10-5);
		assertEquals(v1, v2);
		
		v1.setVector(6, 9);
		v2 = new Vector(v1);
		assertEquals(v1, v2);
		
		v1.setX(8);
		v1.setY(-13);
		v2.setVector(8, -13);
		assertEquals(v1, v2);
		
		v1.setVector(12, -6);
		v2.setVector(v1);
		assertEquals(v1, v2);
		
		Vector v3 = Vector.addVectors(v1, v2);
		v1.add(v2);
		assertEquals(v1, v3);
		
		v1.setVector(1, 2);
		v1.multiply(8);
		v2.setVector(8, 16);
		assertEquals(v1, v2);
		
		double norm = v1.getNorm();
		double expectedNorm = Math.sqrt(Math.pow(v1.getX(), 2) + Math.pow(v1.getY(), 2));
		assertEquals(Double.doubleToLongBits(norm), Double.doubleToLongBits(expectedNorm));
		
		v1.toUnitVector(); 
		assertEquals((int)(v1.getNorm() + 0.5), 1);
		
		v1.setVector(4, 5);
		v1.rotate(Math.PI);
		v1.setX((int)(v1.getX() - 0.5));
		v1.setY((int)(v1.getY() - 0.5));
		v2.setVector(-4, -5);
		assertEquals(v1, v2);
		
		v1.rotate(Math.PI);
		v1.setX((int)(v1.getX() + 0.5));
		v1.setY((int)(v1.getY() + 0.5));
		v2.setVector(4, 5);
		assertEquals(v1, v2);
		
		String s = "(1.0,2.0)";
		v1.setVector(1, 2);
		assertEquals(s, v1.toString());
		
		v1 = Vector.emptyVector();
		assertTrue(v1.isNullVector());
		v1.setVector(1, 0);
		assertFalse(v1.isNullVector());
		
		assertFalse(v1.equals(null));
		assertFalse(v1.equals(new Position()));
	}
}




















