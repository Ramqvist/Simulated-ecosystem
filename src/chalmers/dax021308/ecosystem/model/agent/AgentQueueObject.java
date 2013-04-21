package chalmers.dax021308.ecosystem.model.agent;

public class AgentQueueObject implements Comparable<AgentQueueObject> {

	private IAgent agent;
	private double distance;
	
	public AgentQueueObject(IAgent agent, double distance){
		this.agent = agent;
		this.distance = distance;
	}
	
	@Override
	public int compareTo(AgentQueueObject o) {	
		if(this.distance < o.distance) {
			return -1;
		} else {
			return 1;
		}
	}

	public IAgent getAgent(){
		return agent;
	}
	
	public double getDistance(){
		return distance;
	}
	
}
