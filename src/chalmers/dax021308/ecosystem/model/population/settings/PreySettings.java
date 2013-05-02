package chalmers.dax021308.ecosystem.model.population.settings;


public class PreySettings extends CommonSettings {
	public static PreySettings instance = new PreySettings();
	
	public PreySettings() {
		super();
		capacity 			= new DoubleSettingsContainer("Capacity", 1, Integer.MAX_VALUE, Integer.MAX_VALUE);
		maxSpeed 			= new DoubleSettingsContainer("Max speed", 1, 100, 2); 	
		maxAcceleration 	= new DoubleSettingsContainer("Max Acceleration", 1, 100, 3);
		visionRange 		= new DoubleSettingsContainer("Vision range", 1, 1000, 200);
		width 				= new DoubleSettingsContainer("Width", 1, 100, 5);
		height				= new DoubleSettingsContainer("Height", 1, 100, 10);
		reproduction_rate	= new DoubleSettingsContainer("Reproduction rate", 0, 1, 0.08);
		
		doubleSettings.add(maxSpeed);
		doubleSettings.add(capacity);
		doubleSettings.add(visionRange);
		doubleSettings.add(maxAcceleration);
		doubleSettings.add(width);
		doubleSettings.add(height);
		doubleSettings.add(reproduction_rate);

//		TODO: Add to deer specific variables.
//		TODO: Make CommonSettings abstract and make extending classes.
//		private double STOTTING_RANGE = 10;
//		private double STOTTING_LENGTH = 8;
//		private double STOTTING_COOLDOWN = 50;
//		private double stottingDuration = STOTTING_LENGTH;
//		private double stottingCoolDown = 0;
//		digestion_time		= new DoubleSettingsContainer("Digestion time", 1, 1000, 10);
//		reproduction_rate	= new DoubleSettingsContainer("Reproduction rate", 1, 1000, 10);
//
//		public DoubleSettingsContainer digestion_time;
//		public DoubleSettingsContainer reproduction_rate;
	}
}
