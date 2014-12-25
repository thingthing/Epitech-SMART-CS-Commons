package eip.smart.api;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import eip.smart.util.Pair;

public class SmartAPIRequest {

	public static String					API_URL				= "http://www.perdu.com";
	public static boolean					FORCE_SYNCHRONOUS	= false;

	private String							request;
	private ArrayList<Pair<String, String>>	data;

	public SmartAPIRequest(String request) {
		this(request, new ArrayList<>());
	}

	public SmartAPIRequest(String request, ArrayList<Pair<String, String>> data) {
		this.request = request;
		this.data = data;
	}

	public SmartAPIRequest(String request, String... data) {
		this(request);
		int i;
		for (i = 0; i < data.length - 1; i += 2)
			this.data.add(new Pair<>(data[i], data[i + 1]));
		if (i < data.length)
			this.data.add(new Pair<>(data[i], ""));
	}

	public String dataStringify() {
		ArrayList<String> elements = new ArrayList<>();

		for (Pair<String, String> pair : this.data)
			try {
				elements.add(pair.getFirst() + "=" + URLEncoder.encode(pair.getSecond(), "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		return (String.join("&", elements));
	}

	public String getUrl() {
		return (SmartAPIRequest.API_URL + this.request + "?" + this.dataStringify());
	}

	public void run(SmartAPIRequestCallback callback) {
		/*
		HttpConnection http = null;
		try {
			http = new HttpConnection(this.getUrl());
		} catch (IOException e) {
			if (callback != null)
				callback.onError(e);
			return;
		}

		try {
			if (callback != null)
				callback.onSuccess(new SmartAPIResponse(Json.createReader(http.open()).readObject(), this));
		} catch (IOException e) {
			if (callback != null)
				callback.onError(e);
		}
		 */
	}

	public void runAsync(SmartAPIRequestCallback callback) {
		/*
		if (SmartAPIRequest.FORCE_SYNCHRONOUS)
			this.run(callback);
		else
			Executors.newSingleThreadExecutor().submit(new Runnable() {
				@Override
				public void run() {
					SmartAPIRequest.this.run(callback);
				}
			});
		 */
	}

}
