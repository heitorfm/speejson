package io.speejson;

import java.io.IOException;
import java.io.OutputStream;

import io.speejson.exception.JsonWritingException;

public class OutputStreamSpeeJsonWriter implements SpeeJsonWriter {

	private OutputStream os;
	
	public OutputStreamSpeeJsonWriter(OutputStream os) {
		this.os = os;
	}

	@Override
	public SpeeJsonWriter append(String str) {

		byte[] bs = str.getBytes();
		
		try {
			os.write(bs);
		} catch (IOException e) {
			throw new JsonWritingException(e);
		}
		
		return this;
	}

	@Override
	public SpeeJsonWriter append(char c) {

		byte[] bs = Character.toString(c).getBytes();
		
		try {
			os.write(bs);
		} catch (IOException e) {
			throw new JsonWritingException(e);
		}
		
		return this;

	}

	@Override
	public void clear() {
		
		try {
			os.flush();
		} catch (IOException e) {
			throw new JsonWritingException(e);
		}
		
	}

	@Override
	public String toString() {
		
		return os.toString();
		
	}

	@Override
	public SpeeJsonWriter append(byte[] ba) {
		
		try {
			os.write(ba);
		} catch (IOException e) {
			throw new JsonWritingException(e);
		}
		
		return this;
		
	}
	
}
