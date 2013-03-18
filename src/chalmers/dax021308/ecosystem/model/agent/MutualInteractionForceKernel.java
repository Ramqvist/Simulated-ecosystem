package chalmers.dax021308.ecosystem.model.agent;

import chalmers.dax021308.ecosystem.model.util.Position;

import com.amd.aparapi.Kernel;

public class MutualInteractionForceKernel extends Kernel {
	//Only final fields in kernels.
	private final double[] xPosArray;
	private final double[] yPosArray;
	public final double[] xResult;
	public final double[] yResult;

	private final double INTERACTION_RANGE;
	private final double myPosX;
	private final double myPosY;
	
	public MutualInteractionForceKernel(int size, double INTERACTION_RANGE, double myPosX, double myPosY, double[] xPosArray, double[] yPosArray) {
		this.xPosArray = xPosArray;
		this.yPosArray = yPosArray;
		this.INTERACTION_RANGE = INTERACTION_RANGE;
		this.myPosX = myPosX;
		this.myPosY = myPosY;
		xResult = new double[size];
		yResult = new double[size];
	}

	@Override
	public void run() {
			int gid = getGlobalId();		
			double newForceX = 0;
			double newForceY = 0;
			double mutualInteractionForceX = 0;
			double mutualInteractionForceY = 0;
//			double dX = 0;
//			double dY = 0;
			double xAgentPosition = xPosArray[gid];
			double yAgentPosition = yPosArray[gid];
			double v = 0;
			double norm = 0;
			double distance = 0;
//			double distance = getDistance(myPosX, myPosY, xAgentPosition, yAgentPosition);
			double dX = myPosX - xAgentPosition;
			double dY = myPosY - yAgentPosition;
			
			distance = sqrt(pow(dX, 2.0) + pow(dY, 2.0));
			double Q = 0; // Q is a function of the distance.
			if (distance <= INTERACTION_RANGE) {
				Q = -20.0 * (INTERACTION_RANGE - distance);
			} else {
				Q = 1.0;
			}	
			newForceX = xAgentPosition - myPosX;
			newForceY = yAgentPosition - myPosY;
			norm = sqrt((newForceX*newForceX)+(newForceY*newForceY));
			v = Q / (norm * distance);
			newForceX = newForceX * v;
			newForceY = newForceY * v;
			xResult[gid] = mutualInteractionForceX + newForceX;
			yResult[gid] = mutualInteractionForceY + newForceY;
	}	
}
