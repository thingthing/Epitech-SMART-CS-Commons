package eip.smart.model.proxy;

import eip.smart.model.Agent;
import eip.smart.model.Agent.AgentType;

public class SimpleAgentProxy extends Proxy<Agent> {

	private AgentType	type;
	private String		name;
	private boolean		connected;

	public SimpleAgentProxy() {}

	public SimpleAgentProxy(Agent object) {
		super(object);
		this.setType(object.getType());
		this.setName(object.getName());
		this.setConnected(object.isConnected());
	}

	public String getName() {
		return (this.name);
	}

	public AgentType getType() {
		return (this.type);
	}

	public boolean isConnected() {
		return (this.connected);
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(AgentType type) {
		this.type = type;
	}
}