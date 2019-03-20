package io.speejson.exception;

public class JsonWritingException extends RuntimeException {

	private static final long serialVersionUID = 8014620982760333258L;

	public JsonWritingException() {
	}

	public JsonWritingException(String message) {
		super(message);
	}

	public JsonWritingException(Throwable cause) {
		super(cause);
	}

	public JsonWritingException(String message, Throwable cause) {
		super(message, cause);
	}

	public JsonWritingException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}
