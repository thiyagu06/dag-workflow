package com.thiyagu.rnd.core.workflow.definiton;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class GraphNode implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private List<GraphEdge> graphEdge;

	public GraphNode() {
		this.graphEdge = new ArrayList<>();
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the graphEdge
	 */
	public List<GraphEdge> getWorkflowEdges() {
		return graphEdge;
	}

	/**
	 * @param graphEdge
	 *            the graphEdge to set
	 */
	public void setWorkflowEdges(List<GraphEdge> graphEdge) {
		this.graphEdge = graphEdge;
	}

}
