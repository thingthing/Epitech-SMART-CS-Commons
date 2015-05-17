package Status;

import eip.smart.model.Agent;

public class StatusRecallError implements Status{

	@Override
	public void doAction(Agent agent) {

	}
	
	//the agent is coming to the base because of an error, he can't receive orders
	@Override
	public boolean canMove() {
		return false;
	}

	@Override
	public boolean checkState(Agent agent) {
		// TODO Auto-generated method stub
		return false;
	}

}