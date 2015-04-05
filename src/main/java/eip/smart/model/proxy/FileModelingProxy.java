package eip.smart.model.proxy;
import java.util.ArrayList;
import eip.smart.model.Area;
import eip.smart.model.Modeling;
import java.io.Serializable;

/**
 * <b>FileModelingProxy est la classe utilis�e pour s�rialiser une mod�lisation.</b>
 * Elle consiste en une classe contenant uniquement les �l�ments qu'il est int�ressant de sauvegarder (les agents par exemple, sont exclus)
 * @author Pierre Demessence
*/
public class FileModelingProxy extends Proxy<Modeling> implements Serializable {

	private double	completion;
	private String	name;
	private long	tick;
	private boolean	obsolete;
	private ArrayList<Area> areas;
	
	
	public FileModelingProxy() {}

	/**
	 * Constructeur prenant en param�tre une mod�lisation, et copiant ses attribus
	 * @param modelisation
	 */
	public FileModelingProxy(Modeling object) {
		super(object);
		this.completion = object.getCompletion();
		this.name = object.getName();
		this.tick = object.getTick();
		this.obsolete = false;
		
		this.areas = object.getAreas();
		
	}

	public ArrayList<Area> getArea() {
		return (this.areas);
	}
	
	public double getCompletion() {
		return (this.completion);
	}

	public String getName() {
		return (this.name);
	}

	public long getTick() {
		return (this.tick);
	}

	public boolean isObsolete() {
		return (this.obsolete);
	}
	
	public void setAreas(ArrayList<Area> areas) {
		this.areas = areas;
	}
	
	public void setCompletion(double completion) {
		this.completion = completion;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTick(long tick) {
		this.tick = tick;
	}
	
	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}
}
