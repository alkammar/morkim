package lib.morkim.mfw.repo;

import java.util.HashMap;
import java.util.Set;

public abstract class Filter {
	
	private HashMap<String, Object> map;

	public Filter() {
		map = new HashMap<String, Object>();
	}
	
	public void set(String key, Object value) {
		map.put(key, value);
	}
	
	public Set<String> getKeys() {
		return map.keySet();
	}
	
	public Object getValue(String key) {
		return map.get(key);
	}
}