package chalmers.dax021308.ecosystem.model.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import chalmers.dax021308.ecosystem.model.agent.DeerAgent;
import chalmers.dax021308.ecosystem.model.agent.IAgent;
import chalmers.dax021308.ecosystem.model.genetics.GenomeFactory;
import chalmers.dax021308.ecosystem.model.util.shape.*;
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
	private IShape shape;
	private Dimension dimension;
	
	@Before
	public void init() {
		name = "Deer test";
		p = new Position(20, 20);
		c = Color.black;
		width = 8;
		height = 12;
		velocity = new Vector();
		maxSpeed = 10;
		maxAcceleration = 3;
		visionRange = 5;
		groupBehaviour = true;
		deerTest = new DeerAgent(name, p, c, width, height, velocity, maxSpeed, maxAcceleration, visionRange, groupBehaviour, GenomeFactory.deerGenomeFactory());
		shape = new SquareShape();
		dimension = new Dimension(50, 50);
	}
		
	@Test
	public void reproduceTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Field hungryField = DeerAgent.class.getDeclaredField("hungry");
		hungryField.setAccessible(true);
		assertTrue(hungryField.get(deerTest).equals(true));
		assertEquals(null, deerTest.reproduce(null, 0, null, null, null));
		hungryField.set(deerTest, false);
		assertTrue(hungryField.get(deerTest).equals(false));
		
		List<IAgent> children;
		do {
			children = deerTest.reproduce(null, 0, null, shape, dimension);
			hungryField.set(deerTest, false);
		} while (children.size() == 0); 
		
		IAgent child = children.get(0);
		
		assertEquals(deerTest.getName(), child.getName());
		assertTrue(!deerTest.getPosition().equals(child.getPosition()));
		assertEquals(deerTest.getColor(), child.getColor());
		assertEquals(deerTest.getWidth(), child.getWidth());
		assertEquals(deerTest.getHeight(), child.getHeight());
		assertEquals(deerTest.getVelocity(), child.getVelocity());
		assertEquals(Double.doubleToLongBits(deerTest.getVisionRange()), Double.doubleToLongBits(child.getVisionRange()));
		assertEquals(Double.doubleToLongBits(deerTest.getMaxAcceleration()), Double.doubleToLongBits(child.getMaxAcceleration()));
		assertEquals(Double.doubleToLongBits(deerTest.getMaxSpeed()), Double.doubleToLongBits(child.getMaxSpeed()));
	}
}















