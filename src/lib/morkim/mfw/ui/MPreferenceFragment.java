package lib.morkim.mfw.ui;

import lib.morkim.mfw.app.MorkimApp;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.FragmentManager;
import android.preference.PreferenceFragment;

@SuppressLint("NewApi")
public abstract class MPreferenceFragment extends PreferenceFragment implements Viewable {
	
	private static final String TAG_CONTROLLER_FRAGMENT = "controller.fragment.tag";

	protected Navigation navigation;
	protected Controller controller;
	protected Presenter presenter;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	
		MorkimApp morkimApp = (MorkimApp) activity.getApplication();
		
		navigation = morkimApp.acquireNavigation();
		
		FragmentManager fm = getFragmentManager();
		controller = (Controller) fm.findFragmentByTag(TAG_CONTROLLER_FRAGMENT);
		if (controller == null) {
			controller = createController();

			fm.beginTransaction().add(controller, TAG_CONTROLLER_FRAGMENT).commit();
		}
	}

	protected abstract Controller createController();
	
	@Override
	public void onResume() {
		super.onResume();

		controller.registerUpdates();
	}
	
	@Override
	public void onPause() {
		super.onPause();

		controller.unregisterUpdates();
	}
	
	@Override
	public void notifyOnUiThread(Runnable runnable) {
		getActivity().runOnUiThread(runnable);
	}
	
	@Override
	public void setNavigation(Navigation navigation) {
		this.navigation = navigation;
	}
}
