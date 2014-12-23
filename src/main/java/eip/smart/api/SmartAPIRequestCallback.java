package eip.smart.api;

import java.io.IOException;

public interface SmartAPIRequestCallback {

	public void onFail(IOException e);

	public void onSuccess(SmartAPIResponse res);

}
