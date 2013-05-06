package chalmers.dax021308.ecosystem.model.population.settings;

import chalmers.dax021308.ecosystem.model.population.settings.CommonSettings.DoubleSettingsContainer;


public class PredSettings extends CommonSettings {
	public static PredSettings instance = new PredSettings();
	
	public PredSettings() {
		super();
		capacity 			= new DoubleSettingsContainer("Capacity", 1, Integer.MAX_VALUE, Integer.MAX_VALUE);
		maxSpeed 			= new DoubleSettingsContainer("Max speed", 1, 100, 2.3);
		maxAcceleration 	= new DoubleSettingsContainer("Max Acceleration", 1, 100, 0.4);
		visionRange 		= new DoubleSettingsContainer("Vision range", 1, 1000, 250);
		width 				= new DoubleSettingsContainer("Width", 1, 100, 10);
		height				= new DoubleSettingsContainer("Height", 1, 100, 20);

		reproduction_rate	= new DoubleSettingsContainer("Reproduction rate", 0, 1, 0.1);
		
		doubleSettings.add(maxSpeed);
		doubleSettings.add(capacity);
		doubleSettings.add(visionRange);
		doubleSettings.add(maxAcceleration);
		doubleSettings.add(width);
		doubleSettings.add(height);
		doubleSettings.add(reproduction_rate);
		
		doubleSettings.remove(capacity);
	}
}
