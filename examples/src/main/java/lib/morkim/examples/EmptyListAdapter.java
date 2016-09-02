package lib.morkim.examples;

import android.view.View;

import lib.morkim.mfw.ui.Presenter;
import lib.morkim.mfw.ui.lists.ListAdapter;
import lib.morkim.mfw.ui.lists.ListItemHolder;

public class EmptyListAdapter extends ListAdapter<Presenter, ListItemHolder> {

	public EmptyListAdapter() {
		super(null);
	}

	@Override
	protected int layoutId() {
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
