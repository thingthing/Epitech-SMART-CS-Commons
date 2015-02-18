package eip.smart.model;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

abstract class AgentMessageHandler<T> {

	Class<T>	type;

	public AgentMessageHandler(Class<T> type) {
		this.type = type;
	}

	public void handleMessage(String data, Agent agent) throws JsonParseException, JsonMappingException, IOException {
		this.handleMessage(new ObjectMapper().readValue(data, this.type), agent);
	}

	public abstract void handleMessage(T data, Agent agent);
}