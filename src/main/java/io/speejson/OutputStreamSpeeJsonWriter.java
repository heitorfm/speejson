package io.speejson;

import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OutputStreamSpeeJsonWriter implements SpeeJsonWriter {

	private OutputStream os;
	
	//private static final Logger log = LoggerFactory.getLogger(OutputStreamSpeeJsonWriter.class);
	
	public OutputStreamSpeeJsonWriter(OutputStream os) {
		this.os = os;
	}

	@Override
	public SpeeJsonWriter append(String str) {

		byte[] bs = str.getBytes();
		
		try {
			os.write(bs);
		} catch (IOException e) {
		//	log.error(e.getMessage(), e);
		}
		
		return this;
	}

	@Override
	public SpeeJsonWriter append(char c) {

		byte[] bs = Character.toString(c).getBytes();
		
		try {
			os.write(bs);
		} catch (IOException e) {
		//	log.error(e.getMessage(), e);
		}
		
		return this;

	}

	@Override
	public void clear() {
		
		try {
			os.flush();
		} catch (IOException e) {
		//	log.error(e.getMessage(), e);
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
		//	log.error(e.getMessage(), e);
		}
		
		return this;
		
	}
	
}
