package io.speejson;

public class JsonSyntax {

	private static final byte[] OPEN_CLAVE = "{".getBytes();
	private static final byte[] CLOSE_CLAVE = "}".getBytes();

	private static final byte[] OPEN_BRACKET = "[".getBytes();
	private static final byte[] CLOSE_BRACKET = "]".getBytes();

	private static final byte[] COMMA = ",".getBytes();

	private static final byte[] QUOTE = "\"".getBytes();

	private static final byte[] COLON = ":".getBytes();

	private static final byte[] NULL = "null".getBytes();

	
	private JsonSyntax() {
		throw new IllegalStateException("Utility class");
	}


	public static byte[] getOpenClave() {
		return OPEN_CLAVE;
	}

	public static byte[] getCloseClave() {
		return CLOSE_CLAVE;
	}

	public static byte[] getOpenBracket() {
		return OPEN_BRACKET;
	}

	public static byte[] getCloseBracket() {
		return CLOSE_BRACKET;
	}

	public static byte[] getComma() {
		return COMMA;
	}

	public static byte[] getQuote() {
		return QUOTE;
	}

	public static byte[] getColon() {
		return COLON;
	}

	public static byte[] getNull() {
		return NULL;
	}	
	
}
