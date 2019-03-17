package io.speejson.bytefier;

public class DoubleStandardBytefier implements Bytefier<Double> {

	@Override
	public byte[] convert(Double value) {

		return value.toString().getBytes();
		
	}

}
