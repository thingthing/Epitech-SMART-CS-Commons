package eip.smart.model.agent;

// status interface list the methods used by all the status
public abstract class State {
	public int		priority;
	private boolean	lock	= false;

	// canMove is the method allowing to know if the agent can receive an order
	public boolean canMove() {
		return (true);
	}

	//
	public boolean checkState(Agent agent) {
		return (false);
	}

	// doAction is the action that the agent has to do each time his status is checked
	public void doAction(Agent agent) {}

	public boolean isLocked() {
		return (this.lock);
	}
}
