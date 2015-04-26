package eip.smart.model.proxy;

import eip.smart.model.Agent;
import eip.smart.model.Agent.AgentType;


/**
 * <b>SimpleAgentProxy est la classe utilisée pour simplifier l'envoi d'informations relatives à un agent.</b>
 * Elle consiste en une classe contenant uniquement les éléments qu'il est intéressant d'envoyer sur un agent (son nom, son type et un boolean déterminant si oui ou non il est connecté)
 * @author Pierre Demessence
*/
public class SimpleAgentProxy extends Proxy<Agent> {

	private AgentType	type;
	private String		name;
	private boolean		connected;

	public SimpleAgentProxy() {}

	/**
	 * Constructeur prenant en paramètre un Agent, et copiant ses attribus
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