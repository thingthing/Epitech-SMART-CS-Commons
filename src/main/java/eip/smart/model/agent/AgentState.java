package eip.smart.model.agent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

// priority
public enum AgentState {
	// this status has to be activated by an agent's message
	LOW_BATTERY(new State(true) {

		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public void doAction(Agent agent) {
			agent.recall();
			// switch agent status to RECALL_BATTERY
		}
	}),

	// this status has to be activated by an agent's message
	DERANGED(new State(true) {

		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public void doAction(Agent agent) {}
	}),

	// this status has to be activated by an agent's message
	BLOCKED(new State(true) {

		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public void doAction(Agent agent) {}
	}),

	LOST_SIGNAL(new State() {

		@Override
		public boolean canMove() {
			return false;
		}
	}),

	// this status has to be activated by an agent's message
	RECALL_ERROR(new State(true) {

		// the agent is coming to the base because of an error, he can't receive orders
		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public void doAction(Agent agent) {}
	}),

	// this status has to be activated by an agent's message
	UNKNOWN_ERROR(new State(true) {

		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public void doAction(Agent agent) {}
	}),

	// decided by server
	// not checked
	NO_BATTERY(new State(true) {

		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public void doAction(Agent agent) {}

		@Override
		public int getPriority() {
			return (1);
		}
	}),

	RECALL_BATTERY(new State() {
		// the agent is coming to the base because it has not enough battery, he can't receive orders
		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public void doAction(Agent agent) {
			agent.recall();
		}

		@Override
		public int getPriority() {
			return (1);
		}

		// this status is activated by the LOW_BATTERY status
	}),

	RECALL(new State() {
		@Override
		public boolean canMove() {
			return true;
		}

		@Override
		public void doAction(Agent agent) {
			agent.recall();
		}

		@Override
		public int getPriority() {
			return (1);
		}

		// this status is activated by a server's decision
	}),

	// checked
	STILL_ERROR(new State() {
		@Override
		public boolean canMove() {
			return true;
		}

		@Override
		public boolean checkState(Agent agent) {
			boolean still = true;
			int check_size = 50;
			int i = 0;

			while (i < ((agent.getPositions().size() > check_size) ? check_size : agent.getPositions().size())) {
				if (agent.getCurrentPosition() != agent.getPositions().get(i) && still)
					still = false;
				i++;
			}
			if (still)
				return true;
			return false;
		}

		@Override
		public void doAction(Agent agent) {
			// try to move... somewhere
		}

		@Override
		public int getPriority() {
			return (5);
		}
	}),

	STILL(new State() {
		@Override
		public boolean canMove() {
			return true;
		}

		@Override
		public boolean checkState(Agent agent) {
			boolean still = true;
			int check_size = 10;
			int i = 0;

			while (i < ((agent.getPositions().size() > check_size) ? check_size : agent.getPositions().size())) {
				if (agent.getCurrentPosition() != agent.getPositions().get(i) && still)
					still = false;
				i++;
			}
			if (still)
				return true;
			return false;
		}

		@Override
		public void doAction(Agent agent) {
			// try to move... somewhere
		}

		@Override
		public int getPriority() {
			return (4);
		}
	}),

	NO_SIGNAL(new State() {
		private int	cpt	= 4;

		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public boolean checkState(Agent agent) {
			if (Date.from(Instant.now()).getTime() - agent.getLastContact().getTime() > 5 * 60 * 1000)
				return true;
			return false;
		}

		@Override
		public void doAction(Agent agent) {
			if (agent.isConnected()) {
				// set agent status to OK
			}
			if (++this.cpt == 20) {
				// set agent status to LOST_SIGNAL
			}
		}
	}),

	OK(new State() {
		@Override
		public boolean canMove() {
			return true;
		}

		// default value
		@Override
		public boolean checkState(Agent agent) {
			return true;
		}

		@Override
		// do all the normal agent actions
		public void doAction(Agent agent) {

		}

		@Override
		public int getPriority() {
			return (-1);
		}

		@Override
		public boolean isLocked() {
			return (false);
		}
	});

	public static AgentState updateAgentState(Agent agent) {
		AgentState[] StatesValues = AgentState.values();
		ArrayList<AgentState> list = new ArrayList<>(Arrays.asList(StatesValues));
		Collections.sort(list, new Comparator<Object>() {
			@Override
			public int compare(Object obj1, Object obj2) {
				AgentState as1 = (AgentState) obj1;
				AgentState as2 = (AgentState) obj2;
				int lib1 = as1.status.getPriority();
				int lib2 = as2.status.getPriority();
				if (lib1 > lib2)
					return (-1);
				else if (lib1 == lib2)
					return (0);
				return (1);
			}
		});

		for (AgentState state : list)
			if (state.status.checkState(agent))
				return (state);
		return (null);
	}

	private State	status;

	AgentState(State status) {
		this.status = status;
	}

	/**
	 * @return
	 * @see eip.smart.model.agent.State#isLocked()
	 */
	public boolean isLocked() {
		return this.status.isLocked();
	}
}