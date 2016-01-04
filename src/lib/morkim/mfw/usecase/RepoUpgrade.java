package lib.morkim.mfw.usecase;

import lib.morkim.mfw.app.AppContext;

public abstract class RepoUpgrade extends UseCase {

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
	protected UseCaseResult onExecute() {
		
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
