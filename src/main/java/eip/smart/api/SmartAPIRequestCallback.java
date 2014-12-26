package eip.smart.api;


public interface SmartAPIRequestCallback {

	public void onFail(Exception e);

	public void onSuccess(SmartAPIResponse res);

}
