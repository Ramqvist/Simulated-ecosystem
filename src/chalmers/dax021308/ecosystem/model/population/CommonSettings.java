package chalmers.dax021308.ecosystem.model.population;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Common settings for all agents to use.
 * 
 * Sets and gets from CommonSettingsPanel.
 * 
 * @author Erik Ramqvist
 *
 */
public class CommonSettings {
	public static CommonSettings predSettings = new CommonSettings();
	public static CommonSettings preySettings = new CommonSettings();
	public static CommonSettings grassSettings = new CommonSettings();
	
	public class DoubleSettingsContainer {
		public DoubleSettingsContainer(String name, double max, double min, double defaultValue) {
			this.value = defaultValue;
			this.defaultValue = defaultValue;
			this.max = max;
			this.min = min;
			this.name = name;
		}
		public String name;
		public double value;
		public double max;
		public double min;
		public double defaultValue;
	}

	public class BooleanSettingsContainer {
		public BooleanSettingsContainer(String name, boolean defaultValue) {
			this.defaultValue = defaultValue;
			this.value = defaultValue;
		}
		public String name;
		public boolean value;
		public boolean defaultValue;
	}
	
	private List<DoubleSettingsContainer> doubleSettings;
	private List<BooleanSettingsContainer> booleanSettings;
	
	
	//TODO: Replace ALL variables with SettingsContainers.
	private int capacity;
	private int max_energy;
	private DoubleSettingsContainer visionRange;
	private DoubleSettingsContainer maxAcceleration;
	private double maxSpeed;
	private int width;
	private int height;
	private double interaction_range;
	private double eating_range;
	private double focus_range;
	private double velocity_decay;
	private boolean groupBehavior;
	
	public CommonSettings() {
		doubleSettings = new ArrayList<DoubleSettingsContainer>();
		visionRange = new DoubleSettingsContainer("Vision range", 1000, 1, 500);
		doubleSettings.add(visionRange);
		maxAcceleration = new DoubleSettingsContainer("Max Acceleration", 1000, 1, 500);
		doubleSettings.add(visionRange);
		visionRange = new DoubleSettingsContainer("Chalmers range", 1000, 1, 500);
		doubleSettings.add(visionRange);
		visionRange = new DoubleSettingsContainer("BigTasty range", 1000, 1, 500);
		doubleSettings.add(visionRange);
		Random ran = new Random();
		visionRange = new DoubleSettingsContainer("Life length", 1000, 1, ran.nextInt(1000));
		doubleSettings.add(visionRange);
		visionRange = new DoubleSettingsContainer("Eating range", 1000, 1, ran.nextInt(1000));
		doubleSettings.add(visionRange);
		visionRange = new DoubleSettingsContainer("Velocity decay", 1000, 1, ran.nextInt(1000));
		doubleSettings.add(visionRange);
		booleanSettings = new ArrayList<BooleanSettingsContainer>();
	}
	public List<DoubleSettingsContainer> getDoubleSettings() {
		return doubleSettings;
	}
	public void setDoubleSettings(List<DoubleSettingsContainer> doubleSettings) {
		this.doubleSettings = doubleSettings;
	}
	public List<BooleanSettingsContainer> getBooleanSettings() {
		return booleanSettings;
	}
	public void setBooleanSettings(
			List<BooleanSettingsContainer> booleanSettings) {
		this.booleanSettings = booleanSettings;
	}
	public double getMaxSpeed() {
		return maxSpeed;
	}
	public void setMaxSpeed(double maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	public int getMax_energy() {
		return max_energy;
	}
	public void setMax_energy(int max_energy) {
		this.max_energy = max_energy;
	}
	public double getVisionRange() {
		return visionRange.value;
	}
	public void setVisionRange(double visionRange) {
		this.visionRange.value = visionRange;
	}
	public double getMaxAcceleration() {
		return maxAcceleration.value;
	}
	public void setMaxAcceleration(double maxAcceleration) {
		this.maxAcceleration.value = maxAcceleration;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public double getInteraction_range() {
		return interaction_range;
	}
	public void setInteraction_range(double interaction_range) {
		this.interaction_range = interaction_range;
	}
	public double getEating_range() {
		return eating_range;
	}
	public void setEating_range(double eating_range) {
		this.eating_range = eating_range;
	}
	public double getFocus_range() {
		return focus_range;
	}
	public void setFocus_range(double focus_range) {
		this.focus_range = focus_range;
	}
	public double getVelocity_decay() {
		return velocity_decay;
	}
	public void setVelocity_decay(double velocity_decay) {
		this.velocity_decay = velocity_decay;
	}
	public boolean isGroupBehavior() {
		return groupBehavior;
	}
	public void setGroupBehavior(boolean groupBehavior) {
		this.groupBehavior = groupBehavior;
	}
}
