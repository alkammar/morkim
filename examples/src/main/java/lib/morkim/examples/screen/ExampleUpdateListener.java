package lib.morkim.examples.screen;

import lib.morkim.mfw.mvp.PendingViewableUpdate;

@PendingViewableUpdate
public interface ExampleUpdateListener extends ExampleBaseUpdateListener {

	void doSomeAction();

	void initializeList();
	void initializeListData();
	void updateListItem(int position);

	void updateSomethingWhenViewableNotAvailable();
}
