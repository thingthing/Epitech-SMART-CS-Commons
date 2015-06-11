package eip.smart.model;

import eip.smart.model.geometry.Point;

public enum AgentMessageReceptor {
	POSITION("position", new AgentMessageHandler<Point>(Point.class) {
		@Override
		public void handleMessage(Point data, Agent agent) {
			agent.setCurrentPosition(data);
			agent.sendStatus(0, "ok");
		}
	}),
	STATE("state", new AgentMessageHandler<Integer>(Integer.class) {
		@Override
		public void handleMessage(Integer data, Agent agent) {
			// @ TODO Handle State here.
			agent.sendStatus(0, "ok but do nothing atm");
		}
	});

	private String					key;
	private AgentMessageHandler<?>	handler;

	private AgentMessageReceptor(String key, AgentMessageHandler<?> handler) {
		this.key = key;
		this.handler = handler;
	}

	/**
	 * @return the handler
	 */
	public AgentMessageHandler<?> getHandler() {
		return this.handler;
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return this.key;
	}

}
