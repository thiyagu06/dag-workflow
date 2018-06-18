package com.thiyagu.rnd.core.workflow.parser;

import com.thiyagu.rnd.core.exception.FlowExecutionException;
import com.thiyagu.rnd.core.workflow.Workflow;

public interface WorkflowDefinitionLoader {

	/**
	 * Loads workflow by name
	 * 
	 * @param name
	 *            of workflow
	 * @return Workflow
	 * @throws FlowExecutionException
	 *             in case of workflow can not be loaded
	 */
	public Workflow load(final String name) throws FlowExecutionException;

}