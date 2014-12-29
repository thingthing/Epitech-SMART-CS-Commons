package eip.smart.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.concurrent.Executors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;

import eip.smart.util.WrapperTypes;

public class SmartAPIRequest {

	public static void setTimeouts(long connectionTimeout, long socketTimeout) {
		Unirest.setTimeouts(connectionTimeout, socketTimeout);
	}

	public static boolean			FORCE_SYNCHRONOUS	= false;
	public static String			DEFAULT_SERVER_URL	= "http://localhost:8080/smartserver/";
	private String					serverUrl			= SmartAPIRequest.DEFAULT_SERVER_URL;
	private String					request;

	private HashMap<String, Object>	data;

	public SmartAPIRequest(String request) {
		this(request, new HashMap<>());
	}

	public SmartAPIRequest(String request, HashMap<String, Object> data) {
		this.request = request;
		this.data = data;
	}

	public SmartAPIRequest addData(String key, Object value) {
		this.data.put(key, value);
		return (this);
	}

	public String getUrl() {
		if (!this.serverUrl.endsWith("/"))
			this.serverUrl += "/";
		if (!(this.serverUrl.startsWith("http://") || this.serverUrl.startsWith("https://")))
			this.serverUrl = "http://" + this.serverUrl;
		return (this.serverUrl + this.request);
	}

	public void run(SmartAPIRequestCallback callback) {
		try {
			GetRequest req = Unirest.get(this.getUrl()).header("accept", "application/json");
			Iterator<Entry<String, Object>> iterator = this.data.entrySet().iterator();
			Entry<String, Object> entry;
			while (iterator.hasNext()) {
				entry = iterator.next();
				if (entry.getValue().getClass().isPrimitive() || WrapperTypes.isWrapperType(entry.getValue().getClass()) || entry.getValue().getClass() == String.class)
					req.queryString(entry.getKey(), entry.getValue());
				else
					req.queryString(entry.getKey(), new ObjectMapper().writeValueAsString(entry.getValue()));
			}
			HttpResponse<JsonNode> response = req.asJson();
			if (response.getStatus() != 200 && callback != null)
				callback.onFail(new Exception(response.getStatusText()));
			else if (callback != null)
				callback.onSuccess(new SmartAPIResponse(this, response.getRawBody()));
		} catch (Exception e) {
			callback.onFail(e);
		}
	}

	public void runAsync(SmartAPIRequestCallback callback) {
		if (SmartAPIRequest.FORCE_SYNCHRONOUS)
			this.run(callback);
		else
			Executors.newSingleThreadExecutor().submit(new Runnable() {
				@Override
				public void run() {
					SmartAPIRequest.this.run(callback);
				}
			});
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

}
