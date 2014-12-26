package eip.smart.model.proxy;

public abstract class Proxy<T> {
	protected T	object;

	public Proxy() {
		this.object = null;
	}

	public Proxy(T object) {
		this.object = object;
	}
}
