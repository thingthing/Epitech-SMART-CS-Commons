package eip.smart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eip.smart.model.proxy.FileModelingProxy;

/**
 * <b>Modeling est la classe repr�sentant et permettant de g�rer les mod�lisations.</b>
 * @author Pierre Demessence
*/
@SuppressWarnings({ "static-method", "unused" })
public class Modeling implements Serializable {

	private final static Logger	LOGGER	= Logger.getLogger(Modeling.class.getName());

	/**
	 * chaine de caract�re (String), permettant d'identifier une mod�lisation
	 */
	private String				name	= "";
	
	/**
	 * tableau de zones (ArrayList<Area>), listant les zones � mod�liser
	 * 
	 * @see Area
	 */
	private ArrayList<Area>		areas	= new ArrayList<>();
	
	/**
	 * tableau d'Agents (ArrayList<Agent>), listant les agents utilis�s pour cette mod�lisation
	 * 
	 * @see Agent
	 */
	private ArrayList<Agent>	agents	= new ArrayList<>();
	
	/**
	 * nombre entier (long), repr�sentant le nombre d'utilisation de la m�thode "run" par la mod�lisation 
	 */
	private long				tick	= 0;

	/**
	 * Constructeur par d�faut
	 */
	public Modeling() {}

	/**
	 * Constructeur permettant de cr�er une mod�lisation � partir d'une sauvegarde de mod�lisation, dans le but de la poursuivre.
	 * 
	 * @see FileModelingProxy
	 * @param fileModeling objet contenant les informations relatives � une mod�lisation sauvegard�e
	 */
	public Modeling(FileModelingProxy fileModeling) {
		this.name = fileModeling.getName();
		this.areas = fileModeling.getArea();
		this.agents = null;
	}

	/**
	 * Constructeur permettant de donner un nom � la mod�lisation � sa cr�ation
	 * 
	 * @param name nom que portera la mod�lisation
	 */
	public Modeling(String name) {
		this.name = name;
		this.areas.add(new Area());
		this.areas.add(new Area());
	}

	/**
	 * Ajoute un agent � la mod�lisation
	 * 
	 * @see Agent
	 * @param agent agent qui sera ajout� � la mod�lisation
	 */
	public void addAgent(Agent agent) {
		this.agents.add(agent);
	}

	/**
	 * Ajoute une zone � la mod�lisation
	 * 
	 * @see Area
	 * @param area zone qui sera ajout�e � la mod�lisation
	 */
	public void addArea(Area area) {
		this.areas.add(area);
	}

	/**
	 * Ecris dans les logs les informations relatives aux agents de la mod�lisation
	 * 
	 * @see Agent
	 */
	public void dumpAgents() {
		Modeling.LOGGER.log(Level.INFO, "Dumping Agents");
		for (Agent a : this.agents) {
			Modeling.LOGGER.log(Level.INFO, "Agent " + a.getName() + " :");
			Modeling.LOGGER.log(Level.INFO, "--Position : " + a.getCurrentPosition());
			Modeling.LOGGER.log(Level.INFO, "--Destination : " + a.getDestination());
		}
	}

	public ArrayList<Agent> getAgents() {
		return (this.agents);
	}

	public ArrayList<Area> getAreas() {
		return (this.areas);
	}

	@JsonIgnore
	public double getCompletion() {
		double res = 0;

		if (this.areas.size() == 0)
			return (100.0d);

		for (Area a : this.areas)
			res += a.getCompletion();
		res /= this.areas.size();
		return (res);
	}

	public String getName() {
		return (this.name);
	}

	public long getTick() {
		return (this.tick);
	}

	/**
	 * Inscrit dans les logs l'�tat de chaque agent
	 * 
	 * @see Agent
	 */
	private void handleAGentsState() {
		Modeling.LOGGER.log(Level.INFO, "-->Handling Agents State...");
	}

	/**
	 * Retire un agent de la mod�lisation
	 * 
	 * @see Agent
	 * @param agent agent � retirer de la mod�lisation
	 */
	public void removeAgent(Agent agent) {
		this.agents.remove(agent);
	}

	/**
	 * M�thode principale du d�roulement d'une mod�lisation, elle :
	 * <ul>
	 * <li>met � jour la position des agents</li>
	 * <li>met � jour les zones attribu�es aux agents</li>
	 * <li>met � jour les zones de destinations des agents</li>
	 * <li>met � jour les points de destinations des agents (appel�s ordres)</li>
	 * </ul>
	 * 
	 * @see Agent
	 */
	public void run() {
		++this.tick;
		Modeling.LOGGER.log(Level.INFO, "Modeling (" + this.name + ") running (tick " + this.tick + ")");
		this.updateAgents();
		this.updateAreaAgentsAttributed();
		this.updateAgentsDestination();
		this.updateAgentsOrders();
	}

	public void setAgents(ArrayList<Agent> agents) {
		this.agents = agents;
	}

	public void setAreas(ArrayList<Area> areas) {
		this.areas = areas;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	private void setTick(long tick) {
		this.tick = tick;
	}

	/**
	 * Met � jour l'�tat des agents
	 * 
	 * @see Agent
	 */
	private void updateAgents() {
		Modeling.LOGGER.log(Level.INFO, "->Updating Agents...");
		this.updateAgentsState();
		this.handleAGentsState();
	}

	/**
	 * Met � jour la destination des agents
	 * 
	 * @see Agent
	 */
	private void updateAgentsDestination() {
		Modeling.LOGGER.log(Level.INFO, "->Updating destination for each agent...");
	}

	/**
	 * Met � jour les ordres des agents
	 * 
	 * @see Agent
	 */
	private void updateAgentsOrders() {
		Modeling.LOGGER.log(Level.INFO, "->Updating orders for each agent...");
	}

	/**
	 * Met � jour le status des agents
	 * 
	 * @see Agent
	 */
	private void updateAgentsState() {
		Modeling.LOGGER.log(Level.INFO, "-->Updating Agents State...");
	}

	/**
	 * Met � jour les zones attribu�es aux agents
	 * 
	 * @see Agent
	 */
	private void updateAreaAgentsAttributed() {
		for (Area a : this.areas)
			a.updateCompletion();
		Modeling.LOGGER.log(Level.INFO, "->Attributing number of agents for each area...");
	}

}
