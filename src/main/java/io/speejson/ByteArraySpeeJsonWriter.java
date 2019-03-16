package io.speejson;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class ByteArraySpeeJsonWriter implements SpeeJsonWriter {

	private byte[] back = null;
	
	private int curPnt = 0;
	
	public ByteArraySpeeJsonWriter(byte[] back, int offset) {
		this.back = back;
		this.curPnt = offset;
	}
	
	public SpeeJsonWriter append(String str) {
		
		byte[] content = str.getBytes(StandardCharsets.UTF_8);
		
		append(content);
		
		return this;
	}

	public SpeeJsonWriter append(char c) {
		
		checkCapacity(1);
		
		append(String.valueOf(c));
		
		return this;
	}

	private void checkCapacity(int newContent) {
		if(back.length < curPnt + newContent) {
			throw new UnsupportedOperationException("Byte array provided is too small.");
		}
	}
	
	public String toString() {
		return new String(back);
	}

	@Override
	public void clear() {
		 curPnt = 0;
		 Arrays.fill(back, (byte)0);
	}

	@Override
	public SpeeJsonWriter append(byte[] content) {
	
		checkCapacity(content.length);
		
		System.arraycopy(content, 0, back, curPnt, content.length);
		
		curPnt += content.length;
	
		return this;
	}
	
}
