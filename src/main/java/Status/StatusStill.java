package Status;

import eip.smart.model.Agent;

public class StatusStill implements Status{

	@Override
	public void doAction(Agent agent) {
		//try to move... somewhere
	}

	@Override
	public boolean canMove() {
		return true;
	}

	@Override
	public boolean checkState(Agent agent) {
		boolean still = true;
		int check_size = 10;
		int i = 0;
		
			while (i < ((agent.getPositions().size() > check_size) ? check_size : agent.getPositions().size()))
			{
				if (agent.getCurrentPosition() != agent.getPositions().get(i) && still)
					still = false;
				i++;
			}
		if (still)
			return true;
		else
			return false;
	}
}

