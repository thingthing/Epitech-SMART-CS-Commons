package eip.smart.cscommons.model.agent;

import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;

import eip.smart.cscommons.model.JSONViews;
import eip.smart.cscommons.model.geometry.Point3D;
import eip.smart.cscommons.model.modeling.Area;

/**
 * <b>Agent is the class allowing the management of the Agents.</b>
 *
 * @author Pierre Demessence
 * @version 3.0
 */
public class Agent implements Serializable {

	/**
	 * Double, the battery percentage
	 */
	@JsonView(JSONViews.ALL.class)
	protected double		battery		= 0;

	/**
	 *
	 */
	@JsonView(JSONViews.ALL.class)
	protected List<Double>	bearings	= new LinkedList<>();

	/**
	 * Boolean, allowing to define if the agent is connected
	 */
	@JsonView(JSONViews.IMPORTANT.class)
	protected boolean		connected	= false;

	/**
	 * destination area (Area), that the agent'll has to explore if the ordrers list is free
	 *
	 * @see Area
	 */
	@JsonView(JSONViews.ALL.class)
	protected Area			destination	= null;

	/**
	 * agent's last contact's date (Date), allowing to determine it state
	 */
	@JsonView(JSONViews.ALL.class)
	protected Date			lastContact	= Date.from(Instant.now());

	/**
	 * Name (String), single id
	 */
	@JsonView(JSONViews.IMPORTANT.class)
	protected String		name		= "";

	/**
	 * List of orders (LinkedList<Point>), the positions where the agent has to go
	 *
	 * @see Point
	 */
	@JsonView(JSONViews.ALL.class)
	protected List<Point3D>	orders		= new LinkedList<>();

	/**
	 * List of the previous positions of the agent (LinkedList<Point>), the last one being the last known position
	 *
	 * @see Point
	 */
	@JsonView(JSONViews.ALL.class)
	protected List<Point3D>	positions	= new LinkedList<>();

	/**
	 * State (AgentState), allowing to define the agent's state (ok, still, lost, etc)
	 *
	 * @see AgentState
	 */
	@JsonView(JSONViews.ALL.class)
	protected AgentState	state		= AgentState.OK;

	/**
	 * Type (AgentType), allowing to define the environment where the agent is able to progress
	 *
	 * @see AgentType
	 */
	@JsonView(JSONViews.IMPORTANT.class)
	protected AgentType		type		= AgentType.TERRESTRIAL;

	@SuppressWarnings("unused")
	private Agent() {}

	public Agent(Agent agent) {
		this.bearings = agent.bearings;
		this.connected = agent.connected;
		this.destination = agent.destination;
		this.lastContact = agent.lastContact;
		this.name = agent.name;
		this.orders = agent.orders;
		this.positions = agent.positions;
		this.state = agent.state;
		this.type = agent.type;
	}

	/**
	 * Constructor allowing to give a name to the agent at it creation
	 *
	 * @param name
	 *            name of the new agent
	 */
	public Agent(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Agent))
			return false;
		Agent other = (Agent) obj;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		return true;
	}

	public double getBattery() {
		return this.battery;
	}

	public List<Double> getBearings() {
		return this.bearings;
	}

	@JsonIgnore
	public Double getCurrentBearing() {
		return (this.bearings.get(0));
	}

	@JsonIgnore
	public Point3D getCurrentOrder() {
		return (this.orders.get(0));
	}

	@JsonIgnore
	public Point3D getCurrentPosition() {
		return (this.positions.get(0));
	}

	public Area getDestination() {
		return (this.destination);
	}

	public Date getLastContact() {
		return (this.lastContact);
	}

	public String getName() {
		return (this.name);
	}

	public List<Point3D> getOrders() {
		return (this.orders);
	}

	public List<Point3D> getPositions() {
		return (this.positions);
	}

	public AgentState getState() {
		return (this.state);
	}

	public AgentType getType() {
		return (this.type);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.name == null) ? 0 : this.name.hashCode());
		return result;
	}

	/**
	 * Return a boolean taking "true" if the agent is connected and "false" if he's not.
	 *
	 * @return A boolean allowing to determine if the agent is connected
	 */
	public boolean isConnected() {
		return (this.connected);
	}

}
