package com.thiyagu.rnd.core.workflow.execution;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.thiyagu.rnd.core.cache.WorkflowCache;
import com.thiyagu.rnd.core.exception.FlowExecutionException;
import com.thiyagu.rnd.core.workflow.Task;
import com.thiyagu.rnd.core.workflow.Workflow;
import com.thiyagu.rnd.core.workflow.WorkflowContext;
import com.thiyagu.rnd.core.workflow.WorkflowExecutionResult;
import com.thiyagu.rnd.core.workflow.parser.WorkflowDefinitionLoader;

/**
 * TODO : Add metrics logger work flow start and end. Use observer pattern 
 * TODO : Add validation to validate all the required parameters existing in the workflow context before executing the each node 
 * TODO : Workflow level timeout
 * TODO : Thread pool configuration for completable future
 * TODO : How to make sure the while loop in execute not goes into infinite mode.
 * TODO : Review the return type of excuteTask 
 * TODO : ExecuteAndWait method or fireAndForget of workflow run
 * @author thiyagu
 *
 */
public class WorkflowScheduler {

	private final WorkflowDefinitionLoader loader;

	private final WorkflowCache cache;
	
	public WorkflowScheduler(WorkflowConfig workflowConfig) {
		this.loader = workflowConfig.getWorkflowDefinitionLoader();
		this.cache = workflowConfig.getWorkflowCache();
	}

	public WorkflowExecutionResult execute(final String flowname, WorkflowContext workflowContext) {
		workflowContext = Optional.ofNullable(workflowContext).orElse(new WorkflowContext());
		WorkflowExecutionResult result = new WorkflowExecutionResult();
		Workflow workflow = loadWorkflow(flowname);
		if (workflow == null) {
			result.setExecutionExeption(new FlowExecutionException("Workflow not found"));
			return result;
		}
		WorkflowSchedulerContext schedulerContext = new WorkflowSchedulerContext(workflow,workflowContext,result);
		try {
			while (true) {
				Task currentNode = schedulerContext.getcurrentNode();
				scheduleNode(currentNode,schedulerContext);
				if (workflow.getAllLeafNodes().contains(currentNode)) {
					if(schedulerContext.notifyLeafnodeScheduled() ==0) {
						break;
					}
				}
			}
		} catch (Exception e) {
			result.setExecutionExeption(new FlowExecutionException("Exception occured"));
		}
		result.setWorkflowContext(workflowContext);
		return result;
	}

	private int execute(Task node, WorkflowContext request) {
		node.execute(request);
		return 1;
	}

	/*private void scheduleNode(BlockingQueue<Task> nodeQueue, Map<Task, Integer> indegreeNodeCount,
			WorkflowContext request, Task currentNode, WorkflowExecutionResult result) {
		PostCompletionProcess completionTask = new PostCompletionProcess(currentNode, nodeQueue, indegreeNodeCount);
	CompletableFuture.supplyAsync(() -> {
			try {
				return this.execute(currentNode, request);
			}catch(FlowExecutionException e) {
				result.setExecutionExeption(e);
			}
			return 1;
		}).thenRunAsync(completionTask::onCompletion);

	}*/
	
	private void scheduleNode(Task currentNode, WorkflowSchedulerContext context) {
		PostCompletionProcess completionTask = new PostCompletionProcess(currentNode, context.getWorkflowQueue(),
				context.getIndegreeNodeCount());
		CompletableFuture.supplyAsync(() -> {
			try {
				return this.execute(currentNode, context.getWorkflowContext());
			} catch (FlowExecutionException e) {
				context.getExecutionResult().setExecutionExeption(e);
			}
			return 1;
		}).thenRunAsync(completionTask::onCompletion);
	}

	public Workflow loadWorkflow(String flowName) throws FlowExecutionException {
		return cache.get(loader, flowName);
	}

}
