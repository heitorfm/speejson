package io.speejson.bytefier;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class BytefiersHolder {

	private static Map<Class<?>, Bytefier<?>> bytefiers = new HashMap<>();

	private static Bytefier<?> customObjectBytefier = new CustomObjectBytefier();
	
	static {
		
		bytefiers.put(Byte.class, new ByteBytefier());
		bytefiers.put(Character.class, new CharBytefier());
		bytefiers.put(Short.class, new ShortBytefier());
		bytefiers.put(Integer.class, new IntegerBytefier());
		bytefiers.put(Long.class, new LongBytefier());
		
		bytefiers.put(byte.class, bytefiers.get(Byte.class));
		bytefiers.put(char.class, bytefiers.get(Character.class));
		bytefiers.put(short.class, bytefiers.get(Short.class));
		bytefiers.put(int.class, bytefiers.get(Integer.class));
		bytefiers.put(long.class, bytefiers.get(Long.class));
		
		bytefiers.put(Float.class, new FloatBytefier());
		bytefiers.put(Double.class, new DoubleManualConvertBytefier());

		bytefiers.put(float.class, bytefiers.get(Float.class));
		bytefiers.put(double.class, bytefiers.get(Double.class));

		bytefiers.put(String.class, new StringDirectBytefier());

		bytefiers.put(Boolean.class, new BooleanBytefier());
		bytefiers.put(boolean.class, bytefiers.get(Boolean.class));

		bytefiers.put(Date.class, new DateBytefier());
		bytefiers.put(BigDecimal.class, new BigDecimalBytefier());
		
		
	}
	
	public static Bytefier<?> get(Class<?> clazz) {
		
		return bytefiers.getOrDefault(clazz, customObjectBytefier);
		
	}

	
	
}
