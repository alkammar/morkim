package lib.morkim.mfw.ui.lists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public abstract class ListAdapter<M extends ListItemModel, VH extends ListItemHolder> extends RecyclerView.Adapter<VH> {

	protected List<M> listModel;

	private View.OnClickListener onClickListener;

	public ListAdapter() {

	}

	public ListAdapter(List<M> listModel) {
		this.listModel = listModel;
	}

	@Override
	public VH onCreateViewHolder(final ViewGroup parent, int viewType) {

		View v = LayoutInflater.from(parent.getContext()).inflate(layoutId(), parent, false);
		v.setOnClickListener(onClickListener);

		return holdView(v, 0);
	}

	@Override
	public void onBindViewHolder(VH holder, int position) {

		bindView(holder, position);
	}

	protected abstract int layoutId();

	protected abstract VH holdView(View convertView, int position);

	protected abstract void bindView(VH holder, int position);

	public void updateData(List<M> listModel) {

		this.listModel = listModel;

		notifyDataSetChanged();
	}

	@Override
	public int getItemCount() {

		if (listModel != null)
			return listModel.size();

		return 0;
	}

	public M getItem(int position) {

		if (listModel != null)
			return listModel.get(position);

		return null;
	}

	@Override
	public long getItemId(int position) {

		return 0;
	}

	public boolean isEnabled(int position) {

		if (listModel != null)
			return listModel.get(position).isEnabled;

		return false;
	}

	public void setOnClickListener(View.OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}
}
