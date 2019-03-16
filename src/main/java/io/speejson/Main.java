package io.speejson;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import net.bytebuddy.implementation.bind.annotation.BindingPriority;

//@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)

public class Main {

	public static void main(String[] argss) {


		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
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
		
		ByteBuffer buf = ByteBuffer.allocate(1000);
		

		
	}
	
}
