package eip.smart.api;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import eip.smart.model.Agent;
import eip.smart.model.Area;
import eip.smart.model.Modeling;
import eip.smart.model.Status;
import eip.smart.model.geometry.Point;
import eip.smart.model.proxy.SimpleAgentProxy;
import eip.smart.model.proxy.SimpleModelingProxy;
import eip.smart.model.proxy.StatusProxy;

public class SmartAPI<T> {

	private interface SmartAPIInnerCallback<T> {
		public abstract T onPreSuccess(SmartAPIResponse res);
	}

	public static SmartAPI<Status> addArea(Area area) {
		return (new SmartAPI<>(new SmartAPIRequest("add_area").addData("area", area), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<Status> agentAdd(int ID) {
		return (new SmartAPI<>(new SmartAPIRequest("agent_add").addData("id", ID), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<Status> agentRecall(int ID) {
		return (new SmartAPI<>(new SmartAPIRequest("agent_recall").addData("id", ID), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<Status> agentRemove(int ID) {
		return (new SmartAPI<>(new SmartAPIRequest("agent_remove").addData("id", ID), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<Agent> getAgentInfo(int ID) {
		return (new SmartAPI<>(new SmartAPIRequest("get_agent_info").addData("id", ID), new SmartAPIInnerCallback<Agent>() {
			@Override
			public Agent onPreSuccess(SmartAPIResponse res) {
				return (SmartAPI.parseJSONObject(Agent.class, res.getData().findValue("agent")));
			}
		}));
	}

	public static SmartAPI<ArrayList<SimpleAgentProxy>> getAgents() {
		return (new SmartAPI<>(new SmartAPIRequest("get_agents"), new SmartAPIInnerCallback<ArrayList<SimpleAgentProxy>>() {
			@Override
			public ArrayList<SimpleAgentProxy> onPreSuccess(SmartAPIResponse res) {
				return (SmartAPI.parseJSONArray(SimpleAgentProxy.class, res.getData().findValue("agents")));
			}
		}));
	}

	public static SmartAPI<ArrayList<SimpleAgentProxy>> getAgentsAvailable() {
		return (new SmartAPI<>(new SmartAPIRequest("get_agents_available"), new SmartAPIInnerCallback<ArrayList<SimpleAgentProxy>>() {
			@Override
			public ArrayList<SimpleAgentProxy> onPreSuccess(SmartAPIResponse res) {
				return (SmartAPI.parseJSONArray(SimpleAgentProxy.class, res.getData().findValue("agents")));
			}
		}));
	}

	public static SmartAPI<ArrayList<Point>> getPoints() {
		return (new SmartAPI<>(new SmartAPIRequest("get_points"), new SmartAPIInnerCallback<ArrayList<Point>>() {

			@Override
			public ArrayList<Point> onPreSuccess(SmartAPIResponse res) {
				return (SmartAPI.parseJSONArray(Point.class, res.getData().findValue("points")));
			}
		}));
	}

	public static SmartAPI<ArrayList<StatusProxy>> listStatus() {
		return (new SmartAPI<>(new SmartAPIRequest("list_status"), new SmartAPIInnerCallback<ArrayList<StatusProxy>>() {
			@Override
			public ArrayList<StatusProxy> onPreSuccess(SmartAPIResponse res) {
				return (SmartAPI.parseJSONArray(StatusProxy.class, res.getData().findValue("statuses")));
			}
		}));
	}

	public static SmartAPI<Status> manualOrder(int ID, Point order) {
		return (new SmartAPI<>(new SmartAPIRequest("manual_order").addData("id", ID).addData("order", order), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<Status> modelingCreate(String name) {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_create").addData("name", name), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<Status> modelingDelete(String name) {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_delete").addData("name", name), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<Modeling> modelingInfo() {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_info"), new SmartAPIInnerCallback<Modeling>() {
			@Override
			public Modeling onPreSuccess(SmartAPIResponse res) {
				return (SmartAPI.parseJSONObject(Modeling.class, res.getData().findValue("modeling")));
			}
		}));
	}

	public static SmartAPI<ArrayList<SimpleModelingProxy>> modelingList() {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_list"), new SmartAPIInnerCallback<ArrayList<SimpleModelingProxy>>() {
			@Override
			public ArrayList<SimpleModelingProxy> onPreSuccess(SmartAPIResponse res) {
				return (SmartAPI.parseJSONArray(SimpleModelingProxy.class, res.getData().findValue("modelings")));
			}
		}));
	}

	public static SmartAPI<Status> modelingLoad(String name) {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_load").addData("name", name), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<Status> modelingPause() {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_pause"), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<Status> modelingResume() {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_resume"), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<Status> modelingSave() {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_save"), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
			}
		}));
	}

	public static SmartAPI<Status> modelingStart() {
		return (new SmartAPI<>(new SmartAPIRequest("modeling_start"), new SmartAPIInnerCallback<Status>() {
			@Override
			public Status onPreSuccess(SmartAPIResponse res) {
				return (res.getStatus());
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

	private static <E> ArrayList<E> parseJSONArray(Class<E> theClass, JsonNode JSONarray) {
		ArrayList<E> array = new ArrayList<>();
		for (int i = 0; i < JSONarray.size(); i++)
			array.add(SmartAPI.parseJSONObject(theClass, JSONarray.get(i)));
		return (array);
	}

	private static <E> E parseJSONObject(Class<E> theClass, JsonNode JSONObject) {
		E object = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			object = mapper.readValue(JSONObject.toString(), theClass);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return (object);
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
				if (res.getStatus().getCode() > 0) {
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
