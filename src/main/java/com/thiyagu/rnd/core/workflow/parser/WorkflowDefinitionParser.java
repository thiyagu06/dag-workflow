package com.thiyagu.rnd.core.workflow.parser;

import java.io.Reader;

import com.thiyagu.rnd.core.exception.FlowExecutionException;
import com.thiyagu.rnd.core.workflow.Workflow;

public interface WorkflowDefinitionParser {
	
	public static String DEFAULT_EXT = ".json";

	Workflow parse(Reader reader, String name) throws FlowExecutionException;

	String getFileExt();
}
