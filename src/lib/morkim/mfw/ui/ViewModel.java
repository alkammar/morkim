package lib.morkim.mfw.ui;

import java.util.HashMap;
import java.util.Set;

public class ViewModel {

	private ViewModelListener listener;
	private HashMap<String, Object> map;

	public ViewModel() {
		map = new HashMap<String, Object>();
	}

	public ViewModel set(String key) {
		return set(key, null);
	}

	public ViewModel set(String key, Object value) {
		map.put(key, value);
		return this;
	}

	public Set<String> getKeys() {
		return map.keySet();
	}

	public Object getValue(String key) {
		return map.get(key);
	}

	public void register(ViewModelListener listener) {
		
		synchronized (this) {
			this.listener = listener;
		}
	}

	public void notifyListener() {

		synchronized (this) {
			listener.onChanged(this);
			map.clear();
		}
	}

	public void unregister() {
		
		synchronized (this) {
			this.listener = null;
		}
	}
	
	@Override
	public String toString() {
		return map.toString();
	}

	public boolean hasListener() {
		return (listener != null);
	}
}