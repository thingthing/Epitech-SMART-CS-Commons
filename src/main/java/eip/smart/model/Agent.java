package eip.smart.model;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.LinkedList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eip.smart.model.geometry.Point;
import eip.smart.model.proxy.SimpleAgentProxy;

/**
  * <b>Agent is the class allowing the management of the Agents.</b>
  * @author Pierre Demessence
  * @version 3.0
 */
public class Agent implements Serializable {

	public static enum AgentState {
		LOST,
		LOW_BATTERY,
		NO_BATTERY,
		NO_RETURN,
		OK,
		STILL,
		UNKNOWN_ERROR
	}

	public static enum AgentType {
		AERIAL,
		SUBMARINE,
		TERRESTRIAL;
	}

	public interface sendMessageCallback {
		public void callback(Object message);
	}

	/**
	 * Name (String), single id
	 */
	private String				name			= "";
	
	/**
	 * Booleen, allowing to define if the agent is connected
	 */
	private boolean				connected		= false;
	
	/**
	 * Type (AgentType), allowing to define the environment where the agent is able to progress
	 * @see AgentType
	 */
	private AgentType			type			= AgentType.TERRESTRIAL;
	
	/**
	 * State (AgentState), allowing to define the agent's state (ok, still, lost, etc)
	 * @see AgentState
	 */
	private AgentState			state			= AgentState.OK;

	/**
	 * List of the previous positions of the agent (LinkedList<Point>), the last one being the last known position
	 * @see Point
	 */
	private LinkedList<Point>	positions		= new LinkedList<>();
	
	/**
	 * Liste of orders (LinkedList<Point>), the positions where the agent has to go
	 * @see Point
	 */
	private LinkedList<Point>	orders			= new LinkedList<>();
	
	/**
	 * destination area (Area), that the agent'll has to explore if the ordrers list is free
	 * @see Area
	 */
	private Area				destination		= null;

	/**
	 *  Objet (AgentMessageManager), managing the messages'reception
	 *  @see AgentMessageManager
	 */
	private AgentMessageManager	messageManager	= new AgentMessageManager();

	/**
	 * Objet (sendMessageCallback), managing the messages'sending
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
	private LinkedList<Double>	bearings		= new LinkedList<Double>();

	/**
	 * default constructor, setting the agent at the coordinates (0, 0, 0) and create a handler allowing to update it position
	 */
	public Agent() {
		this.setCurrentPosition(new Point(0, 0, 0));
		this.messageManager.addHandler("position", new AgentMessageHandler<Point>(Point.class) {
			@Override
			public void handleMessage(Point data, Agent agent) {
				agent.setCurrentPosition(data);
			}
		});
	}

	/**
	 * Constructor allowing to give a name to the agent at it creation
	 * 
	 * @param name name of the new agent
	 */
	public Agent(String name) {
		this();
		this.name = name;
	}

	/**
	 * Copy constructor
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
	 * @param order Point, new order send to the agent
	 */
	public void pushOrder(Point order) {
		this.orders.push(order);
		this.sendMessage("order:%o", order);
	}

	/**
	 * Donne � l'agent l'ordre de retourner � son point de d�part
	 */
	public void recall() {
		this.pushOrder(new Point(0, 0, 0));
	}

	/**
	 * V�rifie si un message a �t� re�u par l'agent
	 * @param msg String, chaine de caract�res repr�sentant le message re�u
	 */
	public void receiveMessage(String msg) {
		try {
			this.messageManager.handleMessage(msg, this);
		} catch (IOException e) {
			this.sendMessage(e.getMessage());
		}

	}

	/**
	 * Envoi un message au tableau de bord
	 * 
	 * @param message String
	 * @param objects Un ou plusieurs objets qui seront envoy�s
	 */
	public void sendMessage(String message, Object... objects) {
		ObjectMapper mapper = new ObjectMapper();
		for (Object object : objects)
			try {
				message = message.replaceFirst("%o", mapper.writeValueAsString(object));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
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
		if (Date.from(Instant.now()).getTime() - this.lastContact.getTime() > 5 * 60 * 1000)
			this.state = AgentState.LOST;
	}
}
