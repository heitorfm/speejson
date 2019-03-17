package io.speejson.bytefier;

public class CharBytefier implements Bytefier<Character> {

	@Override
	public byte[] convert(Character value) {
		

		byte[] bytes = new byte[2];

		bytes[0 * 2] = (byte) (value >> 8);
		bytes[1 * 2 + 1] = (byte) value.charValue();

		
		return bytes;
		
	}

}
