package io.speejson.io;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import io.speejson.JsonSyntax;
import io.speejson.Property;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;

public class ReaderBuilder {

	private static final Map<Class<?>, ObjectReader> readers = new HashMap<>();
	

	
	public static ObjectReader build(Class<?> clazz) {
		
		ObjectReader reader =  readers.get(clazz);
		
		if(reader != null) return reader;
		
		try {

			ClassPool cp = ClassPool.getDefault();
			CtClass objectReaderInterface = cp.get("io.speejson.io.ObjectReader");
			CtClass readerClass = cp.makeClass("io.speejson.io.SpeedJson" + clazz.getSimpleName() + "Reader");
			readerClass.addInterface(objectReaderInterface);
			
			StringBuilder methBody = new StringBuilder("public Object read(Object obj, int idx) {");
			methBody.append(clazz.getCanonicalName() + " pojo = (" + clazz.getCanonicalName() + ") obj;");
			methBody.append("Object ret = null;");
			
			
			Field[] fields = clazz.getDeclaredFields();
			
			Property[] properties = new Property[fields.length];
			int propsCnt = 0;
			
			for (int i = 0; i < fields.length; i++) {
	
				Field field = fields[i];
				
				if(field.getName().equals("serialVersionUID")) continue;
				
				Property prop = new Property(field.getName(), assembleKey(field.getName()), field.getType());
				properties[propsCnt++] = prop;
				
				Method declaredMethod = null;
				
				String methName = null;
				if(field.getType() == Boolean.class || field.getType() == boolean.class) {
					methName = "is" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);			
				
					try {
						declaredMethod = clazz.getDeclaredMethod(methName);
					} catch(NoSuchMethodException | SecurityException e) {}
					
				}
				
				if(declaredMethod == null) {
					methName = "get" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);	
				}
				
				declaredMethod = clazz.getDeclaredMethod(methName);
				Class retType = declaredMethod.getReturnType();
				
				String cond = null;
				
				if(retType == int.class)
					cond = "if(idx == " + i + ") ret = Integer.valueOf(pojo." + methName + "());";
				else if(retType == boolean.class)
					cond = "if(idx == " + i + ") ret = Boolean.valueOf(pojo." + methName + "());";					
				else if(retType == float.class)
					cond = "if(idx == " + i + ") ret = Float.valueOf(pojo." + methName + "());";				
				else if(retType == double.class)
					cond = "if(idx == " + i + ") ret = Double.valueOf(pojo." + methName + "());";
				else if(retType == char.class)
					cond = "if(idx == " + i + ") ret = Character.valueOf(pojo." + methName + "());";				
				else if(retType == short.class)
					cond = "if(idx == " + i + ") ret = Short.valueOf(pojo." + methName + "());";
				else if(retType == long.class)
					cond = "if(idx == " + i + ") ret = Long.valueOf(pojo." + methName + "());";
				else if(retType == byte.class)
					cond = "if(idx == " + i + ") ret = Byte.valueOf(pojo." + methName + "());";
				else
					cond = "if(idx == " + i + ") ret = pojo." + methName + "();";
				
				methBody.append(cond);
				
			}
			
			methBody.append(" return ret;}");

			CtMethod readMethod = CtNewMethod.make(methBody.toString(), readerClass);
			readerClass.addMethod(readMethod);
			
			CtField field = CtField.make("private " + Property.class.getCanonicalName() + "[] properties;", readerClass);
			readerClass.addField(field);
			
			CtMethod getFieldsMethod = CtNewMethod.make("public " + Property.class.getCanonicalName() + "[] getProperties(){return properties;}", readerClass);
			readerClass.addMethod(getFieldsMethod);
			CtMethod setFieldsMethod = CtNewMethod.make("public void setProperties(" + Property.class.getCanonicalName() + "[] properties){this.properties = properties;}", readerClass);
			readerClass.addMethod(setFieldsMethod);

			reader = (ObjectReader) readerClass.toClass().getConstructor().newInstance();
			
			
			
			reader.setProperties(properties);
			
			readers.put(clazz, reader);
			
			return reader;
			
		} catch(Exception e) {
			throw new RuntimeException(e);
		}
		
		
	}
	
	
	private static byte[] assembleKey(String fieldName) {
		
		byte[] name = fieldName.getBytes();
		
		byte[] ret = new byte[name.length + (JsonSyntax.QUOTE.length * 2) + JsonSyntax.COLON.length];
		
		int retOffset = 0;
		System.arraycopy(JsonSyntax.QUOTE, 0, ret, retOffset, JsonSyntax.QUOTE.length);
		retOffset += JsonSyntax.QUOTE.length;

		System.arraycopy(name, 0, ret, retOffset, name.length);
		retOffset += name.length;
		
		System.arraycopy(JsonSyntax.QUOTE, 0, ret, retOffset, JsonSyntax.QUOTE.length);
		retOffset += JsonSyntax.QUOTE.length;
		
		System.arraycopy(JsonSyntax.COLON, 0, ret, retOffset, JsonSyntax.COLON.length);

		return ret;
	}
	
	
}
