package chalmers.dax021308.ecosystem.model.agent;

import chalmers.dax021308.ecosystem.model.util.Position;

import com.amd.aparapi.Kernel;

public class MutualInteractionForceKernel extends Kernel {
	//Only final fields in kernels.
	public final double[] xPosArray;
	public final double[] yPosArray;
	public final double[] xResult;
	public final double[] yResult;

	public final double INTERACTION_RANGE;
	public final double myPosX;
	public final double myPosY;
	
	public MutualInteractionForceKernel(int size, double INTERACTION_RANGE, double myPosX, double myPosY) {
		xPosArray = new double[size];
		yPosArray = new double[size];
		this.INTERACTION_RANGE = INTERACTION_RANGE;
		this.myPosX = myPosX;
		this.myPosY = myPosY;
		xResult = new double[size];
		yResult = new double[size];
	}

	@Override
	public void run() {
			double newForceX = 0;
			double newForceY = 0;
			double mutualInteractionForceX = 0;
			double mutualInteractionForceY = 0;
			double dX = 0;
			double dY = 0;
			int gid = getGlobalId();
			double xAgentPosition = xPosArray[gid];
			double yAgentPosition = yPosArray[gid];
			double v = 0;
			double norm = 0;
			double distance = 0;
//			double distance = getDistance(myPosX, myPosY, xAgentPosition, yAgentPosition);
			dX = myPosX - xAgentPosition;
			dY = myPosY - yAgentPosition;
			//Math library allowed in Aparapi!
			distance = Math.sqrt(dX * dX + dY * dY);
			double Q = 0; // Q is a function of the distance.
			if (distance <= INTERACTION_RANGE) {
				Q = -20 * (INTERACTION_RANGE - distance);
			} else {
				Q = 1;
			}	
			newForceX = xAgentPosition - myPosX;
			newForceY = yAgentPosition - myPosY;
			norm = Math.sqrt((newForceX*newForceX)+(newForceY*newForceY));
			v = Q / (norm * distance);
			newForceX = newForceX * v;
			newForceY = newForceY * v;
			xResult[gid] = mutualInteractionForceX + newForceX;
			yResult[gid] = mutualInteractionForceY + newForceY;
	}	
}
