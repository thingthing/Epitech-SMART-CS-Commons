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
	AGENT_ALREADY_ADDED("agent already added to current modeling"),
	AGENT_NOT_ADDED("agent not in current modeling"),
	DUPLICATE("%s with %s %s already exists"),
	ERR_REMOVED(-4, "SERVLET REMOVED"),
	ERR_SIMULATION(-2, "SIMULATION"),
	ERR_TODO(-1, "TODO"),
	ERR_UNKNOWN(-3, "UNKOWN ERROR : %s"),
	MISSING_PARAMETER("missing parameter %s"),
	MODELING_ALREADY_CURRENT("a modeling is already loaded"),
	MODELING_NO_CURRENT("no current modeling"),
	MODELING_STATE_ERROR("%s"),
	NOT_FOUND("%s with %s %s not found"),
	OK(0, "ok"),
	SOCKET_ERROR("Socket error : %s");

	private static int	next_code	= 1;

	static {
		for (Status s : Status.values())
			if (s.getCode() == Integer.MIN_VALUE)
				s.code = Status.next_code++;
	}

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

	public Status addObjects(Object... os) {
		this.objects.clear();
		for (Object o : os)
			this.objects.add(o);
		return (this);
	}

	public int getCode() {
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