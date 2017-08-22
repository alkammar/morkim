package lib.morkim.mfw.ui.lists;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lib.morkim.mfw.ui.Controller;
import lib.morkim.mfw.ui.Presenter;

public abstract class ListAdapter<C extends Controller, P extends Presenter, VH extends ListItemHolder> extends RecyclerView.Adapter<VH> {

	private View.OnClickListener onClickListener;
	private View.OnLongClickListener onLongClickListener;

	protected P presenter;
	protected C controller;

	public ListAdapter(C controller, P presenter) {
		this.controller = controller;
		this.presenter = presenter;
	}

	@Override
	public VH onCreateViewHolder(final ViewGroup parent, int viewType) {

		View view = LayoutInflater.from(parent.getContext()).inflate(layoutId(viewType), parent, false);
		view.setOnClickListener(onClickListener);
		view.setOnLongClickListener(onLongClickListener);

		VH holder = holdView(view, viewType);

		view.setTag(holder);

		return holder;
	}

	protected abstract int layoutId(int viewType);

	protected abstract VH holdView(View convertView, int position);

	@Override
	public long getItemId(int position) {
		return 0;
	}

	public void setOnClickListener(View.OnClickListener onClickListener) {
		this.onClickListener = onClickListener;
	}

	public void setOnLongClickListener(View.OnLongClickListener onLongClickListener) {
		this.onLongClickListener = onLongClickListener;
	}

	public interface UpdateListener<VH> {

		int onUpdateListSize();

		void onUpdateListItem(VH holder, int position);
	}
}
