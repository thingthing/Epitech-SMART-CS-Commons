package Status;

import eip.smart.model.Agent;

public class StatusOk implements Status{

	@Override
	public void doAction(Agent agent) {
		
	}

	@Override
	public boolean canMove() {
		return true;
	}

	// default value
	@Override
	public boolean checkState(Agent agent) {
		return true;
	}

}
