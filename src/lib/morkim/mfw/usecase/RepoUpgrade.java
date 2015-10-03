package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.AppContext;

public abstract class RepoUpgrade extends SyncUseCase {

//	private int versionCode;

	public RepoUpgrade(AppContext appContext) {
		super(appContext);
	}
	
	@Override
	protected void onPrepare() {
		super.onPrepare();
		
//		versionCode = Package.getVersionCode(getAppContext().getContext());
	}

	@Override
	protected void onExecute() {
		
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
	}

	@Override
	protected void onReportProgress() {
		// TODO Auto-generated method stub

	}
	
	@Override
	protected void onSaveModel() {

//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putInt("lastUpdate", versionCode);
//        editor.commit();
	}

}
