package io.speejson.bytefier;

public class ByteBytefier implements Bytefier<Byte> {

	@Override
	public byte[] convert(Byte value) {

	    return new byte[] {value};
	}

}
