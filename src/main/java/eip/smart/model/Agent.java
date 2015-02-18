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
 * Created by Pierre Demessence on 09/10/2014.
 */
public class Agent implements Serializable {

	public static enum AgentState {
		OK,
		LOST,
		STILL,
		NO_RETURN,
		LOW_BATTERY,
		NO_BATTERY,
		UNKNOWN_ERROR
	}

	public static enum AgentType {
		TERRESTRIAL,
		AERIAL,
		SUBMARINE;
	}

	public interface sendMessageCallback {
		public void callback(Object message);
	}

	static int					nextID			= 1;
	private int					ID				= -1;
	private String				name			= null;
	private boolean				connected		= false;
	private AgentType			type			= AgentType.TERRESTRIAL;
	private AgentState			state			= AgentState.OK;
	private LinkedList<Point>	positions		= new LinkedList<>();
	private LinkedList<Point>	orders			= new LinkedList<>();
	private Area				destination		= null;
	private AgentMessageManager	messageManager	= new AgentMessageManager();

	private sendMessageCallback	messageCallback	= null;

	private Date				lastContact		= Date.from(Instant.now());

	public Agent(String name) {
		this.ID = Agent.nextID++;
		this.name = name;
		this.setCurrentPosition(new Point(0, 0, 0));
		this.messageManager.addHandler("position", new AgentMessageHandler<Point>(Point.class) {
			@Override
			public void handleMessage(Point data, Agent agent) {
				agent.setCurrentPosition(data);
			}
		});
	}
	
	public Agent() {
		this.ID = Agent.nextID++;
		this.setCurrentPosition(new Point(0, 0, 0));
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

	public int getID() {
		return (this.ID);
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

	public boolean isConnected() {
		return (this.connected);
	}

	public void pushOrder(Point order) {
		this.orders.push(order);
		this.sendMessage("order:%o", order);
	}

	public void recall() {
		// TODO Auto-generated method stub
	}

	public void receiveMessage(String msg) {
		try {
			this.messageManager.handleMessage(msg, this);
		} catch (IOException e) {
			this.sendMessage(e.getMessage());
		}

	}

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

	public void updateState() {
		if (Date.from(Instant.now()).getTime() - this.lastContact.getTime() > 5 * 60 * 1000)
			this.state = AgentState.LOST;
	}
}
