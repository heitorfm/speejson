package io.speejson.bytefier;

import io.speejson.JsonSyntax;
import io.speejson.UnsafeHandler;

public class StringDirectBytefier implements Bytefier<String> {

	@Override
	public byte[] convert(String value) {
	
		byte[] strBa = UnsafeHandler.getValue(value);
		
		byte[] ret = new byte[strBa.length + (JsonSyntax.QUOTE.length * 2)];
		
		int offset = 0;
		System.arraycopy(JsonSyntax.QUOTE, 0, ret, offset, JsonSyntax.QUOTE.length);
		offset += JsonSyntax.QUOTE.length;
		
		System.arraycopy(strBa, 0, ret, offset, strBa.length);
		offset += strBa.length;
		
		System.arraycopy(JsonSyntax.QUOTE, 0, ret, offset, JsonSyntax.QUOTE.length);
		
		return ret;
		
	}
	
}
