package eip.smart.model.agent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import eip.smart.model.geometry.v2.Point3D;

/**
 * <b>AgentState is the enum allowing the management of the Agents'states.</b>
 *
 * @author Pierre Demessence
 * @version 1.0
 */
public enum AgentState {
	/**
	 * this state has to be activated by an agent's message
	 */
	LOW_BATTERY(new State(true) {

		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public void doAction(Agent agent) {
			agent.recall();
			agent.setState(AgentState.RECALL_BATTERY);
			// switch agent status to RECALL_BATTERY
		}
	}),

	/**
	 * this state has to be activated by an agent's message
	 * this state is locked
	 */
	DERANGED(new State(true) {

		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public void doAction(Agent agent) {}
	}),

	/**
	 * this state has to be activated by an agent's message
	 * this state is locked
	 */
	BLOCKED(new State(true) {

		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public void doAction(Agent agent) {}
	}),

	/**
	 * this state has to be activated by the NO_SIGNAL state
	 */
	LOST_SIGNAL(new State() {

		@Override
		public boolean canMove() {
			return false;
		}
	}),

	/**
	 * this state has to be activated by an agent's message
	 * this state is locked
	 */
	RECALL_ERROR(new State(true) {

		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public void doAction(Agent agent) {}
	}),

	/**
	 * this state has to be activated by an agent's message
	 * this state is locked
	 */
	UNKNOWN_ERROR(new State(true) {

		@Override
		public boolean canMove() {
			return false;
		}

		@Override
		public void doAction(Agent agent) {}
	}),

	/**
	 * this state is decided by server (not checked)
	 * this state is locked
	 */
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

	/**
	 * this state is activated by the LOW_BATTERY state
	 */
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

	}),

	/**
	 * this state is activated by a server's decision
	 */
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
	}),

	/**
	 * this state is checked by the server
	 */
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
			Point3D currentPos = agent.getPositions().getLast();
			Point3D order = new Point3D(currentPos.getX() + 5, currentPos.getY() + 5, 0);
			agent.pushOrder(order);
		}

		@Override
		public int getPriority() {
			return (5);
		}
	}),

	/**
	 * this state is checked by the server
	 */
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
			Point3D currentPos = agent.getPositions().getLast();
			Point3D order = new Point3D(currentPos.getX() + 5, currentPos.getY() + 5, 0);
			agent.pushOrder(order);
		}

		@Override
		public int getPriority() {
			return (4);
		}
	}),

	/**
	 * this state is checked by the server
	 */
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
			if (agent.isConnected())
				agent.setState(AgentState.OK);
			if (++this.cpt == 20)
				agent.setState(AgentState.LOST_SIGNAL);
		}
	}),

	/**
	 * this state is the default state
	 */
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

	public static AgentState getAgentState(Agent agent) {
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

		for (AgentState state : list) {
			System.out.println("Checking state " + state.name());
			if (state.status.checkState(agent))
				return (state);
		}
		return (AgentState.OK);
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