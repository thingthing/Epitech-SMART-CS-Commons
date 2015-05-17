package Status;

import eip.smart.model.Agent;

public class StatusRecallBattery implements Status{

	@Override
	public void doAction(Agent agent) {
		agent.recall();
	}
	
	//the agent is coming to the base because it has not enough battery, he can't receive orders
	@Override
	public boolean canMove() {
		return false;
	}

	// this status is activated by the LOW_BATTERY status
	@Override
	public boolean checkState(Agent agent) {
		return false;
	}

}