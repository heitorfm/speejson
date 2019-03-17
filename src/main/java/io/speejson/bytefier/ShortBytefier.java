package io.speejson.bytefier;

public class ShortBytefier implements Bytefier<Short> {

	@Override
	public byte[] convert(Short value) {
		
	    return new byte[] {
	    	(byte) (value & 0xff),
	    	(byte) ((value >> 8) & 0xff)
	    };
	}

}
