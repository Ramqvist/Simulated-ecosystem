package chalmers.dax021308.ecosystem.model.util;

public class StatTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Stat<Double> stat = new Stat<Double>();
		for(int i=1;i<100;i++){//Creates an arithmetic sum with mean = 50.5.
			stat.addObservation((double)i);
		}
		System.out.println(stat.getMean());
		System.out.println(stat.getSampleVariance());
	}

}
