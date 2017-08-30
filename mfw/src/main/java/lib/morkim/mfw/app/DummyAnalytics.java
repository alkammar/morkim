package lib.morkim.mfw.app;

import android.content.Context;
import android.util.Log;


public class DummyAnalytics implements Analytics {

	public DummyAnalytics(Context context) {
		
	}

	@Override
	public void initialize() {
		
	}

	@Override
	public void sendEvent(String category, String action) {
		Log.i("DummyAnalytics", "category: " + category + ", action: " + action);
	}

	@Override
	public void sendEvent(String category, String action, long value) {
		Log.i("DummyAnalytics", "category: " + category + ", action: " + action + ", value: " + value);
	}

	@Override
	public void sendEvent(String category, String action, String label, long value) {
		Log.i("DummyAnalytics", "category: " + category + ", action: " + action + ", label: " + label + ", value: " + value);
	}

}
