package lib.morkim.mfw.repo;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;

public class SpRepo {

	public static final String SP_DEFAULT = "sp.default";

	private Context context;

	public SpRepo(Context context) {
		this.context = context;
	}

	public Map<String, Object> read(String id, Map<String, Object> keysAndDefaults) {

		HashMap<String, Object> map = new HashMap<String, Object>();

		if (id != null && keysAndDefaults != null) {
			SharedPreferences sp = getSharedPreferences(id);

			Map<String, ?> allValues = sp.getAll();

			for (Entry<String, Object> entry : keysAndDefaults.entrySet()) {

				String key = entry.getKey();
				Object defaultValue = entry.getValue();

				map.put(key, allValues.containsKey(key) ? allValues.get(key) : defaultValue);

			}
		}

		return map;
	}

	public void write(String id, Map<String, Object> mapToWrite) {

		if (mapToWrite != null) {

			SharedPreferences sp = getSharedPreferences(id);
			Editor editor = sp.edit();

			for (Entry<String, Object> entry : mapToWrite.entrySet()) {

				String key = entry.getKey();
				Object value = entry.getValue();

				if (value instanceof Boolean)
					editor.putBoolean(key, (Boolean) value);
				else if (value instanceof Integer)
					editor.putInt(key, (Integer) value);
				else if (value instanceof Float)
					editor.putFloat(key, (Float) value);
				else if (value instanceof Long)
					editor.putLong(key, (Long) value);
				else if (value instanceof String)
					editor.putString(key, (String) value);
			}

			editor.commit();
		}
	}

	public void delete(String id, String ... keys) {

		if (keys.length > 0) {

			SharedPreferences sp = getSharedPreferences(id);
			Editor editor = sp.edit();

			for (String key : keys)
				editor.remove(key);
			
			editor.commit();
		}
	}

	private SharedPreferences getSharedPreferences(String id) {
		if (!isDefaultSharedPreferences(id))
			return context.getSharedPreferences(id, Context.MODE_PRIVATE);
		else {
			return PreferenceManager.getDefaultSharedPreferences(context);
		}
	}

	private boolean isDefaultSharedPreferences(String id) {
		return SP_DEFAULT.equals(id);
	}
}
