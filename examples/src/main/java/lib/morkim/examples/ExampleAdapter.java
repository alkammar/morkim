package lib.morkim.examples;

import android.view.View;
import android.widget.TextView;

import lib.morkim.mfw.ui.lists.ListAdapter;
import lib.morkim.mfw.ui.lists.ListItemHolder;

public class ExampleAdapter extends ListAdapter<ExampleUpdateListener, ExampleAdapter.ExampleItemHolder> {

	public ExampleAdapter(ExampleUpdateListener updateListener) {
		super(updateListener);
	}

	@Override
	protected int layoutId() {
		return R.layout.example_list_item;
	}

	@Override
	protected ExampleItemHolder holdView(View convertView, int position) {

		return new ExampleItemHolder(convertView);
	}

	@Override
	protected void bindView(ExampleItemHolder holder, int position) {

		holder.textView.setText(updateListener.getItemNumber(position));
	}

	static class ExampleItemHolder extends ListItemHolder {

		private TextView textView;

		public ExampleItemHolder(View itemView) {
			super(itemView);

			textView = ((TextView) itemView.findViewById(R.id.tv_example_item_text_view));
		}
	}
}