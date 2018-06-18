package com.thiyagu.rnd.core.workflow.execution;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

import com.thiyagu.rnd.core.workflow.WorkflowExecutionResult;
import com.thiyagu.rnd.core.workflow.Workflow;
import com.thiyagu.rnd.core.workflow.WorkflowContext;
import com.thiyagu.rnd.core.workflow.Task;

public class WorkflowSchedulerContext {

	private BlockingQueue<Task> workflowQueue;
	
	private final WorkflowContext workflowContext;
	
	private final WorkflowExecutionResult workflowExecutionResult;
	
	private final Map<Task,Integer> indegreeNodeCount;
	
	private final AtomicInteger scheduledLeafNodesCount;

	public WorkflowSchedulerContext(Workflow workflow,WorkflowContext context,WorkflowExecutionResult workflowExecutionResult) {
		super();
		this.workflowQueue = new ArrayBlockingQueue<>(workflow.size());
		this.workflowQueue.addAll(workflow.getInitialNodes());
		this.workflowContext = context;
		this.workflowExecutionResult = workflowExecutionResult;
		this.indegreeNodeCount = workflow.getNodeIndegreeCount();
		this.scheduledLeafNodesCount = new AtomicInteger(workflow.getAllLeafNodes().size());
	}

	/**
	 * @return the workflowQueue
	 */
	public BlockingQueue<Task> getWorkflowQueue() {
		return workflowQueue;
	}

	/**
	 * @param workflowQueue the workflowQueue to set
	 */
	public void setWorkflowQueue(BlockingQueue<Task> workflowQueue) {
		this.workflowQueue = workflowQueue;
	}

	/**
	 * @return the workflowContext
	 */
	public WorkflowContext getWorkflowContext() {
		return workflowContext;
	}

	/**
	 * @return the workflowExecutionResult
	 */
	public WorkflowExecutionResult getExecutionResult() {
		return workflowExecutionResult;
	}

	/**
	 * @return the indegreeNodeCount
	 */
	public Map<Task, Integer> getIndegreeNodeCount() {
		return indegreeNodeCount;
	}

	/**
	 * @return the scheduledLeafNodesCount
	 */
	public AtomicInteger getScheduledLeafNodesCount() {
		return scheduledLeafNodesCount;
	}
	
	public Task getcurrentNode() {
		try {
			return this.workflowQueue.take();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int notifyLeafnodeScheduled() {
		return this.scheduledLeafNodesCount.decrementAndGet();
	}
	
}
