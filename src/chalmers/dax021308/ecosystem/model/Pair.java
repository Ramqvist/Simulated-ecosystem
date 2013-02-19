package chalmers.dax021308.ecosystem.model;

public class Pair<startNumber, endNumber> {
	private startNumber start;
	private endNumber end;
	
	public Pair(startNumber start, endNumber end){
		this.start = start;
		this.end = end;
	}
	
	public startNumber getStart(){
		return start;
	}
	
	public endNumber getEnd(){
		return end;
	}
	
	public void setStart(startNumber start){
		this.start = start;
	}
	
	public void setEnd(endNumber end){
		this.end = end;
	}
}
