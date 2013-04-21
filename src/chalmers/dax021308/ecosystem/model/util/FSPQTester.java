package chalmers.dax021308.ecosystem.model.util;

import java.util.ArrayList;

import chalmers.dax021308.ecosystem.model.agent.AgentQueueObject;

public class FSPQTester {
	
	public static void main(String[] args) {
		
		FixedSizeAgentQueueObjectPriorityQueue asdf = new FixedSizeAgentQueueObjectPriorityQueue(20);
		
		for(int i=0; i<100; i++) {
			asdf.insertWithOverflow(new AgentQueueObject(null,i));
		}
	
		System.out.println(asdf.top());
		
	}

}
