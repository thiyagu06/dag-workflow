/**
 * Copyright (c) 2013-2016, Neuro4j
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package com.thiyagu.rnd.core.workflow;

import java.util.HashSet;
import java.util.Set;

import com.thiyagu.rnd.core.exception.FlowExecutionException;

/**
 * Class holds information regarding execution result.
 *
 */
public class WorkflowExecutionResult {

	int errorCode = -1;

	/**
	 * Keeps exception if flow finished with error.
	 */
	private FlowExecutionException exception = null;

	/**
	 * Keeps context after execution.
	 */
	private WorkflowContext workflowContext = null;

	private Set<NodeInfo> failedNodes;

	public WorkflowExecutionResult() {
	}

	public void setExecutionExeption(FlowExecutionException ex) {

		this.exception = ex;
	}

	public FlowExecutionException getException() {
		return exception;
	}

	public void print() {
		if (exception != null) {
			exception.printStackTrace();
		}

	}

	public WorkflowContext getWorkflowContext() {
		return workflowContext;
	}

	public void setWorkflowContext(WorkflowContext workflowContext) {
		this.workflowContext = workflowContext;
	}

	public void notifyFailedNodes(NodeInfo failedNode) {
		if (null == failedNodes) {
			failedNodes = new HashSet<>();
		}
		failedNodes.add(failedNode);
	}

}
