package com.thiyagu.rnd.core.workflow.execution;

import com.thiyagu.rnd.core.cache.ConcurrentMapWorkflowCache;
import com.thiyagu.rnd.core.cache.WorkflowCache;
import com.thiyagu.rnd.core.workflow.parser.WorkflowDefinitionLoader;

public class WorkflowConfig {

	private WorkflowDefinitionLoader workflowDefinitionLoader;

	private WorkflowCache workflowCache;

	private ThreadPoolTaskConfig threadPoolTaskConfig;

	

	public WorkflowConfig(ThreadPoolTaskConfig threadPoolTaskConfig,WorkflowDefinitionLoader workflowDefinitionLoader) {
		this.threadPoolTaskConfig = threadPoolTaskConfig;
		this.workflowCache = new ConcurrentMapWorkflowCache();
		this.workflowDefinitionLoader = workflowDefinitionLoader;
	}

	public WorkflowConfig(ThreadPoolTaskConfig threadPoolTaskConfig,
			WorkflowCache workflowCache) {
		this.threadPoolTaskConfig = threadPoolTaskConfig;
		this.workflowCache = workflowCache;
	}

	/**
	 * @return the loader
	 */
	public WorkflowDefinitionLoader getWorkflowDefinitionLoader() {
		return workflowDefinitionLoader;
	}

	/**
	 * @return the workflowCache
	 */
	public WorkflowCache getWorkflowCache() {
		return workflowCache;
	}

	/**
	 * @return the threadPoolTaskConfig
	 */
	public ThreadPoolTaskConfig getThreadPoolTaskConfig() {
		return threadPoolTaskConfig;
	}

}
