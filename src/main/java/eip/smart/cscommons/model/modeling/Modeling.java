package eip.smart.cscommons.model.modeling;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

import eip.smart.cscommons.model.JSONViews;
import eip.smart.cscommons.model.agent.Agent;

/**
 * <b>Modeling is the class allowing the management of the modelisations.</b>
 *
 * @author Pierre Demessence
 */

public class Modeling implements Serializable {

	/**
	 * Agent'sarray, list the agents used on this modeling
	 *
	 * @see Agent
	 */
	@JsonView(JSONViews.ALL.class)
	protected transient List<Agent>	agents		= new ArrayList<>();

	/**
	 * Areas'array, list the areas that have to be modelised
	 *
	 * @see Area
	 */
	@JsonView({ JSONViews.ALL.class, JSONViews.DISK.class })
	protected List<Area>			areas		= new ArrayList<>();

	/**
	 * String allowing to identify the modeling
	 */
	@JsonView({ JSONViews.IMPORTANT.class, JSONViews.DISK.class })
	protected String				name		= "";

	@JsonView(JSONViews.IMPORTANT.class)
	protected boolean				obsolete	= false;

	/**
	 * long, number of uses of the "run" method by the modeling
	 */
	@JsonView({ JSONViews.IMPORTANT.class, JSONViews.DISK.class })
	protected long					tick		= 0;

	/**
	 * default constructor
	 */
	public Modeling() {
		this.areas.add(new Area());
	}

	public Modeling(Modeling modeling) {
		this.agents = modeling.agents;
		this.areas = modeling.areas;
		this.name = modeling.name;
		this.tick = modeling.tick;
		this.obsolete = modeling.obsolete;
	}

	/**
	 * Constructor allowing to give a name to the modeling at it creation
	 *
	 * @param name
	 *            name that the modeling'll have
	 */
	public Modeling(String name) {
		this.name = name;
		// this.areas.add(new Area());
		// this.areas.add(new Area());
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
		if (!(obj instanceof Modeling))
			return false;
		Modeling other = (Modeling) obj;
		if (this.name == null) {
			if (other.name != null)
				return false;
		} else if (!this.name.equals(other.name))
			return false;
		return true;
	}

	public List<? extends Agent> getAgents() {
		return (this.agents);
	}

	public List<Area> getAreas() {
		return (this.areas);
	}

	@JsonView(JSONViews.IMPORTANT.class)
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

	public boolean isObsolete() {
		return (this.obsolete);
	}

}