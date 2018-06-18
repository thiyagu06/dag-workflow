package com.thiyagu.rnd.core.cache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.thiyagu.rnd.core.workflow.Workflow;
import com.thiyagu.rnd.core.workflow.parser.WorkflowDefinitionLoader;

public class ConcurrentMapWorkflowCache implements WorkflowCache {

	private final ConcurrentMap<String, Workflow> cache;
	
	public ConcurrentMapWorkflowCache(final ConcurrentMap<String, Workflow> cache) {
		this.cache = cache;
	}

	public ConcurrentMapWorkflowCache() {
		this(new ConcurrentHashMap<String, Workflow>());
	}

	
	@Override
	public void clearAll() {
		cache.clear();

	}

	@Override
	public void clear(String key) {
		cache.remove(key);

	}

	@Override
	public Workflow get(WorkflowDefinitionLoader loader,String workflowId) {
		Workflow workflow;
		if(this.cache.containsKey(workflowId)) {
			workflow = this.cache.get(workflowId);
		}else {
			workflow = loader.load(workflowId);
			this.cache.put(workflowId, workflow);
		}
		return workflow;
	}

}
