package io.speejson;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.nio.ByteBuffer;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.speejson.io.ObjectReader;
import io.speejson.io.ReaderBuilder;

public class SpeeJson {
	
	private int porpCount = 0;
	
	private DateFormat dateFormat = null;
	
	private DecimalFormat decFormat = null;
	
	private MathContext mc = null;
	
	private DateTimeFormatter dateTimeFormatter;
	
	private SpeeJsonWriter writer;
	
	private static final byte READY  = 0;
	private static final byte OPENED = 1;
	private static final byte CLOSED = 1;
	private byte curState = READY;
	
	private static final List<Class<?>> NATIVE_TYPES = Arrays.asList(
			boolean.class, char.class, byte.class, short.class,
			int.class, long.class, float.class, double.class,
	        Boolean.class, Character.class, Byte.class, Short.class,
	        Integer.class, Long.class, Float.class, Double.class, Void.class);
	        //String.class, Timestamp.class, BigInteger.class, BigDecimal.class, 
	        //Date.class, LocalDate.class, LocalTime.class, LocalDateTime.class, Calendar.class, GregorianCalendar.class);
	
	

	
	public SpeeJson() {
		writer = new StringSpeeJsonWriter();
		//writer.append("{");
	}
	
	public SpeeJson(byte[] back) {
		this(back, 0);
	}

	public SpeeJson(byte[] back, int offset) {
		writer = new ByteArraySpeeJsonWriter(back, 0);
		//writer.append("{");
	}
	
	public SpeeJson(ByteBuffer buf) {
		writer = new ByteBufferSpeeJsonWriter(buf);
		//writer.append("{");
	}

	public SpeeJson(OutputStream os) {
		writer = new OutputStreamSpeeJsonWriter(os);
	}

	public void setDateFormatter(DateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}
	
	public void setDecimalFormat(DecimalFormat decFormat) {
		this.decFormat = decFormat;
	}
	
	public void setMathContext(MathContext mc) {
		this.mc = mc;
	}
	
	public void setDateTimeFormatter(DateTimeFormatter dateTimeFormatter) {
		this.dateTimeFormatter = dateTimeFormatter;
	}
	
	public SpeeJson put(String key, Object value) {
		
		if(curState == READY) {
			writer.append(JsonSyntax.OPEN_CLAVE);
			curState = OPENED;
		}
		
		if(porpCount > 0) writer.append(JsonSyntax.COMMA);
		
		processKeyValue(key, value);
		
		return this;
	}
	
	
	public void put(Object obj) {
		
		extractValue(obj);
		

		if(curState == OPENED) {
			writer.append(JsonSyntax.CLOSE_CLAVE);
			curState = CLOSED;
		}
		
	}
	
	private void processKeyValue(Property property, Object value) {
		
		writer.append(property.getKey());
		
		extractValue(value);
		
		porpCount++;
	}
	
	private void processKeyValue(String key, Object value) {

		writer.append(JsonSyntax.QUOTE).append(key).append(JsonSyntax.QUOTE).append(JsonSyntax.COLON);
		
		extractValue(value);
		
		porpCount++;
	}
	
	private void extractValue(Object value) {
		
		if(value == null)
			writer.append(JsonSyntax.NULL);
		
		else if(NATIVE_TYPES.contains(value.getClass())) 
			writer.append(value.toString());
		
		else if(value.getClass() == String.class)
			writer.append(JsonSyntax.QUOTE).append(value.toString()).append(JsonSyntax.QUOTE);
		
		else if(value.getClass() == Date.class) {
			if(dateFormat != null) writer.append(JsonSyntax.QUOTE).append(dateFormat.format(value)).append(JsonSyntax.QUOTE);
			else writer.append(Long.toString(((Date)value).getTime()));
		}
		else if(value.getClass() == Calendar.class) {
			if(dateFormat != null) writer.append(JsonSyntax.QUOTE).append(dateFormat.format(((Calendar)value).getTime())).append(JsonSyntax.QUOTE);
			else writer.append(Long.toString(((Calendar)value).getTime().getTime()));
		}
		else if(value.getClass() == LocalDateTime.class || value.getClass() == LocalDate.class || value.getClass() == LocalTime.class) {
			if(dateTimeFormatter != null) writer.append(JsonSyntax.QUOTE).append(dateTimeFormatter.format((TemporalAccessor)value)).append(JsonSyntax.QUOTE);
			else writer.append(JsonSyntax.QUOTE).append(value.toString()).append(JsonSyntax.QUOTE);			
		}
		
		else if(value.getClass() == BigDecimal.class) {
			if(mc != null) value = ((BigDecimal)value).setScale(mc.getPrecision(), mc.getRoundingMode());
			if(decFormat != null) writer.append(JsonSyntax.QUOTE).append(decFormat.format(value)).append(JsonSyntax.QUOTE);
			else writer.append(value.toString());
		}
		
		else if(value instanceof List) {
			writer.append(JsonSyntax.OPEN_BRACKET);
			for(int i = 0; i < ((List)value).size(); i++) {
				if(i > 0) writer.append(JsonSyntax.COMMA);
				extractValue(((List)value).get(i));
			}
			writer.append(JsonSyntax.CLOSE_BRACKET);
		}
		
		else if(value instanceof Integer[])
			writer.append(Arrays.toString((Integer[]) value));
		else if(value instanceof int[])
			writer.append(Arrays.toString((int[]) value));
		else if(value instanceof Double[])
			writer.append(Arrays.toString((Double[]) value));
		else if(value instanceof double[])
			writer.append(Arrays.toString((double[]) value));
		else if(value instanceof Float[])
			writer.append(Arrays.toString((Float[]) value));
		else if(value instanceof float[])
			writer.append(Arrays.toString((Float[]) value));
		else if(value instanceof Short[])
			writer.append(Arrays.toString((Short[]) value));
		else if(value instanceof short[])
			writer.append(Arrays.toString((short[]) value));
		else if(value instanceof Long[])
			writer.append(Arrays.toString((Long[]) value));
		else if(value instanceof long[])
			writer.append(Arrays.toString((long[]) value));
		else if(value instanceof Character[])
			writer.append(Arrays.toString((Character[]) value));
		else if(value instanceof char[])
			writer.append(Arrays.toString((char[]) value));
		else if(value instanceof Boolean[])
			writer.append(Arrays.toString((Boolean[]) value));
		else if(value instanceof boolean[])
			writer.append(Arrays.toString((boolean[]) value));
		
		else if(value instanceof Date[]) {
			writer.append(JsonSyntax.OPEN_BRACKET);
			for(int i = 0; i < ((Date[])value).length; i++) {
				if(i > 0) writer.append(JsonSyntax.COMMA);
				extractValue(((Date[])value)[i]);
			}
			writer.append(JsonSyntax.CLOSE_BRACKET);
		}

		else if(value instanceof BigDecimal[]) {
			writer.append(JsonSyntax.OPEN_BRACKET);
			for(int i = 0; i < ((BigDecimal[])value).length; i++) {
				if(i > 0) writer.append(JsonSyntax.COMMA);
				extractValue(((BigDecimal[])value)[i]);
			}
			writer.append(JsonSyntax.CLOSE_BRACKET);
		}
		
		else if(value instanceof BigInteger[]) {
			writer.append(JsonSyntax.OPEN_BRACKET);
			for(int i = 0; i < ((BigInteger[])value).length; i++) {
				if(i > 0) writer.append(JsonSyntax.COMMA);
				extractValue(((BigInteger[])value)[i]);
			}
			writer.append(JsonSyntax.CLOSE_BRACKET);
		}
		
		else {
			writer.append(JsonSyntax.OPEN_CLAVE);
	
			//long ini = System.nanoTime();

			ObjectReader reader = ReaderBuilder.build(value.getClass());
		
			//long end = System.nanoTime();
			//System.out.println("read[build] => " + (end - ini) + " nanos  |  " +  TimeUnit.NANOSECONDS.toMicros(end - ini) + " micros  |  " + TimeUnit.NANOSECONDS.toMillis(end - ini) + " millis");
			
			Property[] fields = reader.getProperties();
			
			for (int i = 0; i < fields.length; i++) {

				
				Object res = reader.read(value, i);

				if(i > 0) writer.append(JsonSyntax.COMMA);
				

				processKeyValue(fields[i], res);
	
				
				
			}
			
			writer.append(JsonSyntax.CLOSE_CLAVE);
		
		}
		
	}

	

	
	public String toString() {

		
		return writer.toString();
	}

	public void clear() {
		writer.clear();
	}
	
}
