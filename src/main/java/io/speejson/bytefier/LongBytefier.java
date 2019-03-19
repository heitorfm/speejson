package io.speejson.bytefier;

public class LongBytefier implements Bytefier<Long> {

	@Override
	public byte[] convert(Long value) {
		
		int size = stringSize(value);
		
        byte[] ret = new byte[size];
        
        getChars(value, size, ret);
		
        return ret;
	}
	
	private int stringSize(long x) {
        int d = 1;
        if (x >= 0) {
            d = 0;
            x = -x;
        }
        long p = -10;
        for (int i = 1; i < 19; i++) {
            if (x > p)
                return i + d;
            p = 10 * p;
        }
        return 19 + d;
    }
	
	private int getChars(long i, int index, byte[] buf) {
        long q;
        int r;
        int charPos = index;

        boolean negative = (i < 0);
        if (!negative) {
            i = -i;
        }

        // Get 2 digits/iteration using longs until quotient fits into an int
        while (i <= Integer.MIN_VALUE) {
            q = i / 100;
            r = (int)((q * 100) - i);
            i = q;
            buf[--charPos] = IntegerBytefier.DigitOnes[r];
            buf[--charPos] = IntegerBytefier.DigitTens[r];
        }

        // Get 2 digits/iteration using ints
        int q2;
        int i2 = (int)i;
        while (i2 <= -100) {
            q2 = i2 / 100;
            r  = (q2 * 100) - i2;
            i2 = q2;
            buf[--charPos] = IntegerBytefier.DigitOnes[r];
            buf[--charPos] = IntegerBytefier.DigitTens[r];
        }

        // We know there are at most two digits left at this point.
        q2 = i2 / 10;
        r  = (q2 * 10) - i2;
        buf[--charPos] = (byte)('0' + r);

        // Whatever left is the remaining digit.
        if (q2 < 0) {
            buf[--charPos] = (byte)('0' - q2);
        }

        if (negative) {
            buf[--charPos] = (byte)'-';
        }
        return charPos;
    }
	
	public byte[] nativeLongToByteArray(Long value) {
		
	    return new byte[] {
		        (byte) ((value >> 56) & 0xff),
		        (byte) ((value >> 48) & 0xff),
		        (byte) ((value >> 40) & 0xff),
		        (byte) ((value >> 32) & 0xff),
		        (byte) ((value >> 24) & 0xff),
		        (byte) ((value >> 16) & 0xff),
		        (byte) ((value >> 8) & 0xff),
		        (byte) ((value >> 0) & 0xff),
		    };
		
	}

}
