package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.MorkimApp;

public abstract class RepoUpgrade extends AsyncUseCase {

//	private int versionCode;

	public RepoUpgrade(MorkimApp morkimApp, UseCaseListener listener) {
		super(morkimApp, listener);
	}

	@Override
	protected TaskResult onExecute(TaskRequest request) {
		
//		Gateway gateway = getRepo().get(ThunderRepo.DATA_SOURCE_STATE);
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
	protected void onPostExecute() {

//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putInt("lastUpdate", versionCode);
//        editor.commit();
	}

}
