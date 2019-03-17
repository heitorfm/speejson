package io.speejson.bytefier;

public class FloatBytefier implements Bytefier<Float> {

	@Override
	public byte[] convert(Float value) {
		
	    int intBits =  Float.floatToIntBits(value);

	    return new byte[] {
    		(byte) (intBits >> 24), 
    		(byte) (intBits >> 16), 
    		(byte) (intBits >> 8), 
    		(byte) (intBits)
	    };
	}

}
