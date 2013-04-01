package chalmers.dax021308.ecosystem.model.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Sebbe
 * This is a class for statistical calculations.
 */
public class Stat<T extends Number> {
	
	private List<T> sample;
	private double mean;
	
	/**
	 * Creates a new and empty Stat object.
	 */
	public Stat(){
		sample = new ArrayList<T>();
		mean = 0;
	}
	
	/**
	 * Add and observation to the Stat sample
	 * @param obs observation to add.
	 */
	public void addObservation(T obs){
		sample.add(obs);
		double sampleSize = sample.size();
		if(sampleSize <= 1){
			mean = obs.doubleValue();
		} else {
			mean = ((mean)*(sampleSize-1)+obs.doubleValue())/sampleSize;
		}
	}
	
	public double getMean(){
		return mean;
	}
	
	public double getSampleVariance(){
		int sampleSize = sample.size();
		if(sampleSize<2){
			return 0;
		}
		int sum = 0;
		for(int i=0; i<sampleSize; i++){
			sum += (sample.get(i).doubleValue()-mean)*(sample.get(i).doubleValue()-mean);
		}
		return sum/(sampleSize-1.0);
	}
	
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

	@Override
	public String toString(){
		return "mean = " + roundTwoDecimals(this.mean) + " | std = " + roundTwoDecimals(this.getSampleVariance());
	}
	
	public static double roundTwoDecimals(double d) {
		double result = d * 100;
		result = Math.round(result);
		result = result / 100;
		return result;
	}
	
}
