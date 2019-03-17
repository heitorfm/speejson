package io.speejson.exception;

public class InternalToolingInitializationException extends RuntimeException {

	private static final long serialVersionUID = -2372159397573061371L;

	public InternalToolingInitializationException() {
	}

	public InternalToolingInitializationException(String message) {
		super(message);
	}

	public InternalToolingInitializationException(Throwable cause) {
		super(cause);
	}

	public InternalToolingInitializationException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalToolingInitializationException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
