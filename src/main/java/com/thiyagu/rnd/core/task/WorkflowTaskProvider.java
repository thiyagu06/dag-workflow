package com.thiyagu.rnd.core.task;

import com.thiyagu.rnd.core.workflow.Task;

public interface WorkflowTaskProvider {

	public Task getTask(String taskname);
	
}
