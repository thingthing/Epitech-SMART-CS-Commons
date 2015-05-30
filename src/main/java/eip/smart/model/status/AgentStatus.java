package eip.smart.model.status;

import eip.smart.model.Agent;
// status interface list the methods used by all the status
public abstract class  AgentStatus {
	// 
	public boolean checkState(Agent agent) { return (false); }
	
	// doAction is the action that the agent has to do each time his status is checked
	public void doAction(Agent agent) {}
	
	// canMove is the method allowing to know if the agent can receive an order
	public boolean canMove() { return (true); }
	}
