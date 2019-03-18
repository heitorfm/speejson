package io.speejson;

import java.io.OutputStream;
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

	private byte curState = READY;
	
	private static final List<Class<?>> NATIVE_TYPES = Arrays.asList(
			boolean.class, char.class, byte.class, short.class,
			int.class, long.class, float.class, double.class,
	        Boolean.class, Character.class, Byte.class, Short.class,
	        Integer.class, Long.class, Float.class, Double.class, Void.class);
	
	
	private ReaderBuilder readerBuilder = new ReaderBuilder();
	
	public SpeeJson() {
		writer = new StringSpeeJsonWriter();
	}
	
	public SpeeJson(byte[] back) {
		this(back, 0);
	}

	public SpeeJson(byte[] back, int offset) {
		writer = new ByteArraySpeeJsonWriter(back, offset);
	}
	
	public SpeeJson(ByteBuffer buf) {
		writer = new ByteBufferSpeeJsonWriter(buf);
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
			writer.append(JsonSyntax.getCloseClave());
			curState = OPENED;
		}
		
		if(porpCount > 0) writer.append(JsonSyntax.getComma());
		
		processKeyValue(key, value);
		
		return this;
	}
	
	
	public void put(Object obj) {
		
		writer.append(JsonSyntax.getCloseClave());
		
		extractValue2(obj);
		
		writer.append(JsonSyntax.getCloseClave() );

	}
	
	private void processKeyValue(Property property, Object value) {
		
		writer.append(property.getKey());
		
		extractValue(value);
		
		porpCount++;
	}
	
	private void processKeyValue(String key, Object value) {

		writer.append(JsonSyntax.getQuote()).append(key).append(JsonSyntax.getQuote()).append(JsonSyntax.getColon());
		
		extractValue(value);
		
		porpCount++;
	}
	
	@SuppressWarnings("unchecked")
	private void extractValue2(Object value) {

		if(value == null)
			writer.append(JsonSyntax.getNull());
		else {
			
			ObjectReader reader = readerBuilder.getReader(value.getClass());
			
			Property[] props = reader.getProperties();
			
			for (int i = 0; i < props.length; i++) {

				
				Object content = reader.read(value, i);

				if(i > 0) writer.append(JsonSyntax.getComma());
				
				
				writer.append(props[i].getKey());
				
				if(content == null) writer.append(JsonSyntax.getNull());
				else {
					writer.append(props[i].getBytefier().convert(content));
				}
				
				porpCount++;
				
			}
			
		}
		
	}
		
	private void extractValue(Object value) {
		
		if(value == null)
			writer.append(JsonSyntax.getNull());
		
		
		else if(NATIVE_TYPES.contains(value.getClass())) 
			writer.append(value.toString());
		
		else if(value.getClass() == String.class)
			writer.append(JsonSyntax.getQuote()).append(value.toString()).append(JsonSyntax.getQuote());
		
		else if(value.getClass() == Date.class) {
			if(dateFormat != null) writer.append(JsonSyntax.getQuote()).append(dateFormat.format(value)).append(JsonSyntax.getQuote());
			else writer.append(Long.toString(((Date)value).getTime()));
		}
		else if(value.getClass() == Calendar.class) {
			if(dateFormat != null) writer.append(JsonSyntax.getQuote()).append(dateFormat.format(((Calendar)value).getTime())).append(JsonSyntax.getQuote());
			else writer.append(Long.toString(((Calendar)value).getTime().getTime()));
		}
		else if(value.getClass() == LocalDateTime.class || value.getClass() == LocalDate.class || value.getClass() == LocalTime.class) {
			if(dateTimeFormatter != null) writer.append(JsonSyntax.getQuote()).append(dateTimeFormatter.format((TemporalAccessor)value)).append(JsonSyntax.getQuote());
			else writer.append(JsonSyntax.getQuote()).append(value.toString()).append(JsonSyntax.getQuote());			
		}
		
		else if(value.getClass() == BigDecimal.class) {
			if(mc != null) value = ((BigDecimal)value).setScale(mc.getPrecision(), mc.getRoundingMode());
			if(decFormat != null) writer.append(JsonSyntax.getQuote()).append(decFormat.format(value)).append(JsonSyntax.getQuote());
			else writer.append(value.toString());
		}
		
		else if(value instanceof List) {
			writer.append(JsonSyntax.getOpenBracket());
			for(int i = 0; i < ((List)value).size(); i++) {
				if(i > 0) writer.append(JsonSyntax.getComma());
				extractValue(((List)value).get(i));
			}
			writer.append(JsonSyntax.getCloseBracket());
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
			writer.append(JsonSyntax.getOpenBracket());
			for(int i = 0; i < ((Date[])value).length; i++) {
				if(i > 0) writer.append(JsonSyntax.getComma());
				extractValue(((Date[])value)[i]);
			}
			writer.append(JsonSyntax.getCloseBracket());
		}

		else if(value instanceof BigDecimal[]) {
			writer.append(JsonSyntax.getOpenBracket() );
			for(int i = 0; i < ((BigDecimal[])value).length; i++) {
				if(i > 0) writer.append(JsonSyntax.getComma());
				extractValue(((BigDecimal[])value)[i]);
			}
			writer.append(JsonSyntax.getCloseBracket());
		}
		
		else if(value instanceof BigInteger[]) {
			writer.append(JsonSyntax.getOpenBracket() );
			for(int i = 0; i < ((BigInteger[])value).length; i++) {
				if(i > 0) writer.append(JsonSyntax.getComma());
				extractValue(((BigInteger[])value)[i]);
			}
			writer.append(JsonSyntax.getCloseBracket());
		}
		
		else {
			writer.append(JsonSyntax.getOpenClave());
	
			ObjectReader reader = readerBuilder.getReader(value.getClass());
				
			Property[] fields = reader.getProperties();
			
			for (int i = 0; i < fields.length; i++) {
			
				Object res = reader.read(value, i);

				if(i > 0) writer.append(JsonSyntax.getComma());

				processKeyValue(fields[i], res);
				
			}
			
			writer.append(JsonSyntax.getCloseClave());
		
		}
		
	}

	
	public String toString() {
		return writer.toString();
	}

	public void clear() {
		writer.clear();
	}
	
}
