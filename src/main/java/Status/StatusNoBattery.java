package Status;

import eip.smart.model.Agent;

public class StatusNoBattery implements Status{

	@Override
	public void doAction(Agent agent) {
	}

	@Override
	public boolean canMove() {
		return false;
	}

	// this status has to be activated by an agent's message
	@Override
	public boolean checkState(Agent agent) {
		return false;
	}

}
