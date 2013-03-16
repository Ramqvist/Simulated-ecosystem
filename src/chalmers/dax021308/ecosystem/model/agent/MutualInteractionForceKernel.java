package chalmers.dax021308.ecosystem.model.agent;

import chalmers.dax021308.ecosystem.model.util.Position;

import com.amd.aparapi.Kernel;

public class MutualInteractionForceKernel extends Kernel {
	//Only final fields in kernels.
	public final float[] xPosArray;
	public final float[] yPosArray;
	public final float[] xResult;
	public final float[] yResult;

	public final float INTERACTION_RANGE;
	public final float myPosX;
	public final float myPosY;
	
	public MutualInteractionForceKernel(int size, float INTERACTION_RANGE, float myPosX, float myPosY) {
		xPosArray = new float[size];
		yPosArray = new float[size];
		this.INTERACTION_RANGE = INTERACTION_RANGE;
		this.myPosX = myPosX;
		this.myPosY = myPosY;
		xResult = new float[size];
		yResult = new float[size];
	}

	@Override
	public void run() {
			float mutualInteractionForceX = 0;
			float mutualInteractionForceY = 0;
			float xAgentPosition = xPosArray[getGlobalId()];
			float yAgentPosition = yPosArray[getGlobalId()];
//			float distance = getDistance(myPosX, myPosY, xAgentPosition, yAgentPosition);
			float dX = myPosX - xAgentPosition;
			float dY = myPosY - yAgentPosition;
			//Math library allowed in Aparapi!
			float distance = (float) Math.sqrt(dX * dX + dY * dY);
			float Q = 0; // Q is a function of the distance.
			if (distance <= INTERACTION_RANGE) {
				Q = -20 * (INTERACTION_RANGE - distance);
			} else {
				Q = 1;
			}	
			float newForceX = 0;
			float newForceY = 0;
			newForceX = xAgentPosition - myPosX;
			newForceY = yAgentPosition - myPosY;
			float norm = (float) Math.sqrt(Math.pow(newForceX,2)+Math.pow(newForceY,2));
			float v = Q / (norm * distance);
			newForceX = newForceX * v;
			newForceY = newForceY * v;
			xResult[getGlobalId()] = mutualInteractionForceX + newForceX;
			yResult[getGlobalId()] = mutualInteractionForceY + newForceY;
	}	
}
