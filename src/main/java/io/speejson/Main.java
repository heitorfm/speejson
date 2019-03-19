package io.speejson;

import java.util.concurrent.TimeUnit;

import io.speejson.bytefier.LongBytefier;

//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)

public class Main {

	public static void main(String[] argss) {

		
		Double d = 1.15;

		LongBytefier longBytefier = new LongBytefier();
		
		for (int f = 0; f < 30; f++) {
			
			long ini2 = System.nanoTime();
			
			long longValue = Double.doubleToRawLongBits(d);
			
			
		    byte[] bs = longBytefier.convert(longValue);
		    
			long end2 = System.nanoTime();
			
			System.out.println(new String(bs));
			
			System.out.println("SPEEJSON => " + (end2 - ini2) + " nanos  |  " +  TimeUnit.NANOSECONDS.toMicros(end2 - ini2) + " micros  |  " + TimeUnit.NANOSECONDS.toMillis(end2 - ini2) + " millis");
		}
		//System.out.println(Arrays.toString(digits));
		
		
		
		System.exit(1);
		
		for (int f = 0; f < 10; f++) {
			
		
		long ini = System.nanoTime();

		byte[] bs = ("password"+f).getBytes();
		
		long end = System.nanoTime();
		
		System.out.println("SPEEJSON => " + (end - ini) + " nanos  |  " +  TimeUnit.NANOSECONDS.toMicros(end - ini) + " micros  |  " + TimeUnit.NANOSECONDS.toMillis(end - ini) + " millis");

		}
	
		System.exit(1);
		

		/*SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		DecimalFormat decForm = (DecimalFormat) DecimalFormat.getInstance(new Locale("pt", "BR"));
		MathContext mc = new MathContext(2, RoundingMode.CEILING);
		
		BigDecimal bd = new BigDecimal(105.55);
		String money = bd.toString();
		
		Date today = new Date();
				
		List items = Arrays.asList("item1", "item2", "item3");

		int[] sequence = {33, 44, 55};

		int[] chars = {12, 55, 21};

		Date[] dates = {new Date(), new Date()};
		
		byte[] arr = new byte[1000];
		
		ByteBuffer buf = ByteBuffer.allocate(1000);*/
		

		
	}
	
}
