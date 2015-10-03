package lib.morkim.mfw.ui;

import lib.morkim.mfw.adapters.Transition;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

public abstract class Navigation {

	protected Context context;

	public Navigation(Context context) {
		this.context = context;
	}

	public abstract void navigate(MView source, String destination);

	protected void navigateTo(Class<?> cls) {
		navigateTo(cls, Transition.NONE);
	}

	private void navigateTo(Class<?> cls, Transition transition) {
	
		Intent intent = new Intent(context, cls);
		intent.putExtra(Screen.KEY_SCREEN_TRANSITION, transition.ordinal());
		context.startActivity(intent);
	}

	protected void navigateToGooglePlay() {
		context.startActivity(new Intent(Intent.ACTION_VIEW, Uri
				.parse(googlePlayBaseUrl()
						+ context.getPackageName())));
	}

	protected void showSharingList(CharSequence title) {
		
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

//	protected void reloadScreen(Transition transition) {
//		navigateTo(view.getClass(), transition);
//		view.finish();
//	}

}