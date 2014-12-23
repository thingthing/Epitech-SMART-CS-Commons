package eip.smart.api;

import eip.smart.util.Pair;

public class SmartAPIResponse {
	private SmartAPIRequest	request;

	public SmartAPIResponse(SmartAPIRequest request) {
		this.request = request;
	}

	public Pair<Integer, String> getStatus() {
		Pair<Integer, String> res = new Pair<>(42, "");
		return (res);
	}

}
