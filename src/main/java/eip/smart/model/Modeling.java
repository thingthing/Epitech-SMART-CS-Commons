package eip.smart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eip.smart.model.proxy.FileModelingProxy;

/**
 * <b>Modeling est la classe représentant et permettant de gérer les modélisations.</b>
 * @author Pierre Demessence
*/
@SuppressWarnings({ "static-method", "unused" })
public class Modeling implements Serializable {

	private final static Logger	LOGGER	= Logger.getLogger(Modeling.class.getName());

	/**
	 * chaine de caractère (String), permettant d'identifier une modélisation
	 */
	private String				name	= "";
	
	/**
	 * tableau de zones (ArrayList<Area>), listant les zones à modéliser
	 * 
	 * @see Area
	 */
	private ArrayList<Area>		areas	= new ArrayList<>();
	
	/**
	 * tableau d'Agents (ArrayList<Agent>), listant les agents utilisés pour cette modélisation
	 * 
	 * @see Agent
	 */
	private ArrayList<Agent>	agents	= new ArrayList<>();
	
	/**
	 * nombre entier (long), représentant le nombre d'utilisation de la méthode "run" par la modélisation 
	 */
	private long				tick	= 0;

	/**
	 * Constructeur par défaut
	 */
	public Modeling() {}

	/**
	 * Constructeur permettant de créer une modélisation à partir d'une sauvegarde de modélisation, dans le but de la poursuivre.
	 * 
	 * @see FileModelingProxy
	 * @param fileModeling objet contenant les informations relatives à une modélisation sauvegardée
	 */
	public Modeling(FileModelingProxy fileModeling) {
		this.name = fileModeling.getName();
		this.areas = fileModeling.getArea();
		this.agents = null;
	}

	/**
	 * Constructeur permettant de donner un nom à la modélisation à sa création
	 * 
	 * @param name nom que portera la modélisation
	 */
	public Modeling(String name) {
		this.name = name;
		this.areas.add(new Area());
		this.areas.add(new Area());
	}

	/**
	 * Ajoute un agent à la modélisation
	 * 
	 * @see Agent
	 * @param agent agent qui sera ajouté à la modélisation
	 */
	public void addAgent(Agent agent) {
		this.agents.add(agent);
	}

	/**
	 * Ajoute une zone à la modélisation
	 * 
	 * @see Area
	 * @param area zone qui sera ajoutée à la modélisation
	 */
	public void addArea(Area area) {
		this.areas.add(area);
	}

	/**
	 * Ecris dans les logs les informations relatives aux agents de la modélisation
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
	 * Inscrit dans les logs l'état de chaque agent
	 * 
	 * @see Agent
	 */
	private void handleAGentsState() {
		Modeling.LOGGER.log(Level.INFO, "-->Handling Agents State...");
	}

	/**
	 * Retire un agent de la modélisation
	 * 
	 * @see Agent
	 * @param agent agent à retirer de la modélisation
	 */
	public void removeAgent(Agent agent) {
		this.agents.remove(agent);
	}

	/**
	 * Méthode principale du déroulement d'une modélisation, elle :
	 * <ul>
	 * <li>met à jour la position des agents</li>
	 * <li>met à jour les zones attribuées aux agents</li>
	 * <li>met à jour les zones de destinations des agents</li>
	 * <li>met à jour les points de destinations des agents (appelés ordres)</li>
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
	 * Met à jour l'état des agents
	 * 
	 * @see Agent
	 */
	private void updateAgents() {
		Modeling.LOGGER.log(Level.INFO, "->Updating Agents...");
		this.updateAgentsState();
		this.handleAGentsState();
	}

	/**
	 * Met à jour la destination des agents
	 * 
	 * @see Agent
	 */
	private void updateAgentsDestination() {
		Modeling.LOGGER.log(Level.INFO, "->Updating destination for each agent...");
	}

	/**
	 * Met à jour les ordres des agents
	 * 
	 * @see Agent
	 */
	private void updateAgentsOrders() {
		Modeling.LOGGER.log(Level.INFO, "->Updating orders for each agent...");
	}

	/**
	 * Met à jour le status des agents
	 * 
	 * @see Agent
	 */
	private void updateAgentsState() {
		Modeling.LOGGER.log(Level.INFO, "-->Updating Agents State...");
	}

	/**
	 * Met à jour les zones attribuées aux agents
	 * 
	 * @see Agent
	 */
	private void updateAreaAgentsAttributed() {
		for (Area a : this.areas)
			a.updateCompletion();
		Modeling.LOGGER.log(Level.INFO, "->Attributing number of agents for each area...");
	}

}
