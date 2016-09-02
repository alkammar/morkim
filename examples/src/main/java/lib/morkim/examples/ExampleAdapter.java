package lib.morkim.examples;

import android.view.View;
import android.widget.TextView;

import lib.morkim.mfw.ui.lists.ListAdapter;
import lib.morkim.mfw.ui.lists.ListItemHolder;

public class ExampleAdapter extends ListAdapter<ExamplePresenter, ExampleAdapter.ExampleItemHolder> {

	public ExampleAdapter(ExamplePresenter presenter) {
		super(presenter);
	}

	@Override
	protected int layoutId() {
		return R.layout.example_list_item;
	}

	@Override
	public int getItemCount() {
		return presenter.getListSize();
	}

	@Override
	protected ExampleItemHolder holdView(View convertView, int position) {
		return new ExampleItemHolder(convertView);
	}

	@Override
	public void onBindViewHolder(ExampleItemHolder holder, int position) {
		holder.textView.setText(presenter.getItemNumber(position));
	}

	static class ExampleItemHolder extends ListItemHolder {

		TextView textView;

		public ExampleItemHolder(View itemView) {
			super(itemView);

			textView = ((TextView) itemView.findViewById(R.id.tv_example_item_text_view));
		}
	}
}