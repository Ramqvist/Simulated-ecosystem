package chalmers.dax021308.ecosystem.model.agent;

import com.amd.aparapi.Kernel;

public class ApatapiTest {
	
	public ApatapiTest() {
		double[] xPos = new double[15];
		double[] yPos = new double[15];
		MutualInteractionForceKernel kernel = new MutualInteractionForceKernel(100, 140, 1.1, 1.1, xPos, yPos);
		kernel.execute(15);
		System.out.println(kernel.getConversionTime());
		System.out.println(kernel.getExecutionTime());
	}
	
	public static void main(String[] args) {
		new ApatapiTest();
	}
	
	private class TestKernel extends Kernel {

		@Override
		public void run() {
			float x = 5 * 5 * 5;
			x = 5 * 5 * 5 * x;
			x = 5 * 5 * 5 * x * 0.05F;
			x = 5 * 5 * 5 * x * 0.05F;
			x = 5 * 5 * 5 * x * 0.05F;
			x = 5 * 5 * 5 * x * 0.05F;
			x = 5 * 5 * 5 * x * 0.05F;
		}
		
	}
}
