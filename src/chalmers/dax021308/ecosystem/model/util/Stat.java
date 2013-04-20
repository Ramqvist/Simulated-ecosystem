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
	
	public List<T> getSample(){
		return sample;
	}
	
	/**
	 * Add and observation to the Stat sample
	 * @param obs observation to add.
	 */
	public void addObservation(T obs){
		sample.add(obs);
		double n = sample.size();
		mean = ((mean)*(n-1)+obs.doubleValue())/n;
	}
	
	public double getMean(){
		return mean;
	}
	
	public double getSampleVariance(){
		double n = sample.size();
		if(n<=1){
			return 0;
		}
		double sum = 0;
		for(int i=0; i<n; i++){
			sum += (sample.get(i).doubleValue()-mean)*(sample.get(i).doubleValue()-mean);
		}
		return sum/(n-1.0);
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
		return "mean = " + roundNDecimals(this.mean, 3) + " | std = " + roundNDecimals(this.getSampleVariance(), 3);
	}
	
	public static double roundNDecimals(double d, int N) {
		double factor = Math.pow(10, N);
		double result = d * factor;
		result = Math.round(result);
		result = result / factor;
		return result;
	}
	
	public static Vector getNormallyDistributedVector(){
		return getNormallyDistributedVector(1);
	}
	
	public static Vector getNormallyDistributedVector(double std){
		double U = Math.random();
		double V = Math.random();
		double x = Math.sqrt(-2*Math.log(U))*Math.cos(2*Math.PI*V);
		double y = Math.sqrt(-2*Math.log(U))*Math.sin(2*Math.PI*V);
		return new Vector(std*x,std*y);
	}
	
}
