package io.speejson.io;

import io.speejson.Property;

public interface ObjectReader {

	public Object read(Object obj, int idx);
	
	public Property[] getProperties();

	public void setProperties(Property[] property);
		
}
