package lib.morkim.mfw.ui;

import lib.morkim.mfw.adapters.Transition;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public abstract class Navigation {

	public abstract void navigate(Viewable source, String destination);

	public static void to(Context context, Class<?> cls) {
		to(context, cls, Transition.NONE);
	}

	public static void to(Context context, Class<?> cls, Transition transition) {

		Intent intent = new Intent(context, cls);
		intent.putExtra(Screen.KEY_SCREEN_TRANSITION, transition.ordinal());
		context.startActivity(intent);
	}

	public static void toGooglePlay(Context context) {
		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
				.parse(googlePlayBaseUrl()
						+ context.getPackageName())));
	}

	public static void showSharingList(Context context, CharSequence title) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(
				Intent.EXTRA_TEXT,
				Uri.parse(googlePlayBaseUrl()
						+ context.getPackageName()).toString());
		context.startActivity(Intent.createChooser(sharingIntent, title));
	}

	private static String googlePlayBaseUrl() {
		return "https://play.google.com/store/apps/details?id=";
	}

	public static void reload(Context context, Transition transition) {
		to(context, context.getClass(), transition);
		((Activity) context).finish();
	}

}