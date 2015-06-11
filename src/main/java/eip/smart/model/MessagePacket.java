package eip.smart.model;

import java.util.ArrayList;

import eip.smart.util.Pair;

public class MessagePacket {
	private int								statusCode		= 0;
	private String							statusMessage	= "";
	private ArrayList<Pair<String, Object>>	data			= new ArrayList<>();

	public MessagePacket() {}

	/**
	 * @param key
	 *            the key to add
	 * @param value
	 *            the object to add
	 */
	public MessagePacket addObject(String key, Object value) {
		this.data.add(new Pair<>(key, value));
		return (this);
	}

	/**
	 * @return the data
	 */
	public ArrayList<Pair<String, Object>> getData() {
		return this.data;
	}

	/**
	 * @return the statusCode
	 */
	public int getStatusCode() {
		return this.statusCode;
	}

	/**
	 * @return the statusMessage
	 */
	public String getStatusMessage() {
		return this.statusMessage;
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 * @param statusMessage
	 *            the statusMessage to set
	 */
	public MessagePacket setStatus(int statusCode, String statusMessage) {
		this.statusCode = statusCode;
		this.statusMessage = statusMessage;
		return (this);
	}

	/**
	 * @param statusCode
	 *            the statusCode to set
	 */
	public MessagePacket setStatusCode(int statusCode) {
		this.statusCode = statusCode;
		return (this);
	}

	/**
	 * @param statusMessage
	 *            the statusMessage to set
	 */
	public MessagePacket setStatusMessage(String statusMessage) {
		this.statusMessage = statusMessage;
		return (this);
	}

}
