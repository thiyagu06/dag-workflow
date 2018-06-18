package com.thiyagu.rnd.core.workflow.execution;

import com.thiyagu.rnd.core.exception.FlowExecutionException;
import com.thiyagu.rnd.core.workflow.WorkflowExecutionResult;
import com.thiyagu.rnd.core.workflow.WorkflowContext;
/*
 *  TODO 
 */
public class WorkflowEngine {

	private final WorkflowScheduler workflowScheduler;
	
	public WorkflowEngine(WorkflowScheduler workflowScheduler) {
		this.workflowScheduler = workflowScheduler;
	}
	
	public WorkflowExecutionResult execute(String flow) throws FlowExecutionException {
		return execute(flow, new WorkflowContext());
	}
	
	public WorkflowExecutionResult execute(String flow, WorkflowContext workflowContext) throws FlowExecutionException {
		WorkflowContext request = new WorkflowContext();
		request.putAll(workflowContext);
		return workflowScheduler.execute(flow, request);
	}

	
}


