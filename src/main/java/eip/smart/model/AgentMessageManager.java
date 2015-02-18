package eip.smart.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

public class AgentMessageManager {
	private HashMap<String, AgentMessageHandler<?>>	handlers	= new HashMap<>();
	private char									separator	= ':';

	public void addHandler(String key, AgentMessageHandler<?> handler) {
		this.handlers.put(key, handler);
	}

	public void handleMessage(String msg, Agent agent) throws JsonParseException, JsonMappingException, IOException {
		if (msg.indexOf(this.separator) == -1) {
			agent.sendMessage("error: no command");
			return;
		}
		String key = msg.substring(0, msg.indexOf(this.separator));
		String data = msg.substring(msg.indexOf(this.separator) + 1, msg.length());
		for (Entry<String, AgentMessageHandler<?>> entry : this.handlers.entrySet())
			if (entry.getKey().equals(key)) {
				entry.getValue().handleMessage(data, agent);
				return;
			}
		agent.sendMessage("error: unknown command");
	}
}