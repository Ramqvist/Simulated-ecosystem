kernel void mutualInteractionForce(global const float* xPosArray, global const float* yPosArray, global float* xResult, global float* yResult,
			int const size, float const interactRange, float const myPosX, float const myPosY) {
   const int gid = get_global_id(0); 
   
	if(gid < size) {
			float newForceX = 0;
			float newForceY = 0;
			float mutualInteractionForceX = 0;
			float mutualInteractionForceY = 0;
			float distance = 0;
//			double dX = 0;
//			double dY = 0;
			float xAgentPosition = xPosArray[gid];
			float yAgentPosition = yPosArray[gid];
			float v = 0;
			float norm = 0;
//			double distance = getDistance(myPosX, myPosY, xAgentPosition, yAgentPosition);
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
			xResult[gid] = mutualInteractionForceX + newForceX;
			yResult[gid] = mutualInteractionForceY + newForceY;
   }
}
