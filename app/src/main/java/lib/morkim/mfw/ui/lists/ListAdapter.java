package lib.morkim.mfw.ui.lists;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import lib.morkim.mfw.ui.helper.ViewHelper;

public abstract class ListAdapter extends BaseAdapter {

	protected Context context;

	protected LayoutInflater layoutInflater;

	protected ViewHelper textViewHelper;

	protected List<ListItemModel> listModel;

	public ListAdapter(Context context, List<ListItemModel> listModel) {

		this.context = context;
		this.listModel = listModel;

		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public ListAdapter(Context context) {

		this.context = context;

		layoutInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ListItemHolder holder = null;

		// item = listModel.get(position);

		if (convertView == null) {

			convertView = inflateLayout(null, position);
			setViewHelper(convertView);

			// init ui elements
			holder = holdView(convertView, position);

			// save holder
			convertView.setTag(holder);

		} else {

			// use saved holder if already recycled
			holder = (ListItemHolder) convertView.getTag();
		}

		// bind data to viewable holder
		bindView(convertView, holder, position);

		return convertView;
	}

	protected abstract View inflateLayout(ViewGroup parent, int position);

	protected abstract ListItemHolder holdView(View convertView,
			int position);

	protected abstract void bindView(ListItemHolder holder, int position);

	protected void bindView(View convertView, ListItemHolder holder,
			int position) {

		// bind data to viewable holder
		bindView(holder, position);
	}

	protected void setViewHelper(View view) {

		textViewHelper = new ViewHelper(context, view);
	}

	public void updateData(ArrayList<ListItemModel> listModel) {

		this.listModel = listModel;

		notifyDataSetChanged();
	}

	@Override
	public int getCount() {

		if (listModel != null)
			return listModel.size();

		return 0;
	}

	@Override
	public Object getItem(int position) {

		if (listModel != null)
			return listModel.get(position);

		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	@Override
	public boolean isEnabled(int position) {

		if (listModel != null)
			return listModel.get(position).isEnabled;

		return false;
	}
}
