package com.thiyagu.rnd.core.exception;

public class FlowExecutionException extends RuntimeException {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    public FlowExecutionException(String msg) {
        super(msg);
    }

    public FlowExecutionException(String string, Exception e1) {
        super(string, e1);
    }

    public FlowExecutionException(Throwable cause) {
        super(cause);
    }
}
