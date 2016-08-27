package lib.morkim.examples;

import lib.morkim.mfw.ui.lists.ListAdapter;

public interface ExampleUpdateListener extends ListAdapter.UpdateListener {

	String getItemNumber(int position);
}
