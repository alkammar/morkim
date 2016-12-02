package lib.morkim.mfw.ui;

import android.view.View;

import lib.morkim.mfw.ui.lists.ListAdapter;
import lib.morkim.mfw.ui.lists.ListItemHolder;

public class EmptyListAdapter extends ListAdapter<Controller, Presenter, ListItemHolder> {

	public EmptyListAdapter() {
		super(null, null);
	}

	@Override
	protected int layoutId(int viewType) {
		return 0;
	}

	@Override
	public int getItemCount() {
		return 0;
	}

	@Override
	protected ListItemHolder holdView(View view, int i) {
		return null;
	}

	@Override
	public void onBindViewHolder(ListItemHolder holder, int position) {

	}
}
