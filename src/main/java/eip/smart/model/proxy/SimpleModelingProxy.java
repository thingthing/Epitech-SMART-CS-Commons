package eip.smart.model.proxy;

import eip.smart.model.Modeling;

public class SimpleModelingProxy extends Proxy<Modeling> {

	private double	completion;
	private String	name;
	private long	tick;

	public SimpleModelingProxy() {}

	public SimpleModelingProxy(Modeling object) {
		super(object);
		this.completion = object.getCompletion();
		this.name = object.getName();
		this.tick = object.getTick();
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

	public void setCompletion(double completion) {
		this.completion = completion;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTick(long tick) {
		this.tick = tick;
	}

}
