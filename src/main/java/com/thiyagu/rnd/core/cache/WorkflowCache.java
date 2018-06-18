package com.thiyagu.rnd.core.cache;

import com.thiyagu.rnd.core.workflow.Workflow;
import com.thiyagu.rnd.core.workflow.parser.WorkflowDefinitionLoader;

public interface WorkflowCache {

	public void clearAll();

	public void clear(String key);

	public Workflow get(WorkflowDefinitionLoader loader,String workflowId);
}
