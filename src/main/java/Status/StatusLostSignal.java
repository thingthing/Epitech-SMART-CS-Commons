package Status;

import eip.smart.model.Agent;

public class StatusLostSignal implements Status{

	@Override
	public void doAction(Agent agent) {

	}
	
	@Override
	public boolean canMove() {
		return false;
	}

	// this status is activated if the agent has NO_SIGNAL status during a moment
	@Override
	public boolean checkState(Agent agent) {
		// TODO Auto-generated method stub
		return false;
	}

}