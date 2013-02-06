package daisy;

import java.util.ArrayList;
import java.util.List;

import org.jfree.ui.RefineryUtilities;

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
	
	public Environment(int white, int black,int capacity, double temp, int nInterations, double growthRate, int delay, double mutationRate) {
		this.white = white;
		this.black = black;
		temperature = new double[nInterations+1];
		temperature[0]=temp;
		this.delay = delay;
		this.nInterations = nInterations;
		this.growthRate = growthRate;
		proportions = new double[nInterations];
		this.mutationRate = mutationRate;
		this.capacity = capacity;
	}
	
	
	@Override
	public void run() {
		List<Integer> blackFlowerList = new ArrayList<Integer>();
		List<Integer> whiteFlowerList = new ArrayList<Integer>();
		for(int iteration = 0; iteration < nInterations; iteration++) {
			
			//Killing white flowers
			int deadWhite = 0;
			for (int i = 0; i < white; i++) {
				if (Math.random() < (1-temperature[iteration])/5) {
					deadWhite++;
				}
			}
			
			//Killing black flowers
			int deadBlack = 0;
			for (int i = 0; i < black; i++) {
				if (Math.random() < temperature[iteration]/5) {
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
				temperature[iteration+1] = proportions[iteration-delay];
			} else {
				temperature[iteration+1] = temperature[iteration];
			}
			
			blackFlowerList.add(black);
			whiteFlowerList.add(white);
			System.out.println("White: " + white + " Black: " + black + " Temp: " + temperature + " proportion " + proportions[iteration]);
//			System.out.println("Black: " + black);
		}
		
		
        final DaisyLineFrame demo = new DaisyLineFrame("Line Chart Demo 6", blackFlowerList, whiteFlowerList);
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);

	}
	
}
