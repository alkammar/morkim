package lib.morkim.mfw.ui;

import android.content.Intent;
import android.net.Uri;

public abstract class Navigation {

	public abstract void navigate(Viewable source, String destination);

	public static void to(Controller controller, Intent intent) {
		controller.getContext().startActivity(intent);
	}

	public static void toGooglePlay(Controller controller) {
		controller.getContext().startActivity(new Intent(Intent.ACTION_VIEW, Uri
				.parse(googlePlayBaseUrl()
						+ controller.getContext().getPackageName())));
	}

	public static void showSharingList(Controller controller, CharSequence title) {
		Intent sharingIntent = new Intent(Intent.ACTION_SEND);
		sharingIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		sharingIntent.setType("text/plain");
		sharingIntent.putExtra(
				Intent.EXTRA_TEXT,
				Uri.parse(googlePlayBaseUrl()
						+ controller.getContext().getPackageName()).toString());
		controller.getContext().startActivity(Intent.createChooser(sharingIntent, title));
	}

	private static String googlePlayBaseUrl() {
		return "https://play.google.com/store/apps/details?id=";
	}

}