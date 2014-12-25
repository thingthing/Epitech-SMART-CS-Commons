package eip.smart.api;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Test;

@SuppressWarnings("static-method")
public class SmartAPIRequestTest {

	@Test
	public void testAPIAsynchronousRequest() {
		SmartAPIRequest.FORCE_SYNCHRONOUS = false;
		SmartAPIRequest req = new SmartAPIRequest("connect");
		req.runAsync(new SmartAPIRequestCallback() {

			@Override
			public void onFail(IOException e) {
				Assert.fail("Not asynchrone");
			}

			@Override
			public void onSuccess(SmartAPIResponse res) {
				Assert.fail("Not asynchrone");
			}
		});
		Assert.assertTrue(true);
	}

	/*
	@Test
	public void testAPIBadConnection() {
		SmartAPIRequest req = new SmartAPIRequest("connect", "login", "badlogin", "password", "badpassword");
		req.run(new SmartAPIRequestCallback() {

			@Override
			public void onFail(IOException e) {
				Assert.fail(e.toString());
			}

			@Override
			public void onSuccess(SmartAPIResponse res) {
				Assert.assertTrue(res.getMessage(), res.getStatus() != 0 && res.getMessage().equals("Invalid credentials"));
			}
		});
	}

	@Test
	public void testAPIGoodConnection() {
		SmartAPIRequest req = new SmartAPIRequest("connect", "login", CorniTest.JUNIT_USER, "password", CorniTest.JUNIT_PASS);
		req.run(new SmartAPIRequestCallback() {

			@Override
			public void onFail(IOException e) {
				Assert.fail(e.toString());
			}

			@Override
			public void onSuccess(SmartAPIResponse res) {
				Assert.assertTrue(res.getMessage(), res.getStatus() == 0);
			}
		});
	}
	 */

	@Test
	public void testAPIResponse() {
		SmartAPIRequest req = new SmartAPIRequest("connect");
		req.run(new SmartAPIRequestCallback() {

			@Override
			public void onFail(IOException e) {
				Assert.fail(e.toString());
			}

			@Override
			public void onSuccess(SmartAPIResponse res) {
				Assert.assertTrue(res.getStatus().getFirst() != -1 && !res.getStatus().getSecond().isEmpty());
			}
		});
	}

	@Test
	public void testAPIUrlConstruction() {
		String url = "test";
		String fookey = "fookey";
		String foo = "foo";
		String barkey = "barkey";
		String bar = "bar";
		SmartAPIRequest req = new SmartAPIRequest(url, fookey, foo, barkey, bar);
		Assert.assertTrue(req.dataStringify().equals(fookey + "=" + foo + "&" + barkey + "=" + bar));
		Assert.assertTrue(req.getUrl().equals(SmartAPIRequest.SERVER_URL + url + "?" + fookey + "=" + foo + "&" + barkey + "=" + bar));
	}

	@Test
	public void testAPIWrongUrl() {
		SmartAPIRequest req = new SmartAPIRequest("wrongurl");
		req.run(new SmartAPIRequestCallback() {
			@Override
			public void onFail(IOException e) {
				Assert.assertTrue(e.toString().equals("java.io.FileNotFoundException: http://54.68.180.234:8080/server_war/wrongurl?"));
			}

			@Override
			public void onSuccess(SmartAPIResponse res) {
				Assert.fail();
			}
		});

	}

}
