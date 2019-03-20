package io.speejson;

import java.lang.reflect.Field;

import io.speejson.exception.InternalToolingInitializationException;
import io.speejson.exception.ReflectionException;
import sun.misc.Unsafe;

public class UnsafeHandler {

	public static final Unsafe UNSAFE = init();

	public static final long VALUE_OFFSET = getFieldOffset("value");
	
	private static final long ARRAY_BASE_OFFSET = (long) UNSAFE.arrayBaseOffset(byte[].class);

	private static Unsafe init() {

		try {
			
			Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
			unsafeField.setAccessible(true);
			
			return (Unsafe) unsafeField.get(null);

		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new InternalToolingInitializationException(e);
		}
	}

	private static long getFieldOffset(String fieldName) {

		try {
			
			return UNSAFE.objectFieldOffset(String.class.getDeclaredField(fieldName));
			
		} catch (NoSuchFieldException | SecurityException e) {
			throw new ReflectionException(e);
		}

	}
	
    public static byte[] getValue(String string) {
        return (byte[]) UNSAFE.getObject(string, VALUE_OFFSET);
    }
	
    private UnsafeHandler() {
		throw new IllegalStateException("Utility class");
    }
    
    public static void copy(byte[] src, byte[] dest) {
    	
    	
    	long srcOffset = ARRAY_BASE_OFFSET + ((long) 0 << 0);
    	
    	long dstOffset = ARRAY_BASE_OFFSET + ((long) 0 << 0);
    	
    	
    	UNSAFE.copyMemory(src,
    			srcOffset,
    			dest,
                dstOffset,
                (long)src.length << 0);
    	
    	
    }
    
    
}
