package com.thiyagu.rnd.core.task;

import java.util.Map;

import com.thiyagu.rnd.core.workflow.Task;

public class DefaultTaskProvider implements WorkflowTaskProvider {

	private final Map<String, Task> tasks;

	public DefaultTaskProvider(Map<String, Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public Task getTask(String taskname) {
		return this.tasks.get(taskname);
	}
}
