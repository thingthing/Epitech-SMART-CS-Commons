package eip.smart.cscommons.model.modeling;

import com.fasterxml.jackson.annotation.JsonView;
import eip.smart.cscommons.model.JSONViews;
import eip.smart.cscommons.model.agent.Agent;
import eip.smart.cscommons.model.geometry.PointCloud3D;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <b>Modeling is the class allowing the management of the modelisations.</b>
 *
 * @author Pierre Demessence
 */

public class Modeling implements Serializable {

	/**
	 *
	 */
	private static final long	serialVersionUID	= 1L;

	/**
	 * Agent'sarray, list the agents used on this modeling
	 *
	 * @see Agent
	 */
	@JsonView(JSONViews.HTTP.class)
	protected List<Agent>		agents				= new ArrayList<>();

	/**
	 * Areas'array, list the areas that have to be modelised
	 *
	 * @see Area
	 */
	@JsonView({ JSONViews.HTTP.class, JSONViews.DISK.class })
	protected List<Area>		areas				= new ArrayList<>();

	@JsonView({ JSONViews.HTTP.class, JSONViews.DISK.class })
	protected double			completion			= 0;

	@JsonView({ JSONViews.HTTP.class, JSONViews.DISK.class })
	protected Date				lastSave			= null;

	@JsonView()
	protected PointCloud3D		mapping				= new PointCloud3D();

	@JsonView({ JSONViews.HTTP.class })
	protected boolean			modified			= false;

	/**
	 * String allowing to identify the modeling
	 */
	@JsonView({ JSONViews.HTTP.class, JSONViews.DISK.class })
	protected String			name				= "";

	@JsonView(JSONViews.HTTP.class)
	protected boolean			obsolete			= false;

	@JsonView({ JSONViews.HTTP.class })
	protected ModelingState		state				= ModelingState.UNLOADED;

	/**
	 * long, number of uses of the "run" method by the modeling
	 */
	@JsonView({ JSONViews.HTTP.class, JSONViews.DISK.class })
	protected long				tick				= 0;

	@JsonView({ JSONViews.HTTP.class, JSONViews.DISK.class })
	protected int nbPoints = 0;

	/**
	 * default constructor
	 */
	public Modeling() {
		this.agents = new ArrayList<>();
		this.nbPoints = this.mapping.size();
	}

	public Modeling(Modeling modeling) {
		this(modeling.name);
		if (modeling.agents != null)
			this.agents.addAll(modeling.agents);
		this.areas = modeling.areas;
		this.tick = modeling.tick;
		this.obsolete = modeling.obsolete;
		this.mapping = modeling.mapping;
		this.lastSave = modeling.lastSave;
		this.nbPoints = modeling.nbPoints;
	}

	/**
	 * Constructor allowing to give a name to the modeling at it creation
	 *
	 * @param name
	 *            name that the modeling'll have
	 */
	public Modeling(String name) {
        this();
        this.name = name;
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

	public double getCompletion() {
		return (this.completion);
	}

	public Date getLastSave() {
		return this.lastSave;
	}

	public PointCloud3D getMapping() {
		return this.mapping;
	}

	public String getName() {
		return (this.name);
	}

	public int getNbPoints() {
		return (this.nbPoints);
	}

	public ModelingState getState() {
		return this.state;
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

	public boolean isModified() {
		return this.modified;
	}

	public boolean isObsolete() {
		return (this.obsolete);
	}

}
