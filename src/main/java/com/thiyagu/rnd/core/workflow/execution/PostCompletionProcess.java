package com.thiyagu.rnd.core.workflow.execution;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;

import com.thiyagu.rnd.core.workflow.Task;

/**
 * TODO : option to optimize the synchronized block inside the for loop
 * TODO : Replace for loop with for each and try improve the logic with fp paradigm
 * TODO : review the return type of onCompletion method. 
 * 
 * @author thiyagu
 *
 */
public class PostCompletionProcess {

	private final Task currentWorkflowNode;

	private final BlockingQueue<Task> nodeQueue;

	private final Map<Task, Integer> nodesIndegreeCount;

	/**
	 * @param currentWorkflowNode
	 * @param nodeQueue
	 * @param nodesIndegreeCount
	 */
	public PostCompletionProcess(Task currentWorkflowNode, BlockingQueue<Task> nodeQueue,
			Map<Task, Integer> nodesIndegreeCount) {
		super();
		this.currentWorkflowNode = currentWorkflowNode;
		this.nodeQueue = nodeQueue;
		this.nodesIndegreeCount = nodesIndegreeCount;
	}

	public int onCompletion() {
		Set<Task> outGoingEdges = currentWorkflowNode.getOutGoingEdges();
		for (Task node : outGoingEdges) {
			synchronized (this.nodesIndegreeCount) {
				int currentCount = this.nodesIndegreeCount.getOrDefault(node, -1);
				this.nodesIndegreeCount.put(node, currentCount - 1);
				if (currentCount - 1 == 0) {
					this.nodeQueue.offer(node);
				}
			}
		}
		return 1;
	}
}
