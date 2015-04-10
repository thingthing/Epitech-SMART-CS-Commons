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
  * <b>Agent est la classe représentant et permettant de gérer un agent.</b>
  * @author Pierre Demessence
  * @version 3.0
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

	/**
	 * Nom (String), servant d'identifiant unique
	 */
	private String				name			= "";
	
	/**
	 * Booleen, permettant de déterminer si l'agent est connecté
	 */
	private boolean				connected		= false;
	
	/**
	 * Type (AgentType), permettant de déterminer le milieu sur lequel l'agent peut évoluer
	 * @see AgentType
	 */
	private AgentType			type			= AgentType.TERRESTRIAL;
	
	/**
	 * Etat (AgentState), permettant de déterminer la situation de l'agent (ok, immobile, perdu, etc)
	 * @see AgentState
	 */
	private AgentState			state			= AgentState.OK;

	/**
	 * Liste des positions qu'a eu l'agent (LinkedList<Point>), la dernière étant sa dernière position connue
	 * @see Point
	 */
	private LinkedList<Point>	positions		= new LinkedList<>();
	
	/**
	 * Liste d'ordres (LinkedList<Point>), les positions auxquelles l'agent doit se rendre
	 * @see Point
	 */
	private LinkedList<Point>	orders			= new LinkedList<>();
	
	/**
	 * Zone de destination (Area), que l'agent devra explorer si la liste d'ordres est vide
	 * @see Area
	 */
	private Area				destination		= null;
	
	/**
	 *  Objet (AgentMessageManager), gérant la réception des messages
	 *  @see AgentMessageManager
	 */
	private AgentMessageManager	messageManager	= new AgentMessageManager();

	/**
	 * Objet (sendMessageCallback), gérant l'envoi des messages
	 * @see sendMessageCallback	
	 */
	private sendMessageCallback	messageCallback	= null;

	
	/**
	 * Date de dernier contact avec l'agent (Date), permettant de déterminer son état
	 */
	private Date				lastContact		= Date.from(Instant.now());

	/**
	 * constructeur par défaut, place l'agent en coordonnées (0, 0, 0) et créer un handler pour mettre à jour sa position
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
	 * Constructeur permettant de donner un nom à l'agent à sa création
	 * 
	 * @param name nom que portera l'agent
	 */
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

	/**
	 * Retourne un boolean ayant pour valeur "true" si l'agent est connecté et "false" s'il ne l'est pas.
	 * 
	 * @return Un boolean permettant de déterminer si l'agent est connecté.
	 */
	public boolean isConnected() {
		return (this.connected);
	}

	/**
	 * Ajoute un Point à la liste d'ordres de l'agent
	 * 
	 * @see Point
	 * @param order Point, nouvel ordre à envoyer à l'agent
	 */
	public void pushOrder(Point order) {
		this.orders.push(order);
		this.sendMessage("order:%o", order);
	}

	/**
	 * Donne à l'agent l'ordre de retourner à son point de départ
	 */
	public void recall() {
		// TODO Auto-generated method stub
	}

	/**
	 * Vérifie si un message a été reçu par l'agent
	 * @param msg String, chaine de caractères représentant le message reçu
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
	 * @param objects Un ou plusieurs objets qui seront envoyés
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
	 * Met à jours l'état de l'agent, en se basant sur les attribus "positions" et "lastContact"
	 */
	public void updateState() {
		if (Date.from(Instant.now()).getTime() - this.lastContact.getTime() > 5 * 60 * 1000)
			this.state = AgentState.LOST;
	}
}
