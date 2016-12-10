package lib.morkim.mfw.ui;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import lib.morkim.mfw.app.MorkimApp;

class UiComponentHelper {

	static <V extends UpdateListener, C extends Controller, P extends Presenter> void createUiComponents(Viewable<V, C, P> viewable, Application app) {

		MorkimApp morkimApp;
		try {
			morkimApp = (MorkimApp) app;
		} catch (ClassCastException e) {
			throw new ClassCastException("Application class must extend a " + MorkimApp.class.getSimpleName() + " class");
		}

		//noinspection unchecked
		morkimApp.createUiComponents(viewable);
	}

	@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
	static <T> T getParentAsListener(android.app.Fragment fragment) {

		android.app.Fragment parentFragment = fragment.getParentFragment();
		if (parentFragment != null)
			try {
				return parentFragment instanceof Viewable ? (T) ((Viewable) parentFragment).getChildListener() : (T) parentFragment;
			} catch (ClassCastException e) {
				throw new ClassCastException(parentFragment.toString() + " must override method getChildListener() or implement interface");
			}
		else {
			return getActivityAsListener(fragment);
		}
	}

	static <T> T getParentAsListener(Fragment fragment) {

		Fragment parentFragment = fragment.getParentFragment();
		if (parentFragment != null) {
			try {
				return parentFragment instanceof Viewable ? (T) ((Viewable) parentFragment).getChildListener() : (T) parentFragment;
			} catch (ClassCastException e) {
				throw new ClassCastException(parentFragment.toString() + " must override method getChildListener() or implement interface");
			}
		} else {
			return getActivityAsListener(fragment);
		}
	}

	@Nullable
	private static <T> T getActivityAsListener(Fragment fragment) {
		return getActivityAsListener(fragment.getActivity());
	}

	@Nullable
	private static <T> T getActivityAsListener(android.app.Fragment fragment) {
		return getActivityAsListener(fragment.getActivity());
	}

	private static <T> T getActivityAsListener(Activity activity) {
		if (activity != null) {
			try {
				return activity instanceof Viewable ? (T) ((Viewable) activity).getChildListener() : (T) activity;
			} catch (ClassCastException e) {
				throw new ClassCastException(activity.toString() + " must override method getChildListener() or implement interface");
			}
		}

		return null;
	}
}
