package Status;

import eip.smart.model.Agent;

public class StatusRecall implements Status{

	@Override
	public void doAction(Agent agent) {
		agent.recall();
	}
	
	@Override
	public boolean canMove() {
		return true;
	}

	// this status is activated by a server's decision
	@Override
	public boolean checkState(Agent agent) {
		return false;
	}

}