package eip.smart.api;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.mashape.unirest.http.Unirest;

@SuppressWarnings("static-method")
public class SmartAPITest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		SmartAPI.SERVER_URL = "http://localhost:8080/smartserver/";
		Unirest.setTimeouts(1000, 1000);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {}

	@Before
	public void setUp() throws Exception {}

	@After
	public void tearDown() throws Exception {}

	@Test
	public void testModelingCreate() {
		/*
		SmartAPI.modelingCreate("testjunit2").run(null);
		SmartAPI.modelingStop().run(null);
		SmartAPI.modelingCreate("testjunit").run(new SmartAPICallback<Status>() {

			@Override
			public void onError(Status s) {
				System.out.println(s);
			}

			@Override
			public void onFail(Exception e) {
				System.out.println(e);
			}

			@Override
			public void onSuccess(Status t) {
				System.out.println("Cr�ation :");
				System.out.println(t);
			}
		});
		SmartAPI.modelingList().run(new SmartAPICallback<ArrayList<SimpleModelingProxy>>() {

			@Override
			public void onError(Status s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFail(Exception e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<SimpleModelingProxy> t) {
				System.out.println("Liste :");
				System.out.println(t);
			}
		});
		SmartAPI.getAgentInfo(1).run(new SmartAPICallback<Agent>() {

			@Override
			public void onError(Status s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFail(Exception e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Agent t) {
				System.out.println("Agent 1 :");
				System.out.println(t);
			}
		});
		SmartAPI.agentAdd(1).run(null);
		SmartAPI.getAgents().run(new SmartAPICallback<ArrayList<SimpleAgentProxy>>() {

			@Override
			public void onError(Status s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFail(Exception e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<SimpleAgentProxy> t) {
				System.out.println("Agents :");
				System.out.println(t);
			}
		});
		SmartAPI.agentRemove(1).run(null);
		SmartAPI.getAgents().run(new SmartAPICallback<ArrayList<SimpleAgentProxy>>() {

			@Override
			public void onError(Status s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFail(Exception e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<SimpleAgentProxy> t) {
				System.out.println("Agents :");
				System.out.println(t);
			}
		});
		SmartAPI.getPoints().run(new SmartAPICallback<ArrayList<Point>>() {

			@Override
			public void onError(Status s) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onFail(Exception e) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(ArrayList<Point> t) {
				System.out.println("Points :");
				System.out.println(t);
			}
		});
		SmartAPI.modelingStart().run(null);
		SmartAPI.modelingPause().run(null);
		SmartAPI.modelingSave().run(null);
		SmartAPI.modelingResume().run(null);
		SmartAPI.modelingStop().run(null);
		SmartAPI.modelingDelete("testjunit").run(null);
		SmartAPI.getAgentsAvailable().run(new SmartAPICallback<ArrayList<SimpleAgentProxy>>() {

			@Override
			public void onError(Status s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFail(Exception e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<SimpleAgentProxy> t) {
				System.out.println("Available :");
				System.out.println(t);
			}
		});
		SmartAPI.modelingLoad("testjunit2").run(null);
		SmartAPI.modelingStart().run(null);
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SmartAPI.modelingInfo().run(new SmartAPICallback<Modeling>() {

			@Override
			public void onError(Status s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFail(Exception e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(Modeling t) {
				// TODO Auto-generated method stub
				System.out.println("Modeling :");
				System.out.println(t);
				System.out.println("Ticks");
				System.out.println(t.getTick());
			}
		});
		SmartAPI.modelingStop().run(null);
		SmartAPI.listStatus().run(new SmartAPICallback<ArrayList<StatusProxy>>() {

			@Override
			public void onError(Status s) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFail(Exception e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(ArrayList<StatusProxy> t) {
				// TODO Auto-generated method stub
				System.out.println("Statuses :");
				System.out.println(t);
			}
		});
		SmartAPI.modelingLoad("testjunit2").run(null);
		SmartAPI.manualOrder(1, new Point(4)).run(new SmartAPICallback<Status>() {

			@Override
			public void onError(Status s) {
				System.out.println(s);
			}

			@Override
			public void onFail(Exception e) {
				System.out.println(e);
			}

			@Override
			public void onSuccess(Status t) {
				System.out.println(t);
			}
		});

		SmartAPI.addArea(new Area(new Square(new Point(4), 5))).run(new SmartAPICallback<Status>() {

			@Override
			public void onError(Status s) {
				System.out.println(s);
			}

			@Override
			public void onFail(Exception e) {
				System.out.println(e);
			}

			@Override
			public void onSuccess(Status t) {
				System.out.println(t);
			}
		});
		SmartAPI.modelingStop().run(null);
		// SmartAPI.modelingDelete("testjunit2").run(null);
		*/
	}
}
