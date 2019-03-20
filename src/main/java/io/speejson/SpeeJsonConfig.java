package io.speejson;

public class SpeeJsonConfig {

	
	
	private static final byte STRING_BITEFIER_FASTEST = 1;

	private static final byte STRING_BITEFIER_UTF16 = 2;
	
	private static final byte[] DEFULTS = new byte[] {STRING_BITEFIER_FASTEST};
	
	private byte[] parameters = null;
	
	public SpeeJsonConfig(byte[] params) {
		this.parameters = params;
	}

	public static SpeeJsonConfig defaults() {
		return new SpeeJsonConfig(DEFULTS);
	}
	
	public SpeeJsonConfig utf16() {
		parameters[0] = STRING_BITEFIER_UTF16;
		return this;
	}
	
}
