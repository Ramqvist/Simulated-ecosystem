package chalmers.dax021308.ecosystem.model.agent;

import java.awt.Color;
import java.lang.reflect.Field;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;

import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;

public class DeerAgentTest {

	private DeerAgent deerTest;
	private String name;
	private Position p;
	private Color c;
	private int width;
	private int height;
	private Vector velocity;
	private double maxSpeed;
	private double maxAcceleration;
	private double visionRange;
	private boolean groupBehaviour;
	
	@Before
	public void init() {
		name = "Deer test";
		p = new Position();
		c = Color.black;
		width = 8;
		height = 12;
		velocity = new Vector();
		maxSpeed = 10;
		maxAcceleration = 3;
		visionRange = 5;
		groupBehaviour = true;
		deerTest = new DeerAgent(name, p, c, width, height, velocity, maxSpeed, maxAcceleration, visionRange, groupBehaviour);
	}
		
	@Test
	public void test() throws NoSuchFieldException, IllegalArgumentException, IllegalAccessException {
		Field hungryField = DeerAgent.class.getDeclaredField("hungry");
		hungryField.setAccessible(true);
		assertTrue(hungryField.get(deerTest).equals(true));
		assertEquals(null, deerTest.reproduce(null, 0, null, null, null));
		hungryField.set(deerTest, false);
		assertTrue(hungryField.get(deerTest).equals(false));
		
		
	}
}















