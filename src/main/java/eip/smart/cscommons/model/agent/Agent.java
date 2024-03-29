package eip.smart.cscommons.model.agent;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

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
	@JsonView(JSONViews.HTTP.class)
	protected double					battery				= 0;

	/**
	 *
	 */
	@JsonView(JSONViews.HTTP.class)
	protected List<Double>				bearings			= new LinkedList<>();

	/**
	 * Boolean, allowing to define if the agent is connected
	 */
	@JsonView(JSONViews.HTTP.class)
	protected boolean					connected			= false;

	@JsonView(JSONViews.HTTP.class)
	protected Point3D					currentDestination	= null;

	/**
	 * destination area (Area), that the agent'll has to explore if the ordrers list is free
	 *
	 * @see Area
	 */
	@JsonView(JSONViews.HTTP.class)
	protected Area						destination			= null;

	/**
	 * agent's last contact's date (Date), allowing to determine it state
	 */
	@JsonView(JSONViews.HTTP.class)
	protected Date						lastContact			= new Date();

	/**
	 * Name (String), single id
	 */
	@JsonView(JSONViews.HTTP.class)
	protected String					name				= "";

	/**
	 * List of orders (LinkedList<Point>), the positions where the agent has to go
	 *
	 * @see Point
	 */
	@JsonView(JSONViews.HTTP.class)
	protected List<Point3D>				orders				= new LinkedList<>();

	/**
	 * List of the previous positions of the agent (LinkedList<Point>), the last one being the last known position
	 *
	 * @see Point
	 */
	@JsonView(JSONViews.HTTP.class)
	protected SortedMap<Date, Point3D>	positions			= new TreeMap<>(Collections.reverseOrder());

	/**
	 * State (AgentState), allowing to define the agent's state (ok, still, lost, etc)
	 *
	 * @see AgentState
	 */
	@JsonView(JSONViews.HTTP.class)
	protected AgentState				state				= AgentState.OK;

	/**
	 * Type (AgentType), allowing to define the environment where the agent is able to progress
	 *
	 * @see AgentType
	 */
	@JsonView(JSONViews.HTTP.class)
	protected AgentType					type				= AgentType.TERRESTRIAL;

	@SuppressWarnings("unused")
	private Agent() {}

	public Agent(Agent agent) {
		this.bearings = agent.bearings;
		this.connected = agent.connected;
		this.destination = agent.destination;
		this.lastContact = agent.lastContact;
		this.name = agent.name;
		this.orders = agent.orders;
		this.setPositions(agent.positions);
		this.state = agent.state;
		this.type = agent.type;
		this.battery = agent.battery;
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

	public Point3D getCurrentDestination() {
		return this.currentDestination;
	}

	@JsonIgnore
	public Point3D getCurrentOrder() {
		return (this.orders.size() > 0 ? this.orders.get(0) : null);
	}

	@JsonIgnore
	public Point3D getCurrentPosition() {
		if (this.positions.isEmpty())
			return (null);
		return (this.positions.get(this.positions.firstKey()));
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
		return (new ArrayList<>(this.positions.values()));
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

	public void popCurrentOrder() {
		this.orders.remove(0);
	}

	private void setPositions(SortedMap<Date, Point3D> positions) {
		this.positions.putAll(positions);
	}

}
