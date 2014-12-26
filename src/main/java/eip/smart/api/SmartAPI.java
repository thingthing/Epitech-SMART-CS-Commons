package eip.smart.api;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.ObjectMapper;

import eip.smart.model.Status;
import eip.smart.model.proxy.SimpleModelingProxy;

public class SmartAPI<T> {

	private interface SmartAPIInnerCallback<T> {
		public abstract T onPreSuccess(SmartAPIResponse res);
	}

	public static SmartAPI<Status> modelingCreate(String name) {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_create", "name", name), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<Status> modelingDelete(String name) {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_delete", "name", name), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<ArrayList<SimpleModelingProxy>> modelingList() {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_list"), new SmartAPIInnerCallback<ArrayList<SimpleModelingProxy>>() {
			@Override
			public ArrayList<SimpleModelingProxy> onPreSuccess(SmartAPIResponse res) {
				ArrayList<SimpleModelingProxy> simpleModelingProxies = new ArrayList<>();
				try {
					ObjectMapper mapper = new ObjectMapper();
					for (int i = 0; i < res.getData()
							.findValue("modelings")
							.size(); i++)
						simpleModelingProxies.add(mapper.readValue(res.getData()
								.findValue("modelings")
								.get(i)
								.toString(), SimpleModelingProxy.class));
				} catch (IOException e) {
					e.printStackTrace();
				}
				return (simpleModelingProxies);
			}
		}));
	}

	public static SmartAPI<Status> modelingStop() {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_stop"), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	// public static String SERVER_URL = "http://54.148.17.11:8080/smartserver/";
	public static String				SERVER_URL	= "http://localhost:8080/smartserver/";
	private SmartAPIRequest				request;
	private SmartAPIInnerCallback<T>	preCallback;

	private SmartAPI(SmartAPIRequest request, SmartAPIInnerCallback<T> preCallback) {
		this.request = request;
		this.request.setServerUrl(SmartAPI.SERVER_URL);
		this.preCallback = preCallback;
	}

	private SmartAPIRequestCallback MUST_FIND_A_NAME(SmartAPICallback<T> callback) {
		return (new SmartAPIRequestCallback() {

			@Override
			public void onFail(Exception e) {
				if (callback != null)
					callback.onFail(e);
			}

			@Override
			public void onSuccess(SmartAPIResponse res) {
				if (res.getStatus()
						.getCode() != 0) {
					if (callback != null)
						callback.onError(res.getStatus());
				} else if (callback != null)
					callback.onSuccess(SmartAPI.this.preCallback.onPreSuccess(res));
			}
		});
	}

	public void run(SmartAPICallback<T> callback) {
		this.request.run(this.MUST_FIND_A_NAME(callback));
	}

	public void runAsync(SmartAPICallback<T> callback) {
		this.request.runAsync(this.MUST_FIND_A_NAME(callback));
	}

}
