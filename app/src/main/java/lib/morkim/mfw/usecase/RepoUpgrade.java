package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.MorkimApp;

public abstract class RepoUpgrade extends MorkimTask {

//	private int versionCode;

	public RepoUpgrade(MorkimApp morkimApp, MorkimTaskListener listener) {
		super(morkimApp, listener);
	}
	
	@Override
	protected void onPrepare() {
		super.onPrepare();
		
//		versionCode = Package.getVersionCode(getAppContext().getContext());
	}

	@Override
	protected TaskResult onExecute() {
		
//		Gateway gateway = getRepos().get(ThunderRepo.DATA_SOURCE_STATE);
//		int currentVersion = gateway.getVersion();
//		
//		DataSourceState dataSourceState = (DataSourceState) gateway.retrieve();
//		
//	    if (dataSourceState.getLastUpdateVersion() != versionCode) {
//	        try {
//	            runUpddates();
//
//	            // Commiting in the preferences, that the update was successful.
//	        } catch(Throwable t) {
//	            // update failed, or cancelled
//	        }
//	    }
		
		return null;
	}
	
	@Override
	protected void onSaveModel() {

//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putInt("lastUpdate", versionCode);
//        editor.commit();
	}

}
