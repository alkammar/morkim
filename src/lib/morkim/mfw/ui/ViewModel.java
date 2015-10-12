package lib.morkim.mfw.ui;

import java.util.HashMap;
import java.util.Set;

public class ViewModel {

	private ViewModelListener listener;

	private HashMap<String, Object> current;
	private HashMap<String, Object> updated;

	public ViewModel() {
		current = new HashMap<String, Object>();
		updated = new HashMap<String, Object>();
	}

	public ViewModel set(String key) {
		return set(key, null);
	}

	public ViewModel set(String key, Object value) {

		if (!current.containsKey(key) || current.get(key) != value) {
			current.put(key, value);
			updated.put(key, value);
		}

		return this;
	}

	public Set<String> getKeys() {
		return updated.keySet();
	}

	public Object getValue(String key) {
		return (updated.containsKey(key)) ? updated.get(key) : current.get(key);
	}

	public void register(ViewModelListener listener) {

		synchronized (this) {
			this.listener = listener;
		}
	}

	public void notifyView() {

		synchronized (this) {
			if (!updated.isEmpty()) {
				listener.onModelUpdated(this);
				updated.clear();
			}
		}
	}

	public void unregister() {

		synchronized (this) {
			this.listener = null;
		}
	}

	public boolean hasUpdates() {
		return !updated.isEmpty();
	}

	@Override
	public String toString() {
		return updated.toString();
	}

	public boolean hasListener() {
		return (listener != null);
	}
}