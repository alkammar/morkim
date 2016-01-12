package lib.morkim.mfw.util;

import android.os.Build;

public final class Device {

	private final static String [] TEST_DEVICE_SERIAL = {"TA9300GL9E", "083c51f101898064"}; 
	
	public static boolean isTestDevice() {
		for (String serial : TEST_DEVICE_SERIAL) {
			if (Build.SERIAL.equals(serial)) {
				return true;
			}
		}
		
		return false;
	}
}
