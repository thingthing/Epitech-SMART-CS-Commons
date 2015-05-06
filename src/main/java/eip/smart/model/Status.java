package eip.smart.model;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;

/**
 * <b>Status est l'énumération listant les différents status et les messages associés.</b>
 *
 * @author Pierre Demessence
 *
 */
public enum Status {
	AGENT_ALREADY_ADDED("agent already added to modeling"),
	AGENT_NOT_ADDED("agent not already added to modeling"),
	ERR_REMOVED(-4, "SERVLET REMOVED"),
	ERR_SIMULATION(-2, "SIMULATION"),
	ERR_TODO(-1, "TODO"),
	ERR_UNKNOWN(-3, "unknown error"),
	MISSING_PARAMETER("missing parameter %s"),
	MODELING_ALREADY_CURRENT("a modeling is already loaded"),
	MODELING_ALREADY_PAUSED("current modeling is already paused"),
	MODELING_ALREADY_RUNNING("current modeling is already running"),
	MODELING_DUPLICATE_NAME("modeling with given name already exist"),
	MODELING_NO_CURRENT("no current modeling"),
	MODELING_NOT_PAUSED("current modeling is not paused"),
	MODELING_NOT_RUNNING("current modeling is not running"),
	NOT_FOUND("%s with %s %s not found"),
	OK(0, "ok"),
	PORT_ALREADY_USED("port already used"),
	PORT_BAD("given port is bad"),
	SOCKET_ALREADY_RUNNING("tcp server is already running"),
	SOCKET_NOT_RUNNING("tcp server is not running");

	/**
	 * Prends en argument le code du status et retourne le status
	 *
	 * @param code
	 *            int, le code du status
	 * @return Status, le status correspondant au code entré en paramètre
	 */
	public static Status getStatusByCode(int code) {
		for (Status status : Status.values())
			if (status.getCode() == code)
				return (status);
		return (null);
	}

	private int					code;
	private String				message;
	private ArrayList<Object>	objects	= new ArrayList<>();

	/**
	 * Constructeur prenant en paramètre un code et un message
	 *
	 * @param code
	 * @param message
	 */
	Status(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Constructeur prenant en paramètre un message
	 *
	 * @param message
	 */
	Status(String message) {
		this(Integer.MIN_VALUE, message);
	}

	public Status addObject(Object o) {
		this.objects.add(o);
		return (this);
	}

	public int getCode() {
		if (this.code == Integer.MIN_VALUE)
			return (this.ordinal());
		return (this.code);
	}

	public String getMessage() {
		String message = this.message;
		try {
			message = String.format(this.message, this.objects.toArray());
		} catch (MissingFormatArgumentException e) {}
		return (message);
	}
}