package eip.smart.model;

import java.io.IOException;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import javax.swing.text.html.HTMLDocument.Iterator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import eip.smart.model.Agent.AgentState;
import eip.smart.model.geometry.Point;
import eip.smart.model.proxy.SimpleAgentProxy;
import eip.smart.model.status.*;
/**
 * Created by Pierre Demessence on 09/10/2014.
 */
public class Agent implements Serializable {

	// priority 
	public static enum AgentState {
		
		// this status has to be activated by an agent's message
		LOW_BATTERY(new State(){
			public int priority = 0;
			private boolean lock = true;
			
			@Override
			public void doAction(Agent agent) {
				agent.recall();
				// switch agent status to RECALL_BATTERY
			}

			@Override
			public boolean canMove() {
				return false;
			}
		}),
		
		// this status has to be activated by an agent's message
		DERANGED(new State(){
			public int priority = 0;
			private boolean lock = true;

			@Override
			public void doAction(Agent agent) {
			}

			@Override
			public boolean canMove() {
				return false;
			}
		}),
		
		// this status has to be activated by an agent's message
		BLOCKED(new State(){
			public int priority = 0;
			private boolean lock = true;
			
			@Override
			public void doAction(Agent agent) {
			}
			
			@Override
			public boolean canMove() {
				return false;
			}
		}),
		
		LOST_SIGNAL(new State(){
			public int priority = 0;

			@Override
			public boolean canMove() {
				return false;
			}
		}),
		
		// this status has to be activated by an agent's message
		RECALL_ERROR(new State(){
			public int priority = 0;
			private boolean lock = true;

			@Override
			public void doAction(Agent agent) {
			}
			
			//the agent is coming to the base because of an error, he can't receive orders
			@Override
			public boolean canMove() {
				return false;
			}
		}),
		
		// this status has to be activated by an agent's message		
		UNKNOWN_ERROR(new State(){
			public int priority = 0;
			private boolean lock = true;

			@Override
			public void doAction(Agent agent) {
			}
			
			@Override
			public boolean canMove() {
				return false;
			}
		}),
		
		
		// decided by server
			// not checked
		NO_BATTERY(new State(){
			public int priority = 1;
			private boolean lock = true;

			@Override
			public void doAction(Agent agent) {
			}
			
			@Override
			public boolean canMove() {
				return false;
			}
		}),
		
		RECALL_BATTERY(new State(){
			public int priority = 1;
			
			@Override
			public void doAction(Agent agent) {
				agent.recall();
			}
			
			//the agent is coming to the base because it has not enough battery, he can't receive orders
			@Override
			public boolean canMove() {
				return false;
			}

			// this status is activated by the LOW_BATTERY status
		}),
		
		RECALL(new State(){
			public int priority = 1;
			
			@Override
			public void doAction(Agent agent) {
				agent.recall();
			}
			
			@Override
			public boolean canMove() {
				return true;
			}

			// this status is activated by a server's decision
		}),
				
		// checked
	STILL_ERROR(new State(){
		public int priority = 5;
		
		@Override
		public void doAction(Agent agent) {
			//try to move... somewhere
		}

		@Override
		public boolean canMove() {
			return true;
		}

		@Override
		public boolean checkState(Agent agent) {
			boolean still = true;
			int check_size = 50;
			int i = 0;
			
				while (i < ((agent.getPositions().size() > check_size) ? check_size : agent.getPositions().size()))
				{
					if (agent.getCurrentPosition() != agent.getPositions().get(i) && still)
						still = false;
					i++;
				}
			if (still)
				return true;
			else
				return false;
		}
	}),
		
	STILL(new State(){
		public int priority = 4;
		
			@Override
			public void doAction(Agent agent) {
				//try to move... somewhere
			}

			@Override
			public boolean canMove() {
				return true;
			}

			@Override
			public boolean checkState(Agent agent) {
				boolean still = true;
				int check_size = 10;
				int i = 0;
				
					while (i < ((agent.getPositions().size() > check_size) ? check_size : agent.getPositions().size()))
					{
						if (agent.getCurrentPosition() != agent.getPositions().get(i) && still)
							still = false;
						i++;
					}
				if (still)
					return true;
				else
					return false;
			} 
		}),
		
		NO_SIGNAL(new State(){
			private int cpt = 4;
						
			@Override
			public void doAction(Agent agent) {
				if (agent.isConnected())
				{
					//set agent status to OK
				}
				if (++cpt == 20)
				{
					//set agent status to LOST_SIGNAL
				}
			}
			
			@Override
			public boolean canMove() {
				return false;
			}

			@Override
			public boolean checkState(Agent agent) {
				if (Date.from(Instant.now()).getTime() - agent.getLastContact().getTime() > 5 * 60 * 1000)
					return true;
				else
					return false;
			}
		}),
		
		OK(new State(){
			public int priority = -1;
			
			@Override
			// do all the normal agent actions
			public void doAction(Agent agent) {
				
			}

			@Override
			public boolean canMove() {
				return true;
			}

			// default value
			@Override
			public boolean checkState(Agent agent) {
				return true;
			}
			
			public boolean isLocked() {
				return (false);
			}
		});
		
		private State status;
		
		AgentState(State status) {
			this.status = status;
		}
		
		public static AgentState updateAgentState(Agent agent) {
			AgentState[] StatesValues = AgentState.values();
			ArrayList<AgentState> list = new ArrayList<AgentState>(Arrays.asList(StatesValues));
			Collections.sort(list, new Comparator<Object>() {
				public int compare(Object obj1, Object obj2) {
					AgentState as1 = (AgentState)obj1;
					AgentState as2 = (AgentState)obj2;
					int lib1 = as1.status.priority;
					int lib2 = as2.status.priority;
					if (lib1 > lib2)
						return (-1);
					else if (lib1 == lib2)
						return (0);
					else
						return (1);
				}
			});
		
			for (AgentState state : list)
				if (state.status.checkState(agent))
					return (state);
			return (null);
		}		
	}

	public static enum AgentType {
		TERRESTRIAL,
		AERIAL,
		SUBMARINE;
	}

	public interface sendMessageCallback {
		public void callback(Object message);
	}

	private String				name			= "";
	private boolean				connected		= false;
	private AgentType			type			= AgentType.TERRESTRIAL;
	private AgentState			state			= AgentState.OK;
	private LinkedList<Point>	positions		= new LinkedList<>();
	private LinkedList<Point>	orders			= new LinkedList<>();
	private Area				destination		= null;
	private AgentMessageManager	messageManager	= new AgentMessageManager();

	private sendMessageCallback	messageCallback	= null;

	private Date				lastContact		= Date.from(Instant.now());
	
	private AgentState statesAgent;
	
	public Agent() {
		this.setCurrentPosition(new Point(0, 0, 0));
		this.messageManager.addHandler("position", new AgentMessageHandler<Point>(Point.class) {
			@Override
			public void handleMessage(Point data, Agent agent) {
				agent.setCurrentPosition(data);
			}
		});
	}

	public Agent(String name) {
		this();
		this.name = name;
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

		if (!this.statesAgent.status.isLocked())
			AgentState.updateAgentState(this);
		
		/*
		boolean still = true;
		int check_size = 10;
		int i = 0;
		
			while (i < ((this.getPositions().size() > check_size) ? check_size : this.getPositions().size()))
			{
				if (this.getCurrentPosition() != this.getPositions().get(i) && still)
					still = false;
				i++;
			}
		if (Date.from(Instant.now()).getTime() - this.lastContact.getTime() > 5 * 60 * 1000)
			this.state = AgentState.LOST;
		else if (still)
			this.state = AgentState.STILL;
		else
			this.state = AgentState.OK;
		 */
	}
		 
}
