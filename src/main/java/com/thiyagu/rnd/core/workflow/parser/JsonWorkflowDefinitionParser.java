package com.thiyagu.rnd.core.workflow.parser;

import java.io.IOException;
import java.io.Reader;
import java.util.Objects;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thiyagu.rnd.core.exception.FlowExecutionException;
import com.thiyagu.rnd.core.task.WorkflowTaskProvider;
import com.thiyagu.rnd.core.workflow.Task;
import com.thiyagu.rnd.core.workflow.Workflow;
import com.thiyagu.rnd.core.workflow.definiton.GraphEdge;
import com.thiyagu.rnd.core.workflow.definiton.GraphNode;

public class JsonWorkflowDefinitionParser implements WorkflowDefinitionParser {

	private final String fileExtension;
	
	private ObjectMapper objectMapper;
	
	private final WorkflowTaskProvider workflowTaskProvider;

	public JsonWorkflowDefinitionParser(String fileExtension,WorkflowTaskProvider workflowTaskProvider) {
		this.fileExtension = fileExtension;
		this.workflowTaskProvider = workflowTaskProvider;
	}

	public JsonWorkflowDefinitionParser(WorkflowTaskProvider workflowTaskProvider) {
		this(DEFAULT_EXT,workflowTaskProvider);
		this.objectMapper = new ObjectMapper();
	}

	@Override
	public Workflow parse(Reader stream, String name) throws FlowExecutionException {
		Objects.requireNonNull(stream);
		GraphNode graphNode;
		try {
			graphNode = objectMapper.readValue(stream, GraphNode.class);
		} catch (IOException e) {
			throw new FlowExecutionException("Can Create Workflow");
		}
		if (StringUtils.isEmpty(graphNode.getName())) {
			graphNode.setName(UUID.randomUUID().toString());
		}
		return createWorkflowFromDefinition(graphNode);
	}

	private Workflow createWorkflowFromDefinition(GraphNode graphNode) {
		Workflow workflow = new Workflow(graphNode.getName());
		for (GraphEdge edge : graphNode.getWorkflowEdges()) {
			Task currentNode = null;
			Task dependentNode = null;
			if (edge.getDependent() == null) {
				currentNode = workflowTaskProvider.getTask(edge.getTaskName());
				workflow.addIndependent(currentNode);
			} else {
				currentNode = workflowTaskProvider.getTask(edge.getTaskName());
				dependentNode = workflowTaskProvider.getTask(edge.getDependent());
				workflow.addDependency(currentNode, dependentNode);
			}
		}
		return workflow;
	}

	@Override
	public String getFileExt() {
		return this.fileExtension;
	}

}
