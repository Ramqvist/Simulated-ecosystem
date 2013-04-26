package chalmers.dax021308.ecosystem.model.agent;

import static org.junit.Assert.assertEquals;

import java.awt.Color;
import java.awt.Dimension;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import chalmers.dax021308.ecosystem.model.environment.SurroundingsSettings;
import chalmers.dax021308.ecosystem.model.environment.obstacle.IObstacle;
import chalmers.dax021308.ecosystem.model.population.AbstractPopulation;
import chalmers.dax021308.ecosystem.model.population.IPopulation;
import chalmers.dax021308.ecosystem.model.util.shape.IShape;
import chalmers.dax021308.ecosystem.model.util.Position;
import chalmers.dax021308.ecosystem.model.util.Vector;


public class AbstractAgentTest {

	private AbstractAgent agent;
	private String name;
	private Position position;
	private Color color;
	private int width;
	private int height;
	private Vector velocity;
	private double maxSpeed;
	private double visionRange;
	private double maxAcceleration;
	
	@Before
	public void init() {
		name = "Test Agent";
		position = new Position(20, 30);
		color = Color.pink;
		width = 10;
		height = 15;
		velocity = new Vector(4, -3);
		maxSpeed = 12;
		visionRange = 5;
		maxAcceleration = 3;
		
		agent = new AbstractAgent(name, position, color, width, height, velocity, maxSpeed, visionRange, maxAcceleration) {
			
			@Override
			public List<IAgent> reproduce(IAgent agent, int populationSize,
					SurroundingsSettings surroundings) {
				return null;
			}
			
			@Override
			public void calculateNextPosition(List<IPopulation> predators,
					List<IPopulation> preys, List<IPopulation> neutral, SurroundingsSettings surroundings) {
			}
		};
	}
	
	@Test
	public void cloneTest() throws CloneNotSupportedException {
		AbstractAgent clone = new AbstractAgent(agent) {
			
			@Override
			public List<IAgent> reproduce(IAgent agent, int populationSize,
					SurroundingsSettings surroundings) {
				return null;
			}
			
			@Override
			public void calculateNextPosition(List<IPopulation> predators,
					List<IPopulation> preys, List<IPopulation> neutral, SurroundingsSettings surroundings) {
			}
		};
		
		assertEquals(agent.getName(), clone.getName());
		assertEquals(agent.getPosition(), clone.getPosition());
		assertEquals(agent.getColor(), clone.getColor());
		assertEquals(agent.getWidth(), clone.getWidth());
		assertEquals(agent.getHeight(), clone.getHeight());
		assertEquals(agent.getVelocity(), clone.getVelocity());
		assertEquals(Double.doubleToLongBits(agent.getMaxSpeed()), Double.doubleToLongBits(clone.getMaxSpeed()));
		assertEquals(Double.doubleToLongBits(agent.getVisionRange()), Double.doubleToLongBits(clone.getVisionRange()));
		assertEquals(Double.doubleToLongBits(agent.getMaxAcceleration()), Double.doubleToLongBits(clone.getMaxAcceleration()));
		
		clone = (AbstractAgent) agent.cloneAgent();
		
		assertEquals(agent.getName(), clone.getName());
		assertEquals(agent.getPosition(), clone.getPosition());
		assertEquals(agent.getColor(), clone.getColor());
		assertEquals(agent.getWidth(), clone.getWidth());
		assertEquals(agent.getHeight(), clone.getHeight());
		assertEquals(agent.getVelocity(), clone.getVelocity());
		assertEquals(Double.doubleToLongBits(agent.getMaxSpeed()), Double.doubleToLongBits(clone.getMaxSpeed()));
		assertEquals(Double.doubleToLongBits(agent.getVisionRange()), Double.doubleToLongBits(clone.getVisionRange()));
		assertEquals(Double.doubleToLongBits(agent.getMaxAcceleration()), Double.doubleToLongBits(clone.getMaxAcceleration()));
	}
	
	@Test
	public void updateNeighbourListTest() throws CloneNotSupportedException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		AbstractAgent agentClose = (AbstractAgent) agent.cloneAgent();
		AbstractAgent agentNotClose = (AbstractAgent) agent.cloneAgent();
		agentClose.setPosition(new Position(22, 31)); 
		agentNotClose.setPosition(new Position(40, 60));
		List<IAgent> agents = new ArrayList<IAgent>();
		agents.add(agentClose);
		agents.add(agentNotClose);
		
		AbstractPopulation pred = new AbstractPopulation() {};
		AbstractPopulation prey = new AbstractPopulation() {};
		AbstractPopulation neutral = new AbstractPopulation() {};
		
		List<IPopulation> predList = new ArrayList<IPopulation>();
		List<IPopulation> preyList = new ArrayList<IPopulation>();
		List<IPopulation> neutralList = new ArrayList<IPopulation>();
		
		predList.add(pred);
		preyList.add(prey);
		neutralList.add(neutral);
		
		Field agentsField = AbstractPopulation.class.getDeclaredField("agents");
		agentsField.setAccessible(true);
		agentsField.set(pred, agents);
		agentsField.set(prey, agents);
		agentsField.set(neutral, agents);
		
		Field neighbourCounterField = AbstractAgent.class.getDeclaredField("neighbourCounter");
		neighbourCounterField.setAccessible(true);
		Field NEIGHBOURS_UPDATE_THRESHOLDField = AbstractAgent.class.getDeclaredField("NEIGHBOURS_UPDATE_THRESHOLD");
		NEIGHBOURS_UPDATE_THRESHOLDField.setAccessible(true);
		
		neighbourCounterField.set(agent, 0);
		agent.updateNeighbourList(neutralList, preyList, predList);
		
		neighbourCounterField.set(agent, NEIGHBOURS_UPDATE_THRESHOLDField.get(agent));
		agent.updateNeighbourList(neutralList, preyList, predList);
		
		Field neutralNeighboursField = AbstractAgent.class.getDeclaredField("neutralNeighbours"); 
		neutralNeighboursField.setAccessible(true);
		Field preyNeighboursField = AbstractAgent.class.getDeclaredField("preyNeighbours");
		preyNeighboursField.setAccessible(true);
		Field predNeighboursField = AbstractAgent.class.getDeclaredField("predNeighbours");
		predNeighboursField.setAccessible(true); 
		
		((List<IAgent>)neutralNeighboursField.get(agent)).clear();
		((List<IAgent>)preyNeighboursField.get(agent)).clear();
		((List<IAgent>)predNeighboursField.get(agent)).clear();
		
		assertEquals(0, ((List<IAgent>)neutralNeighboursField.get(agent)).size());
		assertEquals(0, ((List<IAgent>)preyNeighboursField.get(agent)).size());
		assertEquals(0, ((List<IAgent>)predNeighboursField.get(agent)).size());
		
		neighbourCounterField.set(agent, NEIGHBOURS_UPDATE_THRESHOLDField.get(agent));
		agent.updateNeighbourList(neutralList, preyList, predList);
		
		assertEquals(1, ((List<IAgent>)neutralNeighboursField.get(agent)).size());
		assertEquals(1, ((List<IAgent>)preyNeighboursField.get(agent)).size());
		assertEquals(1, ((List<IAgent>)predNeighboursField.get(agent)).size());
	}
}












