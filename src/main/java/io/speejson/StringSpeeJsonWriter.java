package io.speejson;

public class StringSpeeJsonWriter implements SpeeJsonWriter {

	private StringBuilder builder;

	public StringSpeeJsonWriter() {
		builder = new StringBuilder(100);
	}

	public StringSpeeJsonWriter append(String str) {
		builder.append(str);
		return this;
	}

	public StringSpeeJsonWriter append(char c) {
		builder.append(c);

		return this;
	}
	
	public String toString() {
		return builder.toString();
	}

	@Override
	public void clear() {
		builder = new StringBuilder(100);
	}

	@Override
	public SpeeJsonWriter append(byte[] ba) {
		
		builder.append(new String(ba));
		
		return this;
		
	}
}
