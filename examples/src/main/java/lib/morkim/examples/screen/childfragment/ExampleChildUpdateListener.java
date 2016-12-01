package lib.morkim.examples.screen.childfragment;

import lib.morkim.mfw.ui.UpdateListener;

//@PendingViewableUpdate
public interface ExampleChildUpdateListener extends UpdateListener {

	void initializeTextView();

	void updateTextView();

	void doSomeAction();

	void initializeList();
	void initializeListData();
	void updateListItem(int position, String param);
}
