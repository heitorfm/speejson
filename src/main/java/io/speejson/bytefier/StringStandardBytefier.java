package io.speejson.bytefier;

public class StringStandardBytefier implements Bytefier<String> {

	@Override
	public byte[] convert(String value) {
	
		return value.getBytes();
		
	}
	
}
