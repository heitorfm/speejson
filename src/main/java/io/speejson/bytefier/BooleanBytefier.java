package io.speejson.bytefier;

public class BooleanBytefier implements Bytefier<Boolean> {

	private static final byte[] TRUE  = "true".getBytes();
	private static final byte[] FALSE = "false".getBytes();
	
	public byte[] convert(Boolean value) {

		return (value == null || value == false)? FALSE : TRUE;
		
	}

}
