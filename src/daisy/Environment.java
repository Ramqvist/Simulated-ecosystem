package daisy;

import java.util.ArrayList;
import java.util.List;

import org.jfree.ui.RefineryUtilities;

/**
 * 
 * @author Albin Bramstång
 */
public class Environment implements Runnable {
	private int white;
	private int black;
	private double[] temperature; //Between 0-1
	private int delay;
	private int nInterations;
	private double growthRate;
	private double[] proportions;
	private double mutationRate;
	private int capacity;
	private double deathSupression;
	
	/**
	 * Creates an environment with black and white daisies. The populations change over time depending 
	 * on temperature, growth rate, death suppression and mutation rate. The amount of black daisies affect the temperature.
	 * 
	 * @param white - initial white daisy population
	 * @param black - initial black daisy population
	 * @param capacity - max number of daisies in the environment
	 * @param startTemperature - initial temperature
	 * @param nInterations - amount of iterations the simulation will run
	 * @param growthRate - the speed of which the daisies grow
	 * @param deathSuppression - limits the speed of death of the daisies
	 * @param delay - offset of temperature from the population of black daisies
	 * @param mutationRate - probability of mutation, i.e. change of color for new born daisies
	 */
	public Environment(int white, int black, int capacity, 
			double startTemperature, int nInterations, double growthRate, double deathSuppression, 
			int delay, double mutationRate) {
		
		this.white = white;
		this.black = black;
		this.capacity = capacity;
		temperature = new double[nInterations+1];
		temperature[0]=startTemperature;
		this.nInterations = nInterations;
		proportions = new double[nInterations];
		this.growthRate = growthRate;
		this.deathSupression = deathSuppression;
		this.delay = delay;
		this.mutationRate = mutationRate;
	}
	
	public Environment() {
		this(100, 100, 2000, 0.9, 500, 0.12, 10, 50, 0.1);
	}
	
	@Override
	public void run() {
		List<Integer> blackFlowerList = new ArrayList<Integer>(1024);
		List<Integer> whiteFlowerList = new ArrayList<Integer>(1024);
		List<Double> temperatureList = new ArrayList<Double>(1024);
		for(int iteration = 0; iteration < nInterations; iteration++) {
			
			//Killing white flowers
			int deadWhite = 0;
			for (int i = 0; i < white; i++) {
				if (Math.random() < (1-temperature[iteration])/deathSupression) {
					deadWhite++;
				}
			}
			
			//Killing black flowers
			int deadBlack = 0;
			for (int i = 0; i < black; i++) {
				if (Math.random() < temperature[iteration]/deathSupression) {
					deadBlack++;
				}
			}
			
			white -= deadWhite;
			black -= deadBlack;
			
			//Giving birth to new flowers
			int newFlowers = (int)((white+black)*growthRate*(1-(white+black)/capacity)+0.5);
			proportions[iteration] = ((double)black / (double)(black + white));
			
			for (int i = 0; i < newFlowers; i++) {
				if (Math.random() < proportions[iteration]) { //Black
					if (Math.random() < mutationRate) {
						white++;
					} else {
						black++;
					}
				} else {
					if (Math.random() < mutationRate) {
						black++;
					} else {
						white++;
					}
				}
			}

			//Updating temperature
			if(iteration>=delay){
				temperature[iteration+1] = ( proportions[iteration-delay] + temperature[iteration] + temperature[iteration-1]) / 3;
			} else {
				temperature[iteration+1] = temperature[iteration];
			}
			
			blackFlowerList.add(black);
			whiteFlowerList.add(white);
			temperatureList.add(temperature[iteration+1] * capacity);
			System.out.println("White: " + white + " Black: " + black + " Temp: " + temperature[iteration] + " proportion " + proportions[iteration]);
		}
		
		
        final DaisyLineFrame demo = new DaisyLineFrame("Daisy simulation", blackFlowerList, whiteFlowerList, temperatureList);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

	}
	
}
