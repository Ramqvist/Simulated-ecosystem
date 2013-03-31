package chalmers.dax021308.ecosystem.model.agent;

public class kernelErrorHelper {
	private float[] xResult;
	private float[] yResult;

	public void help() {
		int gid = 0;
		int numAgentToCalc = 0;
		if (gid < numAgentToCalc) {
			float[] agentsPosX = null;
			float myPosX = agentsPosX[gid];
			float[] agentsPosY = null;
			float myPosY = agentsPosY[gid];
			float mutualInteractionForceX = 0;
			float mutualInteractionForceY = 0;
			int interactRange = 0;
			float[] yPosArray = null;
			float[] xPosArray = null;
			int totalAgents = 0;
			for (int i = 0; i < totalAgents; i++) {
				float xAgentPosition = xPosArray[i];
				float yAgentPosition = yPosArray[i];
				float newForceX = 0;
				float newForceY = 0;
				float distance = 0;
				float v = 0;
				float norm = 0;
				float dX = myPosX - xAgentPosition;
				float dY = myPosY - yAgentPosition;
				distance = (float) Math.sqrt(Math.pow(dX, 2) + Math.pow(dY, 2));
				float Q = 0; // Q is a function of the distance.
				if (distance <= interactRange) {
					Q = -20.0F * (interactRange - distance);
				} else {
					Q = 1.0F;
				}	
				newForceX = xAgentPosition - myPosX;
				newForceY = yAgentPosition - myPosY;
				norm = (float) Math.sqrt((newForceX*newForceX)+(newForceY*newForceY));
				v = Q / (norm * distance);
				newForceX = newForceX * v;
				newForceY = newForceY * v;
				mutualInteractionForceX = mutualInteractionForceX + newForceX;
				mutualInteractionForceY = mutualInteractionForceY + newForceY;
			}
			xResult[gid] = mutualInteractionForceX;
			yResult[gid] = mutualInteractionForceY;
		}
	}
}
