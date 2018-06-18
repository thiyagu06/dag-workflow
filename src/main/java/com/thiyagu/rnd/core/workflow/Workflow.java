package com.thiyagu.rnd.core.workflow;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

/**
 * TODO : Add validation to detect cycle in the loop, throw exception 
 * TODO : Add validation to detect self loop, throw exception or not added the nodes 
 * TODO : Replace for loop with for each and try improve the logic with fp paradigm
 * TODO : Eager computation of root vertices, indegree vertex count, leaf nodes
 * TODO : Maintain state of graph, so that nodes are not added dynamically added
 * 		  after the initialization
 * 
 * @author thiyagu
 *
 */
public class Workflow {

	private final String name;

	private boolean active;

	private Map<NodeInfo, Task> nodes;

	public Workflow(String name) {
		this.name = name;
		active = true;
		nodes = new HashMap<>();
	}

	public void addAsDependentOnAllLeafNodes(final Task nodeValue) {
		if (this.size() == 0) {
			addIndependent(nodeValue);
		} else {
			for (Task node : this.getLeafNodes()) {
				addDependency(node, nodeValue);
			}
		}
	}

	public void addDependency(final Task evalFirstNode, final Task evalLaterNode) {
		Task firstNode = addOrGet(evalFirstNode);
		Task afterNode = addOrGet(evalLaterNode);

		addEdges(firstNode, afterNode);
	}

	private void addEdges(final Task firstNode, final Task afterNode) {
		if (!firstNode.equals(afterNode)) {
			firstNode.addOutgoingEdges(afterNode);
			afterNode.addIncomingEdges(firstNode);
		}
	}

	public Set<Task> getLeafNodes() {
		Set<Task> leafNodes = new LinkedHashSet<Task>();
		for (Entry<NodeInfo, Task> entry : this.nodes.entrySet()) {
			Task node = entry.getValue();
			if (node.getOutGoingEdges().isEmpty()) {
				leafNodes.add(node);
			}
		}
		return leafNodes;
	}

	public void addIndependent(final Task nodeValue) {
		addOrGet(nodeValue);
	}

	private Task addOrGet(final Task nodeValue) {
		Task graphNode = null;
		if (this.nodes.containsKey(nodeValue.getNodeInfo())) {
			graphNode = this.nodes.get(nodeValue.getNodeInfo());
		} else {
			graphNode = nodeValue;
			this.nodes.put(nodeValue.getNodeInfo(), nodeValue);
		}
		return graphNode;
	}

	public Set<Task> getInitialNodes() {
		Set<Task> initialNodes = new LinkedHashSet<>();
		for (Entry<NodeInfo, Task> entry : this.nodes.entrySet()) {
			Task node = entry.getValue();
			if (node.getIncomingEdges().isEmpty()) {
				initialNodes.add(node);
			}
		}
		return Collections.unmodifiableSet(initialNodes);
	}

	public Map<Task, Integer> getNodeIndegreeCount() {
		Map<Task, Integer> indegreeNodesCount = new HashMap<>();
		for (Entry<NodeInfo, Task> entry : this.nodes.entrySet()) {
			Task node = entry.getValue();
			if (!node.getIncomingEdges().isEmpty()) {
				indegreeNodesCount.put(node, node.getIncomingEdges().size());
			}
		}
		return indegreeNodesCount;
	}

	public Set<Task> getAllLeafNodes() {
		Set<Task> leafNodes = new LinkedHashSet<>();
		for (Entry<NodeInfo, Task> entry : this.nodes.entrySet()) {
			Task node = entry.getValue();
			if (node.getOutGoingEdges().isEmpty()) {
				leafNodes.add(node);
			}
		}
		return Collections.unmodifiableSet(leafNodes);
	}

	public int size() {
		return this.nodes.size();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the active
	 */
	public boolean isStatus() {
		return active;
	}

	/**
	 * @return the nodes
	 */
	public Map<NodeInfo, Task> getNodes() {
		return Collections.unmodifiableMap(nodes);
	}
}
