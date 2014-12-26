package eip.smart.api;

import java.util.HashMap;
import java.util.concurrent.Executors;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;

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

	public SmartAPIRequest(String request, String... data) {
		this(request);
		int i;
		for (i = 0; i < data.length - 1; i += 2)
			this.data.put(data[i], data[i + 1]);
		if (i < data.length)
			this.data.put(data[i], "");
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

			HttpResponse<JsonNode> response = Unirest.get(this.getUrl())
					.header("accept", "application/json")
					.queryString(this.data)
					.asJson();
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
			Executors.newSingleThreadExecutor()
					.submit(new Runnable() {
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
