package lib.morkim.examples;

import lib.morkim.mfw.mvp.PendingViewableUpdate;
import lib.morkim.mfw.ui.UpdateListener;

@PendingViewableUpdate
public interface ExampleUpdateListener extends UpdateListener {

	void initializeTextView();

	void updateTextView();

	void doSomeAction();

	void initializeList();
	void initializeListData();
	void updateListItem(int position);

	void updateSomethingWhenViewableNotAvailable();
}
