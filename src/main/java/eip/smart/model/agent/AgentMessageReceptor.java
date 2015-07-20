package eip.smart.model.agent;

import eip.smart.model.geometry.Point;

public enum AgentMessageReceptor {
	POSITION("position", new AgentMessageHandler<Point>(Point.class) {
		@Override
		public void handleMessage(Point data, Agent agent) {
			agent.setCurrentPosition(data);
			agent.sendStatus(0, "ok");
		}
	}),
	STATE("state", new AgentMessageHandler<String>(String.class) {
		@Override
		public void handleMessage(String data, Agent agent) {
			for (AgentState as : AgentState.values())
				if (as.name().equals(data)) {
					agent.setState(as);
					agent.sendStatus(0, "ok");
					return;
				}
			agent.sendStatus(1, "unknown state");
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
