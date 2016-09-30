package lib.morkim.mfw.ui;


import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v4.app.Fragment;

import lib.morkim.mfw.app.MorkimApp;

class UiComponentHelper {

	static <V extends UpdateListener, C extends Controller, P extends Presenter> void createUiComponents(Viewable<V, C, P> viewable, Activity activity) {

		try {
			MorkimApp morkimApp = (MorkimApp) activity.getApplication();
			//noinspection unchecked
			morkimApp.createUiComponents(viewable);
		} catch (ClassCastException e) {
			throw new ClassCastException("Application class must extend a " + MorkimApp.class.getSimpleName() + " class");
		}
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	static <T> T getParentAsListener(android.app.Fragment fragment) {

		android.app.Fragment parentFragment = fragment.getParentFragment();
		if (parentFragment != null)
			try {
				return (T) parentFragment;
			} catch (ClassCastException e) {
				throw new ClassCastException(parentFragment.toString()
						+ " must implement interface");
			}
		else {
			Activity activity = fragment.getActivity();
			if (activity != null) {
				try {
					return (T) activity;
				} catch (ClassCastException e) {
					throw new ClassCastException(activity.toString()
							+ " must implement interface");
				}
			}
		}

		return null;
	}

	static <T> T getParentAsListener(Fragment fragment) {

		Fragment parentFragment = fragment.getParentFragment();
		if (parentFragment != null)
			try {
				return (T) parentFragment;
			} catch (ClassCastException e) {
				throw new ClassCastException(parentFragment.toString() + " must implement interface");
			}
		else {
			Activity activity = fragment.getActivity();
			if (activity != null) {
				try {
					return (T) activity;
				} catch (ClassCastException e) {
					throw new ClassCastException(activity.toString() + " must implement interface");
				}
			}
		}

		return null;
	}
}
