package chalmers.dax021308.ecosystem.model.population.settings;

public class GrassFieldSettings extends CommonSettings {
	public static GrassFieldSettings instance = new GrassFieldSettings();

	public GrassFieldSettings() {
		super();
		doubleSettings.remove(interaction_range);
		doubleSettings.remove(eating_range);
		doubleSettings.remove(focus_range);
		doubleSettings.remove(velocity_decay);
		booleanSettings.remove(groupBehavior);
		booleanSettings.remove(pathFinding);
		doubleSettings.remove(obstacle_safety_distance);

		DoubleSettingsContainer maxEnergy = new DoubleSettingsContainer(
				"Max Energy", 1, 5000, 200);
		DoubleSettingsContainer timeToBloom = new DoubleSettingsContainer(
				"Time to Bloom", 0, 5000, 100);
		reproductionRate = new DoubleSettingsContainer("Reproduction rate", 0,
				1, 0.2);

		doubleSettings.add(maxEnergy);
		doubleSettings.add(timeToBloom);
		doubleSettings.add(reproductionRate);
	}

}
