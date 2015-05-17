package Status;

import eip.smart.model.Agent;

public class StatusNoReturn implements Status{

	@Override
	public void doAction(Agent agent) {

	}

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
