package io.speejson;

public interface SpeeJsonWriter {

	public SpeeJsonWriter append(String str);

	public SpeeJsonWriter append(char c);
	
	public String toString();

	public void clear();

	public SpeeJsonWriter append(byte[] ba);
}
