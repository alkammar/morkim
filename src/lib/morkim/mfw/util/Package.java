package lib.morkim.mfw.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.Signature;

public final class Package {

	private final static int[] RELEASE_SIGNATURE_HASH = { -573166621,
		-1115806733 };

	public static boolean isReleaseBuild(Context context) {
		try {
			Signature[] signatures = context.getPackageManager()
					.getPackageInfo(context.getPackageName(),
							PackageManager.GET_SIGNATURES).signatures;
			for (int releaseHash : RELEASE_SIGNATURE_HASH) {
				for (Signature signature : signatures) {
					if (signature.hashCode() == releaseHash)
						return true;
				}
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static String getVersionName(Context context) {
		String versionName = "1.0";
		try {
			versionName = context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		return versionName;
	}


	public static int getVersionCode(Context context){
		try {
			return context.getPackageManager()
					.getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}
		
		return 0;
	}
}
