package io.speejson;

import java.lang.reflect.Field;

import sun.misc.Unsafe;

public class UnsafeHandler {

	public static final Unsafe UNSAFE = init();

	public static final long VALUE_OFFSET = getFieldOffset("value");

	private static Unsafe init() {

		try {
			Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
			unsafeField.setAccessible(true);
			return (Unsafe) unsafeField.get(null);

		} catch (NoSuchFieldException e) {
			return null;
		} catch (IllegalAccessException e) {
			return null;
		}
	}

	private static long getFieldOffset(String fieldName) {

		try {
			
			return UNSAFE.objectFieldOffset(String.class.getDeclaredField(fieldName));
			
		} catch (NoSuchFieldException | SecurityException e) {
			throw new RuntimeException(e);
		}

	}
	
    public static byte[] getValue(String string) {
        return (byte[]) UNSAFE.getObject(string, VALUE_OFFSET);
    }
	

}
