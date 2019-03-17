package io.speejson.bytefier;

public class CustomObjectBytefier implements Bytefier {

	@Override
	public byte[] convert(Object value) {
		
		return "null".getBytes();
		
	}

}
