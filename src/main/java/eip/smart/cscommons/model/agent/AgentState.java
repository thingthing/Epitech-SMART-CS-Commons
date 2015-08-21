package eip.smart.cscommons.model.agent;

/**
 * <b>AgentState is the enum allowing the management of the Agents'states.</b>
 *
 * @author Pierre Demessence
 * @version 1.0
 */
public enum AgentState {
	BLOCKED,
	DERANGED,
	LOST_SIGNAL,
	LOW_BATTERY,
	NO_BATTERY,
	NO_SIGNAL,
	OK,
	RECALL,
	RECALL_BATTERY,
	RECALL_ERROR,
	STILL,
	STILL_ERROR,
	UNKNOWN_ERROR;
}