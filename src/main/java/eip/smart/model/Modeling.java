package eip.smart.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.lang.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import eip.smart.model.geometry.Point;

/**
 * Created by Pierre Demessence on 10/10/2014.
 */
@SuppressWarnings({ "static-method", "unused" })
public class Modeling implements Serializable {

	private final static Logger	LOGGER	= Logger.getLogger(Modeling.class.getName());

	private String				name	= "";
	private ArrayList<Area>		areas	= new ArrayList<>();
	private ArrayList<Agent>	agents	= new ArrayList<>();
	private long				tick	= 0;

	public Modeling() {}

	public Modeling(String name) {
		this.name = name;
		this.areas.add(new Area());
		this.areas.add(new Area());
	}

	public void addAgent(Agent agent) {
		this.agents.add(agent);
	}

	public void addArea(Area area) {
		this.areas.add(area);
	}

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

	private void handleAGentsState() {
		Modeling.LOGGER.log(Level.INFO, "-->Handling Agents State...");
	}

	public void removeAgent(Agent agent) {
		this.agents.remove(agent);
	}

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

	private void setName(String name) {
		this.name = name;
	}

	private void setTick(long tick) {
		this.tick = tick;
	}

	private void updateAgents() {
		Modeling.LOGGER.log(Level.INFO, "->Updating Agents...");
		this.updateAgentsState();
		this.handleAGentsState();
	}

	private double getDiffPoint(Point x, Point y) {
		return (Math.abs((Math.abs(x.getX() - y.getX())) - (Math.abs(x.getY() - y.getY()))));
	}

	private void updateAgentsDestination() {
		Modeling.LOGGER.log(Level.INFO, "->Updating destination for each agent...");
	}

	private void updateAgentsOrders() {
		Modeling.LOGGER.log(Level.INFO, "->Updating orders for each agent...");
	}

	private void updateAgentsState() {
		Modeling.LOGGER.log(Level.INFO, "-->Updating Agents State...");
	}

	private void updateAreaAgentsAttributed() {
		//for (Area a : this.areas)
			//a.updateCompletion();
		Area dest;
		if (this.areas.size() > 0)
		{
			for (Agent agent : this.agents) {
				dest = this.areas.get(0);
				for (Area area : this.areas) {
					if (getDiffPoint(agent.getCurrentPosition(), area.getAvgPoint()) < getDiffPoint(agent.getCurrentPosition(), dest.getAvgPoint()))
						dest = area;
				}
				agent.setDestination(dest);
			}
		}			
		Modeling.LOGGER.log(Level.INFO, "->Attributing number of agents for each area...");
	}

}
