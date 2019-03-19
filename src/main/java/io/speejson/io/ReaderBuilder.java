/**
 *   ========================================================================
 *   SpeeJson - high throughput json processing
 *   Copyright (C) 2019  Heitor Machado
 *   ------------------------------------------------------------------------
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Lesser General Public License as published
 *    by the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <https://www.gnu.org/licenses/>.
 * 
 **/



package io.speejson.io;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.speejson.JsonSyntax;
import io.speejson.Property;
import io.speejson.bytefier.Bytefier;
import io.speejson.bytefier.BytefiersHolder;
import io.speejson.exception.InternalToolingInitializationException;
import io.speejson.exception.ReflectionException;
import javassist.CannotCompileException;
import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtField;
import javassist.CtMethod;
import javassist.CtNewMethod;
import javassist.NotFoundException;

public class ReaderBuilder {

	private static final Map<Class<?>, ObjectReader> readers = new HashMap<>();
    
    public ObjectReader getReader(Class<?> clazz) {
    	
    	ObjectReader reader = readers.get(clazz);
    	
    	if(reader == null) {
    		reader = build(clazz);
    		readers.put(clazz, reader);
    	}
    	
    	return reader;
    }
	
	public ObjectReader build(Class<?> clazz) {

		ObjectReader reader = null;
		
		StringBuilder methBody = new StringBuilder("public Object read(Object obj, int idx) {");
		methBody.append(clazz.getCanonicalName() + " pojo = (" + clazz.getCanonicalName() + ") obj;");
		methBody.append("Object ret = null;");
		
		Field[] fields = clazz.getDeclaredFields();
		
		Property[] properties = new Property[fields.length];
		int propsCnt = 0;
		
		for (int i = 0; i < fields.length; i++) {

			Field field = fields[i];
			
			if(field.getName().equals("serialVersionUID")) continue;
			
			Bytefier<?> bytefier = BytefiersHolder.get(field.getType());
			Property prop = new Property(field.getName(), assembleKey(field.getName()), field.getType(), bytefier);
			properties[propsCnt++] = prop;
			
			Method method = getMehtod(clazz, field);
			
			Class<?> retType = method.getReturnType();
			
			String methName = method.getName();
			
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
		
		methBody.append("return ret;}");

		try {
		
			ClassPool cp = ClassPool.getDefault();
			CtClass objectReaderInterface = cp.get("io.speejson.io.ObjectReader");
			CtClass readerClass = cp.makeClass("io.speejson.io.SpeedJson" + clazz.getSimpleName() + "Reader");
			readerClass.addInterface(objectReaderInterface);
			
			CtMethod readMethod = CtNewMethod.make(methBody.toString(), readerClass);
			readerClass.addMethod(readMethod);
			
			CtField field = CtField.make("private " + Property.class.getCanonicalName() + "[] properties;", readerClass);
			readerClass.addField(field);
			
			CtMethod getFieldsMethod = CtNewMethod.make("public " + Property.class.getCanonicalName() + "[] getProperties(){return properties;}", readerClass);
			readerClass.addMethod(getFieldsMethod);
			CtMethod setFieldsMethod = CtNewMethod.make("public void setProperties(" + Property.class.getCanonicalName() + "[] properties){this.properties = properties;}", readerClass);
			readerClass.addMethod(setFieldsMethod);

			reader = (ObjectReader) readerClass.toClass().getConstructor().newInstance();
		
		} catch(CannotCompileException | InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException | NotFoundException e) {
			throw new InternalToolingInitializationException(e);
		}
		
		
		reader.setProperties(properties);
		
		return reader;
	}
	
	private Method getMehtod(Class<?> clazz, Field field) {
		
		String methNameIs = mountMethodName("is", field.getName());			
		String methNameGet = mountMethodName("get", field.getName());			
	
		Method[] declaredMethods = clazz.getDeclaredMethods();
		
		List<Method> methodList = 
				Stream.of(declaredMethods)
						.filter(methodTest -> isGetter(methodTest, field, methNameIs, methNameGet))
						.collect(Collectors.toList());
		
		if(methodList.size() != 1) {
			throw new ReflectionException("Did not find getter for: " + field.getName());
		}
		
		return methodList.get(0);
	}
	
	private String mountMethodName(String prefix, String fieldName) {
		return prefix + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
	}
	
	private boolean isGetter(Method methodTest, Field field, String methNameIs, String methNameGet) {
		
		return (methodTest.getName().equals(methNameIs) || methodTest.getName().equals(methNameGet))  
				&& methodTest.getReturnType() == field.getType() 
				&& methodTest.getParameters().length == 0;
		
	}
	
	private byte[] assembleKey(String fieldName) {
		
		byte[] name = fieldName.getBytes();
		
		byte[] ret = new byte[name.length + (JsonSyntax.getQuote().length * 2) + JsonSyntax.getQuote().length];
		
		int retOffset = 0;
		System.arraycopy(JsonSyntax.getQuote(), 0, ret, retOffset, JsonSyntax.getQuote().length);
		retOffset += JsonSyntax.getQuote().length;

		System.arraycopy(name, 0, ret, retOffset, name.length);
		retOffset += name.length;
		
		System.arraycopy(JsonSyntax.getQuote(), 0, ret, retOffset, JsonSyntax.getQuote().length);
		retOffset += JsonSyntax.getQuote().length;
		
		System.arraycopy(JsonSyntax.getColon(), 0, ret, retOffset, JsonSyntax.getColon().length);

		return ret;
	}
	
	
}
