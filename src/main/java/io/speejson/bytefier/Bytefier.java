package io.speejson.bytefier;

public interface Bytefier<T> {

	public byte[] convert(T value);
	
}
