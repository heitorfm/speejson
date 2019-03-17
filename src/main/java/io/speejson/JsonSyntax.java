package io.speejson;

public class JsonSyntax {

	public static final byte[] OPEN_CLAVE = "{".getBytes();
	public static final byte[] CLOSE_CLAVE = "}".getBytes();

	public static final byte[] OPEN_BRACKET = "[".getBytes();
	public static final byte[] CLOSE_BRACKET = "]".getBytes();

	public static final byte[] COMMA = ",".getBytes();

	public static final byte[] QUOTE = "\"".getBytes();

	public static final byte[] COLON = ":".getBytes();

	public static final byte[] NULL = "null".getBytes();

	
	private JsonSyntax() {
		throw new IllegalStateException("Utility class");
	}
	
}
