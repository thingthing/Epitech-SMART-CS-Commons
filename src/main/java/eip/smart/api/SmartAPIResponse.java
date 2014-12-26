package eip.smart.api;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import eip.smart.model.Status;

public class SmartAPIResponse {
	private SmartAPIRequest	request;
	private JsonNode		body;

	public SmartAPIResponse(SmartAPIRequest request, InputStream inputStream) {
		this.request = request;
		try {
			this.body = new ObjectMapper().readTree(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public JsonNode getData() {
		return (this.body.findValue("data"));
	}

	public Status getStatus() {
		return (Status.getStatusByCode(this.body.findValue("status")
				.findValue("code")
				.asInt()));
	}

	public String getUrl() {
		return this.request.getUrl();
	}
}
