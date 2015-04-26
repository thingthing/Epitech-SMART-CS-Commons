package eip.smart.model.proxy;

import eip.smart.model.Agent;
import eip.smart.model.Agent.AgentType;


/**
 * <b>SimpleAgentProxy est la classe utilis�e pour simplifier l'envoi d'informations relatives � un agent.</b>
 * Elle consiste en une classe contenant uniquement les �l�ments qu'il est int�ressant d'envoyer sur un agent (son nom, son type et un boolean d�terminant si oui ou non il est connect�)
 * @author Pierre Demessence
*/
public class SimpleAgentProxy extends Proxy<Agent> {

	private AgentType	type;
	private String		name;
	private boolean		connected;

	public SimpleAgentProxy() {}

	/**
	 * Constructeur prenant en param�tre un Agent, et copiant ses attribus
	 * @param modelisation
	 */
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