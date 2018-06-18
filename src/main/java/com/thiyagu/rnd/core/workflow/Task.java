package com.thiyagu.rnd.core.workflow;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public abstract class Task {

	private final NodeInfo nodeInfo;

	private Set<Task> incomingEdges;

	private Set<Task> outgoingEdges;

	public Task(String name, String uuid) {
		nodeInfo = new NodeInfo(name, uuid);
		incomingEdges = new HashSet<>();
		outgoingEdges = new HashSet<>();
	}

	public NodeInfo getNodeInfo() {
		return nodeInfo;
	}

	public void addIncomingEdges(Task incomingEdgeNode) {
		incomingEdges.add(incomingEdgeNode);
	}

	public void addOutgoingEdges(Task outgoingEdgeNode) {
		outgoingEdges.add(outgoingEdgeNode);
	}

	public Set<Task> getOutGoingEdges() {
		return Collections.unmodifiableSet(this.outgoingEdges);
	}
	
	public Set<Task> getIncomingEdges(){
		return Collections.unmodifiableSet(this.incomingEdges);
	}
	
	public abstract void execute(WorkflowContext request);
}

class NodeInfo {

	private final String name;

	private final String uuid;

	protected NodeInfo(String name, String uuid) {
		super();
		this.name = name;
		this.uuid = uuid;
	}

	public String getUuid() {
		return uuid;
	}

	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "NodeInfo [uuid=" + uuid + ", name=" + name + "]";
	}

}
