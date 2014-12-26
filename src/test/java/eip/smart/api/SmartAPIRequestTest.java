package eip.smart.api;

import java.io.IOException;
import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import eip.smart.model.proxy.SimpleModelingProxy;

@SuppressWarnings("static-method")
public class SmartAPIRequestTest {

	@Test
	public void testAPIAsynchronousRequest() {
		SmartAPIRequest.FORCE_SYNCHRONOUS = false;
		SmartAPIRequest req = new SmartAPIRequest("connect");
		req.runAsync(new SmartAPIRequestCallback() {

			@Override
			public void onFail(Exception e) {
				Assert.fail("Not asynchronous");
			}

			@Override
			public void onSuccess(SmartAPIResponse res) {
				Assert.fail("Not asynchronous");
			}
		});
		Assert.assertTrue(true);
	}

	@Test
	public void testAPIResponse() {
		SmartAPIRequest req = new SmartAPIRequest("modeling_list");
		req.run(new SmartAPIRequestCallback() {

			@Override
			public void onFail(Exception e) {
				if (!e.getMessage()
						.startsWith("org.apache.http.conn.HttpHostConnectException: Connect to localhost:8080"))
					Assert.fail();
			}

			@Override
			public void onSuccess(SmartAPIResponse res) {
				if (res.getStatus()
						.getCode() != 0)
					Assert.fail();
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
			}
		});
	}

	@Test
	public void testAPIWrongRequest() {
		SmartAPIRequest req = new SmartAPIRequest("wrongurl");
		req.run(new SmartAPIRequestCallback() {

			@Override
			public void onFail(Exception e) {
				Assert.assertTrue(true);
			}

			@Override
			public void onSuccess(SmartAPIResponse res) {
				Assert.fail();
			}
		});
	}

	@Test
	public void testAPIWrongUrl() {
		SmartAPIRequest req = new SmartAPIRequest("modeling_list");
		req.setServerUrl("toto");
		req.run(new SmartAPIRequestCallback() {

			@Override
			public void onFail(Exception e) {
				Assert.assertTrue(true);
			}

			@Override
			public void onSuccess(SmartAPIResponse res) {
				Assert.fail();
			}
		});
	}

}
