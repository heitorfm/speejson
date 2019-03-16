package io.speejson;

public class Property {
	
	private String name;
	
	private byte[] key;
	
	private Class<?> type;

	
	
	public Property(String name, byte[] key, Class<?> type) {
		super();
		this.name = name;
		this.key = key;
		this.type = type;
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
	
}