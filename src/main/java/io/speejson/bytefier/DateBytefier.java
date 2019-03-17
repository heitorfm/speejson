package io.speejson.bytefier;

import java.util.Date;

public class DateBytefier implements Bytefier<Date> {

	@SuppressWarnings("unchecked")
	public byte[] convert(Date value) {
		
		Bytefier<Long> bytefier = (Bytefier<Long>) BytefiersHolder.get(Long.class);
		
	    return bytefier.convert(value.getTime());
		
	}

}
