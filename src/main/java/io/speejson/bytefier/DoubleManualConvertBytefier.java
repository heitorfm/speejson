package io.speejson.bytefier;

public class DoubleManualConvertBytefier implements Bytefier<Double> {

	private static LongBytefier longBytefier = (LongBytefier) BytefiersHolder.get(Long.class);
	
    /**
     * Most used power of ten (to avoid the cost of Math.pow(10, n)
     */
    private static final long[] POWERS_OF_TEN_LONG = new long[19];
    
    private static final double[] POWERS_OF_TEN_DOUBLE = new double[30];
    
    static {
        POWERS_OF_TEN_LONG[0] = 1L;
        for (int i = 1; i < POWERS_OF_TEN_LONG.length; i++) {
            POWERS_OF_TEN_LONG[i] = POWERS_OF_TEN_LONG[i - 1] * 10L;
        }
        for (int i = 0; i < POWERS_OF_TEN_DOUBLE.length; i++) {
            POWERS_OF_TEN_DOUBLE[i] = Double.parseDouble("1e" + i);
        }
    }
    
    private static byte[] MINUS = "-".getBytes();
    private static byte[] ZERO = "0".getBytes();
    private static byte[] DOT = ".".getBytes();

	@Override
	public byte[] convert(Double value) {

		return formatDoubleFast(value, 2, 2);
		
	}

	
	 /**
     * Rounds the given source value at the given precision
     * and writes the rounded value into the given target
     * <p>
     * This method internally uses double precision computation and rounding,
     * so the result may not be accurate (see formatDouble method for conditions).
     *
     * @param source the source value to round
     * @param decimals the decimals to round at (use if abs(source) > 1.0)
     * @param precision the precision to round at (use if abs(source) < 1.0)
     * @param target the buffer to write to
     * 
     * Originally authored by Julien Aymé; in https://xmlgraphics.apache.org/repo.html
     */
    public static byte[] formatDoubleFast(double source, int decimals, int precision) {
    	
    	int offset = 0;
    	
    	byte[] ret = new byte[30];
    	
        if (isRoundedToZero(source, decimals, precision)) {
            // Will always be rounded to 0
        	offset = add(ret, ZERO, offset);

            return trim(ret, offset);
        } else if (Double.isNaN(source) || Double.isInfinite(source)) {
            // Cannot be formated
            offset = add(ret, Double.toString(source).getBytes(), offset);
            return trim(ret, offset);
        }

        boolean isPositive = source >= 0.0;
        source = Math.abs(source);
        int scale = (source >= 1.0) ? decimals : precision;

        long intPart = (long) Math.floor(source);
        double tenScale = tenPowDouble(scale);
        double fracUnroundedPart = (source - intPart) * tenScale;
        long fracPart = Math.round(fracUnroundedPart);
        if (fracPart >= tenScale) {
            intPart++;
            fracPart = Math.round(fracPart - tenScale);
        }
        if (fracPart != 0L) {
            // Remove trailing zeroes
            while (fracPart % 10L == 0L) {
                fracPart = fracPart / 10L;
                scale--;
            }
        }

        if (intPart != 0L || fracPart != 0L) {
            // non-zero value
            if (!isPositive) {
                // negative value, insert sign
            	offset = add(ret, MINUS, offset);
            }
            // append integer part
            offset = add(ret, longBytefier.convert(intPart), offset);
            if (fracPart != 0L) {
                // append fractional part
            	offset = add(ret, DOT, offset);
                // insert leading zeroes
                while (scale > 0 && fracPart < tenPowDouble(--scale)) {
                	offset = add(ret, ZERO, offset);
                }
                offset = add(ret, longBytefier.convert(fracPart), offset);
            }
        } else {
        	offset = add(ret, ZERO, offset);
        }
        
        return trim(ret, offset);
    }
    
    private static int add(byte[] base, byte[] newContent, int offset) {
    	
    	System.arraycopy(newContent, 0, base, offset, newContent.length);
    	
    	return offset + newContent.length;
    }

    private static byte[] trim(byte[] ba, int offset) {
    	
    	byte[] ret = new byte[offset];
    	
    	System.arraycopy(ba, 0, ret, 0, offset);
    	
    	return ret;
    }
    
	
    private static boolean isRoundedToZero(double source, int decimals, int precision) {
    	// Use 4.999999999999999 instead of 5 since in some cases, 5.0 / 1eN > 5e-N (e.g. for N = 37, 42, 45, 66, ...)
    	return source == 0.0 || Math.abs(source) < 4.999999999999999 / tenPowDouble(Math.max(decimals, precision) + 1);
    }
	
    private static double tenPowDouble(int n) {
    	assert n >= 0;
    	return n < POWERS_OF_TEN_DOUBLE.length ? POWERS_OF_TEN_DOUBLE[n] : Math.pow(10, n);
    }
    
    public void getDecimal(double value) {
	
        short[] digits = new short[10];
        double tmp = value - ((int) value) + 0.5 * 1e-10;
        for (int i = 0; i < digits.length && tmp != 0; i++) {
            tmp *= 10;
            digits[i] = (short) tmp;
            tmp -= (int) tmp;
        }
		
	}
	
    public byte[] nativeDoubleToByteArray(Double value) {
		
	    long longValue = Double.doubleToRawLongBits(value);
		
	    return new byte[] {
	        (byte) ((longValue >> 56) & 0xff),
	        (byte) ((longValue >> 48) & 0xff),
	        (byte) ((longValue >> 40) & 0xff),
	        (byte) ((longValue >> 32) & 0xff),
	        (byte) ((longValue >> 24) & 0xff),
	        (byte) ((longValue >> 16) & 0xff),
	        (byte) ((longValue >> 8) & 0xff),
	        (byte) ((longValue >> 0) & 0xff),
	    };
		
	}
}
