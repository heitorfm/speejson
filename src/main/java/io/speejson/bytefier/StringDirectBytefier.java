package io.speejson.bytefier;

import io.speejson.JsonSyntax;
import io.speejson.UnsafeHandler;

public class StringDirectBytefier implements Bytefier<String> {

	@Override
	public byte[] convert(String value) {
	
		byte[] strBa = UnsafeHandler.getValue(value);
		
		byte[] ret = new byte[strBa.length  + 2];
		
		ret[0] = JsonSyntax.getQuote()[0];
		ret[ret.length - 1] = JsonSyntax.getQuote()[0];
		
		for(int i = 1; i < ret.length - 1; i++) {
			ret[i] = strBa[i - 1];
		}
		
		return ret;
		
	}
	
}
