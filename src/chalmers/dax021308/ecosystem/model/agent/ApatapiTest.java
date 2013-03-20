package chalmers.dax021308.ecosystem.model.agent;

import java.util.List;

import com.amd.aparapi.Kernel;
import com.amd.aparapi.ProfileInfo;

public class ApatapiTest {
	
	public ApatapiTest() {
		final double[] plus = new double[655360];
		for(int i = 0; i < plus.length ; i++) {
			plus[i] = Math.random();
		}
		final double[] result = new double[655360];
		Kernel kernel = new Kernel() {
			@Override
			public void run() {
				int gid = getGlobalId();
				result[gid] = plus[gid] * plus[gid] * plus[gid];
				result[gid] = sin(result[gid]);
				result[gid] = plus[gid] * plus[gid] * plus[gid];
				result[gid] = sin(result[gid]);
			}
		};
		kernel.execute(1);
		System.out.println("Conversion time: " + kernel.getConversionTime());
		System.out.println("getAccumulatedExecutionTime time: " + kernel.getAccumulatedExecutionTime() );
		System.out.println("Execution time: " + kernel.getExecutionTime());
		System.out.println("Execution mode: " + kernel.getExecutionMode() );
		List<ProfileInfo> profileInfoList = kernel.getProfileInfo();
		System.out.println(profileInfoList);
		for (ProfileInfo profileInfo : profileInfoList) {
		    System.out.println(profileInfo);
		}

		System.out.println("Net time GPU: " + (kernel.getExecutionTime() - kernel.getConversionTime()) );
		
		executeJava();
	}
	
	public void executeJava() {
		final double[] plus = new double[655360];
		for(int i = 0; i < plus.length ; i++) {
			plus[i] = Math.random();
		}
		long time = System.currentTimeMillis();
		final double[] result = new double[655360];
		for(int i = 0; i < plus.length ; i++) {
			result[i] = plus[i] * plus[i] * plus[i];
			result[i] = Math.sin(result[i]);
			result[i] = plus[i] * plus[i] * plus[i];
			result[i] = Math.sin(result[i]);
		}
		time = System.currentTimeMillis() - time;
		System.out.println("Net time CPU: " + time );
		
	}
	
	public static void main(String[] args) {
		new ApatapiTest();
	}
	
	private class TestKernel extends Kernel {

		@Override
		public void run() {
			float x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x); x = 5 * 5 * 5;
			sin(x);
		}
		
	}
}
