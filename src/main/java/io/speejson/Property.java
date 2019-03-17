package io.speejson;

import io.speejson.bytefier.Bytefier;

public class Property {
	
	private String name;
	
	private byte[] key;
	
	private Class<?> type;

	private Bytefier bytefier;
	
	public Property(String name, byte[] key, Class<?> type, Bytefier bytefier) {
		this.name = name;
		this.key = key;
		this.type = type;
		this.bytefier = bytefier;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte[] getKey() {
		return key;
	}

	public void setKey(byte[] key) {
		this.key = key;
	}

	public Class<?> getType() {
		return type;
	}

	public void setType(Class<?> type) {
		this.type = type;
	}

	public Bytefier getBytefier() {
		return bytefier;
	}

	public void setBytefier(Bytefier<?> bytefier) {
		this.bytefier = bytefier;
	}
	
}