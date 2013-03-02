package chalmers.dax021308.ecosystem.model.util;

import java.util.ArrayList;
import java.util.List;

import chalmers.dax021308.ecosystem.model.agent.IAgent;

/**
 * AgentContainer, contains an ordinary IAgent-list and a remove list.
 * <p>
 * This removelist is a list of agents that is to be removed.
 * @author Erik
 *
 */
public class AgentContainer {
	private List<IAgent> list;
	private List<IAgent> removeList;
	
	public AgentContainer(List<IAgent> list) {
		this.list = list;
		removeList = new ArrayList<IAgent>(12);
	}
	
	public List<IAgent> getList() {
		return list;
	}
	
	public void setList(List<IAgent> list) {
		this.list = list;
	}
	
	public void addToRemoveList(IAgent a) {
		removeList.add(a);
	}
	
	/**
	 * Cleares out the agents in the removeList.
	 * <p>
	 * Warning! Use only when no other thread is iterating of the agentlist.
	 */
	public void removeAgents() {
		IAgent a;
		for(int i = 0 ; i < removeList.size() ; i++)  {
			a = removeList.get(i);
			list.remove(a);
		}
		removeList.clear();
	}
}
