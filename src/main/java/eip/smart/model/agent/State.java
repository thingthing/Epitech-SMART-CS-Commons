package eip.smart.model.agent;

// status interface list the methods used by all the status
@SuppressWarnings({ "static-method", "unused" })
public abstract class State {

	private boolean	lock;

	public State() {
		this.lock = false;
	}

	public State(boolean lock) {
		this.lock = lock;
	}

	// canMove is the method allowing to know if the agent can receive an order
	public boolean canMove() {
		return (true);
	}

	public boolean checkState(Agent agent) {
		return (false);
	}

	// doAction is the action that the agent has to do each time his status is checked
	public void doAction(Agent agent) {}

	public int getPriority() {
		return (0);
	}

	public boolean isLocked() {
		return (this.lock);
	}
}
