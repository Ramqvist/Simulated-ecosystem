kernel void mutualInteractionForce(
			global const float* xPosArray,
			global const float* yPosArray, 
			int const totalAgents,
			global float* xResult,
			global float* yResult,
			float const interactRange, 
			global const float* agentsPosX,
			global const float* agentsPosY,
			int const numAgentToCalc) {
	
	const int gid = get_global_id(0); 
   
	if (gid < numAgentToCalc) {
		float myPosX = agentsPosX[gid];
		float myPosY = agentsPosY[gid];
		float mutualInteractionForceX = 0;
		float mutualInteractionForceY = 0;
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
			distance = sqrt(pow(dX, 2) + pow(dY, 2));
			float Q = 0; // Q is a function of the distance.
			if (distance <= interactRange) {
				Q = -20.0 * (interactRange - distance);
			} else {
				Q = 1.0;
			}	
			newForceX = xAgentPosition - myPosX;
			newForceY = yAgentPosition - myPosY;
			norm = sqrt((newForceX*newForceX)+(newForceY*newForceY));
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
