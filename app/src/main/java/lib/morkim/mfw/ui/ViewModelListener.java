package lib.morkim.mfw.ui;

public interface ViewModelListener {

	public void onModelUpdated(ViewModel viewModel);
	
	public void notifyOnUiThread(Runnable runnable);
}
