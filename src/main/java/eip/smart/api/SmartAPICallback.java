package eip.smart.api;

import eip.smart.model.Status;

public interface SmartAPICallback<T> {
	public void onError(Status s);

	public void onFail(Exception e);

	public void onSuccess(T t);
}
