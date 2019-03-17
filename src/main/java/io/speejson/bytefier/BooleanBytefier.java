package io.speejson.bytefier;

public class BooleanBytefier implements Bytefier<Boolean> {

	@SuppressWarnings("unchecked")
	public byte[] convert(Boolean value) {
		
		Bytefier<String> bytefier = (Bytefier<String>) BytefiersHolder.get(String.class);
		
		return bytefier.convert(value.toString());
		
	}

}
