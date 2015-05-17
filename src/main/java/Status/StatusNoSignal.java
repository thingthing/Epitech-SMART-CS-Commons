package Status;

import java.time.Instant;
import java.util.Date;

import eip.smart.model.Agent;

public class StatusNoSignal implements Status{
	private int cpt;

	public StatusNoSignal() {
		cpt = 0;
	}
	
	@Override
	public void doAction(Agent agent) {
		if (agent.isConnected())
		{
			//set agent status to OK
		}
		if (++cpt == 20)
		{
			//set agent status to NO_SIGNAL
		}
	
	}
	
	@Override
	public boolean canMove() {
		return false;
	}

	@Override
	public boolean checkState(Agent agent) {
		if (Date.from(Instant.now()).getTime() - agent.getLastContact().getTime() > 5 * 60 * 1000)
			return true;
		else
			return false;
	}

}