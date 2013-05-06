package chalmers.dax021308.ecosystem.model.population.settings;



public class GrassSettings extends CommonSettings {
	public static GrassSettings instance = new GrassSettings();
	
	public GrassSettings() {
		super();
		
		doubleSettings.remove(interaction_range);
		doubleSettings.remove(eating_range);
		doubleSettings.remove(focus_range);
		doubleSettings.remove(velocity_decay);
		booleanSettings.remove(groupBehavior);
		booleanSettings.remove(pathFinding);
		doubleSettings.remove(capacity);
		
		capacity = new DoubleSettingsContainer("Capacity", 1, 2000, 800);
		reproduction_rate	= new DoubleSettingsContainer("Reproduction rate", 0, 1, 0.01);
		
		doubleSettings.add(capacity);
		doubleSettings.add(reproduction_rate);
		
//		capacity 			= new DoubleSettingsContainer("Capacity", 1, Integer.MAX_VALUE, Integer.MAX_VALUE);
//		maxSpeed 			= new DoubleSettingsContainer("Max speed", 1, 100, 2); // 2.3
//		maxAcceleration 	= new DoubleSettingsContainer("Max Acceleration", 1, 100, 1); //0.5
//		visionRange 		= new DoubleSettingsContainer("Vision range", 1, 1000, 250);
//		width 				= new DoubleSettingsContainer("Width", 1, 100, 10);
//		height				= new DoubleSettingsContainer("Height", 1, 100, 20);
//		
//		doubleSettings.add(maxSpeed);
//		doubleSettings.add(capacity);
//		doubleSettings.add(visionRange);
//		doubleSettings.add(maxAcceleration);
//		doubleSettings.add(width);
//		doubleSettings.add(height);
	}
}
