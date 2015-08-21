package eip.smart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eip.smart.model.agent.Agent;
import eip.smart.model.geometry.Point;
import eip.smart.model.proxy.FileModelingProxy;

/**
 * <b>Modeling is the class allowing the management of the modelisations.</b>
 *
 * @author Pierre Demessence
 */
@SuppressWarnings({ "static-method", "unused" })
public class Modeling implements Serializable {

	private final static Logger	LOGGER	= Logger.getLogger(Modeling.class.getName());

	/**
	 * String allowing to identify the modeling
	 */
	private String				name	= "";

	/**
	 * Areas'array, list the areas that have to be modelised
	 *
	 * @see Area
	 */
	private ArrayList<Area>		areas	= new ArrayList<>();

	/**
	 * Agent'sarray, list the agents used on this modeling
	 *
	 * @see Agent
	 */
	private ArrayList<Agent>	agents	= new ArrayList<>();

	/**
	 * long, number of uses of the "run" method by the modeling
	 */
	private long				tick	= 0;

	/**
	 * default constructor
	 */
	public Modeling() {}

	/**
	 * Constructor allowing to create a modeling based on a saved one, in order to continue it.
	 *
	 * @see FileModelingProxy
	 * @param fileModeling
	 *            objet containing informations about a saved modeling
	 */
	public Modeling(FileModelingProxy fileModeling) {
		this.name = fileModeling.getName();
		this.areas = fileModeling.getArea();
		this.agents = null;
	}

	/**
	 * Constructor allowing to give a name to the modeling at it creation
	 *
	 * @param name
	 *            name that the modeling'll have
	 */
	public Modeling(String name) {
		this.name = name;
		this.areas.add(new Area());
		this.areas.add(new Area());
	}

	/**
	 * Add an agent to the modeling
	 *
	 * @see Agent
	 * @param agent
	 *            Agent that will be added to the modeling
	 */
	public void addAgent(Agent agent) {
		this.agents.add(agent);
	}

	/**
	 * Add an area to the modeling
	 *
	 * @see Area
	 * @param area
	 *            Area that will be added to the modeling
	 */
	public void addArea(Area area) {
		this.areas.add(area);
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

	private double getDiffPoint(Point x, Point y) {
		return (Math.abs((Math.abs(x.getX() - y.getX())) - (Math.abs(x.getY() - y.getY()))));
	}

	public String getName() {
		return (this.name);
	}

	public long getTick() {
		return (this.tick);
	}

	/**
	 * remove an agent of the modeling
	 *
	 * @see Agent
	 * @param agent
	 *            agent, the one we'll remove
	 */
	public void removeAgent(Agent agent) {
		this.agents.remove(agent);
	}

	/**
	 * main method of of the modeling development, it:
	 * <ul>
	 * <li>update agents'position</li>
	 * <li>update agents'attributed'areas</li>
	 * <li>update agents'destination's area</li>
	 * <li>update agents'destination's points</li>
	 * </ul>
	 *
	 * @see Agent
	 */
	public void run() {
		++this.tick;
		Modeling.LOGGER.log(Level.INFO, "Modeling (" + this.name + ") running (tick " + this.tick + ")");
		this.updateAreaAgentsAttributed();
		this.updateAgents();
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
	 * update agents'state
	 *
	 * @see Agent
	 */
	private void updateAgents() {
		Modeling.LOGGER.log(Level.INFO, "->Updating Agents...");
		for (Agent agent : this.agents)
			agent.updateState();
	}

	/**
	 * update agents'attributed's areas
	 *
	 * @see Agent
	 */
	private void updateAreaAgentsAttributed() {
		// for (Area a : this.areas)
		// a.updateCompletion();
		Area dest;
		if (this.areas.size() > 0)
			for (Agent agent : this.agents) {
				dest = this.areas.get(0);
				for (Area area : this.areas)
					if (this.getDiffPoint(agent.getCurrentPosition(), area.getAvgPoint()) < this.getDiffPoint(agent.getCurrentPosition(), dest.getAvgPoint()))
						dest = area;
				agent.setDestination(dest);
			}
		Modeling.LOGGER.log(Level.INFO, "->Attributing number of agents for each area...");
	}

}
