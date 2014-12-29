package eip.smart.model.proxy;

import eip.smart.model.Status;

public class StatusProxy extends Proxy<Status> {

	private int		code;
	private String	message;

	public StatusProxy() {}

	public StatusProxy(Status object) {
		super(object);
		this.setCode(object.getCode());
		this.setMessage(object.getMessage());
	}

	public int getCode() {
		return (this.code);
	}

	public String getMessage() {
		return (this.message);
	}

	public void setCode(int code) {
		this.code = code;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return this.code + " : " + this.message;
	}

}
