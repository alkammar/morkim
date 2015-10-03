package lib.morkim.mfw.usecase;

import android.os.AsyncTask;

public class UseCaseTask extends AsyncTask<Void, Void, Void> {
	
	private BaseUseCase useCase;

	public UseCaseTask(BaseUseCase useCase) {

		this.useCase = useCase;
	}

	@Override
	protected Void doInBackground(Void... params) {
		
		useCase.onPrepare();
		useCase.onExecute();
		publishProgress(params);
		useCase.onSaveModel();
		
		return (Void) null;
	}
	
	protected void reportProgress() {
		publishProgress((Void []) null);
	}

	@Override
	protected void onProgressUpdate(Void... values) {
		useCase.onReportProgress();
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}
}