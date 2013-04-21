package chalmers.dax021308.ecosystem.model.util;

import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.AgentQueueObject;
import chalmers.dax021308.ecosystem.model.agent.IAgent;

public class FixedSizeAgentQueueObjectPriorityQueue extends AgentQueueObjectPriorityQueue{
	
	public FixedSizeAgentQueueObjectPriorityQueue(int maxSize){
		initialize(maxSize);
	}

	@Override
	protected boolean lessThan(AgentQueueObject a, AgentQueueObject b) {
		if(a.getDistance() < b.getDistance()) {
			return false;
		} else {
			return true;
		}
	}
	
	public List<IAgent> getAgentsInHeapAsList(){
		ArrayList<IAgent> l = new ArrayList<IAgent>(size);
		for(int i=1; i <= size; i++) {
			IAgent a = heap[i].getAgent();
			l.add(a);
		}
		return l;
	}
	
	public List<AgentQueueObject> getHeapAsList(){
		ArrayList<AgentQueueObject> l = new ArrayList<AgentQueueObject>(heap.length);
		for(int i=1; i <= size; i++) {
			l.add(heap[i]);
		}
		return l;
	}

}
