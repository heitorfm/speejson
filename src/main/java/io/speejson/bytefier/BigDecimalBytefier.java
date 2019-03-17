package io.speejson.bytefier;

import java.math.BigDecimal;

public class BigDecimalBytefier implements Bytefier<BigDecimal> {


	@SuppressWarnings("unchecked")
	public byte[] convert(BigDecimal value) {

		Bytefier<Double> bytefier = (Bytefier<Double>) BytefiersHolder.get(Double.class);
	    
	    return bytefier.convert(value.doubleValue());
		
	}

}
