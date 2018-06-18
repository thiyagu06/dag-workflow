package com.thiyagu.rnd.core.workflow.parser;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import com.thiyagu.rnd.core.exception.FlowExecutionException;
import com.thiyagu.rnd.core.workflow.Workflow;

public class FileWorkflowDefinitionLoader implements WorkflowDefinitionLoader {

	private String baseDir;

	private final WorkflowDefinitionParser workflowDefinitionParser;
	

	public FileWorkflowDefinitionLoader(WorkflowDefinitionParser workflowDefinitionParser, String baseDir)
			throws FlowExecutionException {
		this.workflowDefinitionParser = workflowDefinitionParser;
		Optional.ofNullable(baseDir).orElseThrow(() -> new FlowExecutionException("Base dir is null"));
		this.baseDir = baseDir;
	}

	@Override
	public Workflow load(String name) throws FlowExecutionException {
		URL resource = getResource(name);
		return content(resource, name);
	}

	protected Workflow content(URL resource, final String name) throws FlowExecutionException {
		Workflow net = null;
		Reader inputStream = null;
		try {
			inputStream = getReader(resource);
			net = workflowDefinitionParser.parse(inputStream, name);

		} catch (IOException e) {
		} finally {
			try {
				if (null != inputStream)
					inputStream.close();
			} catch (Exception e) {
			}
		}
		return net;
	}

	protected URL getResource(final String name) throws FlowExecutionException {
		File file = new File(baseDir, name + workflowDefinitionParser.getFileExt());
		if (file.exists()) {
			try {
				return file.toURI().toURL();
			} catch (MalformedURLException e) {
				throw new FlowExecutionException(name + "not found");
			}
		}
		throw new FlowExecutionException("not found");
	}

	protected Reader getReader(URL resource) throws IOException {
		return new InputStreamReader(resource.openStream(), "UTF-8");
	}

}
