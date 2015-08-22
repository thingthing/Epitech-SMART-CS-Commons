package eip.smart.cscommons.model;

import java.util.ArrayList;
import java.util.MissingFormatArgumentException;

/**
 * <b>Status is the enum listing the different status and messages associated.</b>
 *
 * @author Pierre Demessence
 *
 */
public enum ServerStatus {
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
		for (ServerStatus s : ServerStatus.values())
			if (s.getCode() == Integer.MIN_VALUE)
				s.code = ServerStatus.next_code++;
	}

	/**
	 * Take as argument status'code and return the status
	 *
	 * @param code
	 *            int, the status'code
	 * @return Status, the parameter code's corresponding status
	 */
	public static ServerStatus getStatusByCode(int code) {
		for (ServerStatus status : ServerStatus.values())
			if (status.getCode() == code)
				return (status);
		return (null);
	}

	private int					code;
	private String				message;
	private ArrayList<String>	objects	= new ArrayList<>();

	/**
	 * Constructor taking as parameter a code and a message
	 *
	 * @param code
	 * @param message
	 */
	ServerStatus(int code, String message) {
		this.code = code;
		this.message = message;
	}

	/**
	 * Constructor taking as parameter a message
	 *
	 * @param message
	 */
	ServerStatus(String message) {
		this(Integer.MIN_VALUE, message);
	}

	/**
	 * Add string to be used in advanced message formating.
	 *
	 * @param os
	 *            some strings.
	 * @return The instance.
	 */
	public ServerStatus addObjects(String... os) {
		this.objects.clear();
		for (String o : os)
			this.objects.add(o);
		return (this);
	}

	/**
	 * Get the code.
	 *
	 * @return the code.
	 */
	public int getCode() {
		return (this.code);
	}

	/**
	 * Get the message.
	 *
	 * @return the message.
	 */
	public String getMessage() {
		String message = this.message;
		try {
			message = String.format(this.message, this.objects.toArray());
		} catch (MissingFormatArgumentException e) {}
		return (message);
	}

	/**
	 * Set the message.
	 *
	 * @param message
	 *            the message to set.
	 */
	public void setMessage(String message) {
		this.message = message;
	}
}