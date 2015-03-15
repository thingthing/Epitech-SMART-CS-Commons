package eip.smart.model;

public enum Status {
	UNKNOWN(-3, "unknown error"),
	SIMULATION(-2, "SIMULATION"),
	TODO(-1, "TODO"),
	OK(0, "ok"),
	MODELING_ALREADY_CURRENT("a modeling is already loaded"),
	MODELING_NO_CURRENT("no current modeling"),
	MODELING_DUPLICATE_NAME("modeling with given name already exist"),
	MODELING_NOT_FOUND("modeling with given name does not exist"),
	MODELING_ALREADY_RUNNING("current modeling is already running"),
	MODELING_NOT_RUNNING("current modeling is not running"),
	MODELING_ALREADY_PAUSED("current modeling is already paused"),
	MODELING_NOT_PAUSED("current modeling is not paused"),
	MODELING_NO_NAME("modeling must have a name"),
	AGENT_NOT_FOUND("agent with given name does not exist"),
	AGENT_ALREADY_ADDED("agent already added to modeling"),
	AGENT_NOT_ADDED("agent not already added to modeling"),
	ORDER_NO_GIVEN("no order was given"),
	AREA_NO_GIVEN("no area was given"),
	PORT_NO_GIVEN("no port was given"),
	PORT_BAD("given port is bad"),
	PORT_ALREADY_USED("port already used"),
	SOCKET_ALREADY_RUNNING("tcp server is already running"),
	SOCKET_NOT_RUNNING("tcp server is not running");

	public static Status getStatusByCode(int code) {
		for (Status status : Status.values())
			if (status.getCode() == code)
				return (status);
		return (null);
	}

	private int		code;

	private String	message;

	Status(int code, String message) {
		this.code = code;
		this.message = message;
	}

	Status(String message) {
		this(Integer.MIN_VALUE, message);
	}

	public int getCode() {
		if (this.code == Integer.MIN_VALUE)
			return (this.ordinal());
		return (this.code);
	}

	public String getMessage() {
		return (this.message);
	}
}