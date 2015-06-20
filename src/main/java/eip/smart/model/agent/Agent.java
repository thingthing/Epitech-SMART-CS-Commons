package eip.smart.model.agent;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.JsonNode;

import eip.smart.model.Area;
import eip.smart.model.MessagePacket;
import eip.smart.model.Modeling;
import eip.smart.model.geometry.Point;
import eip.smart.model.proxy.SimpleAgentProxy;
import eip.smart.util.Pair;

/**
 * <b>Agent is the class allowing the management of the Agents.</b>
 *
 * @author Pierre Demessence
 * @version 3.0
 */
@SuppressWarnings("unchecked")
public class Agent implements Serializable {

	public static enum AgentType {
		AERIAL,
		SUBMARINE,
		TERRESTRIAL;
	}

	public interface sendMessageCallback {
		public void callback(Object message);
	}

	private final static Logger	LOGGER			= Logger.getLogger(Modeling.class.getName());

	/**
	 * Name (String), single id
	 */
	private String				name			= "";

	/**
	 * Boolean, allowing to define if the agent is connected
	 */
	private boolean				connected		= false;

	/**
	 * Type (AgentType), allowing to define the environment where the agent is able to progress
	 *
	 * @see AgentType
	 */
	private AgentType			type			= AgentType.TERRESTRIAL;

	/**
	 * State (AgentState), allowing to define the agent's state (ok, still, lost, etc)
	 *
	 * @see AgentState
	 */
	private AgentState			state			= AgentState.OK;

	/**
	 * List of the previous positions of the agent (LinkedList<Point>), the last one being the last known position
	 *
	 * @see Point
	 */
	private LinkedList<Point>	positions		= new LinkedList<>();

	/**
	 * List of orders (LinkedList<Point>), the positions where the agent has to go
	 *
	 * @see Point
	 */
	private LinkedList<Point>	orders			= new LinkedList<>();

	/**
	 * destination area (Area), that the agent'll has to explore if the ordrers list is free
	 *
	 * @see Area
	 */
	private Area				destination		= null;

	/**
	 * Object (AgentMessageManager), managing the messages'reception
	 *
	 * @see AgentMessageManager
	 */
	private AgentMessageManager	messageManager	= new AgentMessageManager();

	/**
	 * Object (sendMessageCallback), managing the messages'sending
	 *
	 * @see sendMessageCallback
	 */
	private sendMessageCallback	messageCallback	= null;

	/**
	 * agent's last contact's date (Date), allowing to determine it state
	 */
	private Date				lastContact		= Date.from(Instant.now());

	/**
	 *
	 */
	private LinkedList<Double>	bearings		= new LinkedList<>();

	/**
	 * default constructor and create handlers
	 */
	public Agent() {
		for (AgentMessageReceptor receptor : AgentMessageReceptor.values())
			this.messageManager.addHandler(receptor.getKey(), receptor.getHandler());
	}

	/**
	 * Copy constructor
	 *
	 * @param agent
	 */
	public Agent(Agent agent) {
		this(agent.getName());
		this.setCurrentPosition(agent.getCurrentPosition());
		this.setCurrentBearing(agent.getCurrentBearing());
		this.setType(agent.getType());
		this.setConnected(agent.isConnected());
		this.setState(agent.getState());
		this.setDestination(agent.getDestination());
		this.setLastContact(agent.getLastContact());
	}

	/**
	 * Constructor allowing to give a name to the agent at it creation
	 *
	 * @param name
	 *            name of the new agent
	 */
	public Agent(String name) {
		this();
		this.name = name;
	}

	public Double getCurrentBearing() {
		return (this.bearings.peek());
	}

	@JsonIgnore
	public Point getCurrentOrder() {
		return (this.orders.peek());
	}

	@JsonIgnore
	public Point getCurrentPosition() {
		return (this.positions.peek());
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

	public LinkedList<Point> getOrders() {
		return (this.orders);
	}

	public LinkedList<Point> getPositions() {
		return (this.positions);
	}

	@JsonIgnore
	public SimpleAgentProxy getProxy() {
		return (new SimpleAgentProxy(this));
	}

	public AgentState getState() {
		return (this.state);
	}

	public AgentType getType() {
		return (this.type);
	}

	/**
	 * Return a boolean taking "true" if the agent is connected and "false" if he's not.
	 *
	 * @return A boolean allowing to determine if the agent is connected
	 */
	public boolean isConnected() {
		return (this.connected);
	}

	/**
	 * Add a Point at the agent's list of orders
	 *
	 * @see Point
	 * @param order
	 *            Point, new order send to the agent
	 */
	public void pushOrder(Point order) {
		this.orders.push(order);
		this.sendMessage(new Pair<>("order", order));
	}

	/**
	 * Donne � l'agent l'ordre de retourner � son point de d�part
	 */
	public void recall() {
		this.pushOrder(new Point(0, 0, 0));
	}

	/**
	 * V�rifie si un message a �t� re�u par l'agent
	 *
	 * @param msg
	 *            String, chaine de caract�res repr�sentant le message re�u
	 */
	public void receiveMessage(JsonNode data) {
		try {
			this.messageManager.handleMessage(data, this);
		} catch (IOException e) {
			this.sendStatus(1, e.getMessage());
		}

	}

	/**
	 * Envoi un message au tableau de bord
	 *
	 * @param message
	 *            String
	 * @param objects
	 *            Un ou plusieurs objets qui seront envoy�s
	 */
	public void sendMessage(Pair<String, Object>... objects) {
		MessagePacket message = new MessagePacket();
		for (Pair<String, Object> p : objects)
			message.addObject(p.getKey(), p.getValue());
		if (this.messageCallback != null)
			this.messageCallback.callback(message);
	}

	public void sendStatus(int statusCode, String statusMessage) {
		MessagePacket message = new MessagePacket();
		message.setStatus(statusCode, statusMessage);
		if (this.messageCallback != null)
			this.messageCallback.callback(message);
	}

	public void setConnected(boolean connected) {
		this.connected = connected;
	}

	public void setCurrentBearing(Double bearing) {
		this.bearings.push(bearing);
	}

	public void setCurrentPosition(Point position) {
		this.positions.push(position);
	}

	public void setDestination(Area destination) {
		this.destination = destination;
	}

	public void setLastContact(Date lastContact) {
		this.lastContact = lastContact;
	}

	public void setSendMessageCallback(sendMessageCallback messageCallback) {
		this.messageCallback = messageCallback;
	}

	public void setState(AgentState state) {
		this.state = state;
	}

	public void setType(AgentType type) {
		this.type = type;
	}

	/**
	 * Met � jours l'�tat de l'agent, en se basant sur les attribus "positions" et "lastContact"
	 */
	public void updateState() {
		/* States are bugged.
		if (!this.state.isLocked())
			this.state = AgentState.getAgentState(this);
		*/
	}

}
