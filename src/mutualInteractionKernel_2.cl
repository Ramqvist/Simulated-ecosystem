kernel void mutualInteractionForce(global const float* xPosArray, global const float* yPosArray, global float* xResult, global float* yResult,
			int const size, float const interactRange, float const myPosX, float const myPosY) {
   const int gid = get_global_id(0); 
   
	if(gid < size) {
			float xAgentPosition = xPosArray[gid];
			float yAgentPosition = yPosArray[gid];
			float xResultF = 0;
			float yResultF = 0;
			for(int i = 0; i < 10000;i++) {
				float newForceX = 0;
				float newForceY = 0;
				float mutualInteractionForceX = 0;
				float mutualInteractionForceY = 0;
				float distance = 0;
	//			double dX = 0;
	//			double dY = 0;
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
				xResultF = mutualInteractionForceX + newForceX;
				yResultF = mutualInteractionForceY + newForceY;
			}
			xResult[gid] = xResultF;
			yResult[gid] = yResultF;
   }
}
