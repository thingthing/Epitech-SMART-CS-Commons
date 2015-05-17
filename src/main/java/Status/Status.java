package Status;

import eip.smart.model.Agent;
// status interface list the methods used by all the status
public interface  Status {
	// 
	public boolean checkState(Agent agent);
	// doAction is the action that the agent has to do each time his status is checked
	public void doAction(Agent agent);
	// canMove is the method allowing to know if the agent can receive an order
	public boolean canMove();
	}
