package io.speejson;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

public class ByteBufferSpeeJsonWriter implements SpeeJsonWriter {

	private ByteBuffer back = null;
	
	
	public ByteBufferSpeeJsonWriter(ByteBuffer back) {
		this.back = back;
	}
	
	public SpeeJsonWriter append(String str) {
		
		byte[] content = str.getBytes(StandardCharsets.UTF_8);

		back.put(content);
		
		return this;
	}

	public SpeeJsonWriter append(char c) {

		back.putChar(c);
		
		return this;
	}

	
	public String toString() {
		return new String(back.array());
	}

	@Override
	public void clear() {
		back.clear();
	}

	@Override
	public SpeeJsonWriter append(byte[] content) {
	
		back.put(content);
	
		return this;
	}
	
}
