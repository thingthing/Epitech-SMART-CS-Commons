package eip.smart.model.proxy;

import eip.smart.model.Agent;
import eip.smart.model.Agent.AgentType;

public class SimpleAgentProxy extends Proxy<Agent> {

	private AgentType	type;
	private int			ID;

	public SimpleAgentProxy() {}

	public SimpleAgentProxy(Agent object) {
		super(object);
		this.setType(object.getType());
		this.setID(object.getID());
	}

	public int getID() {
		return this.ID;
	}

	public AgentType getType() {
		return this.type;
	}

	public void setID(int iD) {
		this.ID = iD;
	}

	public void setType(AgentType type) {
		this.type = type;
	}
}