package lib.morkim.mfw.ui;

import java.util.HashMap;
import java.util.Set;

public class ViewModel {

	private ViewModelListener listener;
	private static ViewModelListener emptyListener = new EmptyViewModelListener();

	private HashMap<String, Object> map;

	public ViewModel() {
		map = new HashMap<String, Object>();

		listener = emptyListener;
	}

	public ViewModel set(String key) {
		return set(key, null);
	}

	public ViewModel set(String key, Object value) {

		map.put(key, value);
		return this;
	}

	public void remove(String tag) {
		map.remove(tag);
	}

	public Object getValue(String key) {
		return map.get(key);
	}

	public void register(ViewModelListener listener) {

		synchronized (this) {
			this.listener = listener;
		}
	}

	public void notifyView() {

		synchronized (this) {
			listener.onModelUpdated(this);
		}
	}

	public void notifyUiView() {

		synchronized (this) {
			listener.notifyOnUiThread(new Runnable() {

				@Override
				public void run() {

					synchronized (ViewModel.this) {
						listener.onModelUpdated(ViewModel.this);
					}
				}
			});
		}
	}

	public void unregister() {

		synchronized (this) {
			this.listener = emptyListener;
		}
	}

	@Override
	public String toString() {
		return map.toString();
	}

	public boolean hasListener() {
		return (listener != emptyListener);
	}

	public Set<String> getKeys() {
		return map.keySet();
	}
}