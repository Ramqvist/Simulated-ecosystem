package chalmers.dax021308.ecosystem.model.agent;

import com.amd.aparapi.Kernel;

public class ApatapiTest {
	
	public ApatapiTest() {
		double[] xPos = new double[15];
		double[] yPos = new double[15];
		TestKernel kernel = new TestKernel();
		kernel.execute(5);
		System.out.println("Conversion time: " + kernel.getConversionTime());
		System.out.println("Execution time: " + kernel.getExecutionTime());
		System.out.println("Net time: " + (kernel.getExecutionTime() - kernel.getConversionTime()) );
		
		System.out.println("Execution mode: " + kernel.getExecutionMode() );
	}
	
	public static void main(String[] args) {
		new ApatapiTest();
	}
	
	private class TestKernel extends Kernel {

		@Override
		public void run() {
			float x = 5 * 5 * 5;
			sin(x);
		}
		
	}
}
