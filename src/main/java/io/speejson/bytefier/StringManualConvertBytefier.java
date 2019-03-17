package io.speejson.bytefier;

import io.speejson.JsonSyntax;

public class StringManualConvertBytefier implements Bytefier<String> {

	@Override
	public byte[] convert(String value) {
		
		char[] chars = value.toCharArray();
		byte[] bytes = new byte[chars.length * 2 + (JsonSyntax.QUOTE.length * 2)];
		for(int i = 0; i < chars.length; i++) {
		   bytes[i * 2] = (byte) (chars[i] >> 8);
		   bytes[i * 2 + 1] = (byte) chars[i];
		}
		
		return bytes;

	
	
	}

	/*
	 
	char[] chars2 = new char[bytes.length/2];
	for(int i=0;i<chars2.length;i++) 
	   chars2[i] = (char) ((bytes[i*2] << 8) + (bytes[i*2+1] & 0xFF));
		String password = new String(chars2);
	  
	 */
	
}
