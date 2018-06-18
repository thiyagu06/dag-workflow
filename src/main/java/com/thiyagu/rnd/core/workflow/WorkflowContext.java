package com.thiyagu.rnd.core.workflow;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Holds information about current request
 *
 */
public class WorkflowContext extends ConcurrentHashMap<String, Object>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	

	public WorkflowContext() {
		super();
	}

}
