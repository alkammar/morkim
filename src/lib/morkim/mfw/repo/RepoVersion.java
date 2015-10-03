package lib.morkim.mfw.repo;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;


public class RepoVersion extends SpRepo {

	private static final String KEY_REPO_ID = "repo.version";
	private static final String KEY_VERSION = "version";

	public RepoVersion(Context context) {
		super(context);
	}
	
	public int get() {
		Map<String, Object> keysAndDefaults = new HashMap<String, Object>();
		keysAndDefaults.put(KEY_VERSION, 1);
		
		Map<String, Object> map = read(KEY_REPO_ID, keysAndDefaults);
		
		return (int) map.get(KEY_VERSION);
	}
	
	public void set(int version) {
		Map<String, Object> mapToWrite = new HashMap<String, Object>();
		
		mapToWrite.put(KEY_VERSION, version);
		
		write(KEY_REPO_ID, mapToWrite);
	}
}
