package chalmers.dax021308.ecosystem.model.util;

import java.util.List;

/**
 * @author Sebbe
 * This is a class for statistical calculations.
 */
public class Stat {
	
	/**
	 * Calculates the mean of a sample
	 * @param sample the sample to calculate the mean of
	 * @return the mean of the sample
	 */
	public static double mean(List<Integer> sample){
		int sampleSize = sample.size();
		if(sampleSize<1) {
			return 0;
		}
		int sum = 0;
		for(int i=0; i<sampleSize; i++){
			sum += sample.get(i);
		}
		return ((double)sum)/((double)sampleSize);
	}
	
	/**
	 * Calculates the sample variance (s^2) of a sample
	 * @param sample the sample to calculate the sample variance of
	 * @return the sample variance of the sample
	 */
	public static double sampleVariance(List<Integer> sample){
		int sampleSize = sample.size();
		if(sampleSize<2){
			return 0;
		}
		double mean = Stat.mean(sample);
		int sum = 0;
		for(int i=0; i<sampleSize; i++){
			sum += (((double)sample.get(i))-mean)*(((double)sample.get(i))-mean);
		}
		return ((double)sum)/(((double)sampleSize)-1.0);
	}

}
