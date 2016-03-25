package lib.morkim.mfw.ui.lists;

import android.util.SparseArray;

import java.util.ArrayList;
import java.util.List;

public class ControllerList<T> {

	private List<T> initial;
	private SparseArray<T> added;
	private SparseArray<T> updated;
	private SparseArray<T> removed;

	public ControllerList() {

		initial = new ArrayList<>();
		added = new SparseArray<>();
		updated = new SparseArray<>();
		removed = new SparseArray<>();
	}

	public void addInitial(List<T> list) {
		initial = (List<T>) ((ArrayList) list).clone();
	}

	public void addToAdded(int position, T object) {
		added.put(position, object);
	}

	public void addToRemoved(int position, T object) {
		removed.put(position, object);
	}

	public void addToChanged(int position, T object) {
		updated.put(position, object);
	}

	public List<T> consumeInitial() {
		List<T> list = (List<T>) ((ArrayList) initial).clone();
		initial.clear();

		return list;
	}

	public SparseArray<T> consumeUpdated() {

		SparseArray<T> array = updated.clone();
		updated.clear();

		return array;
	}

	public SparseArray<T> consumeRemoved() {
		SparseArray<T> array = removed.clone();
		removed.clear();

		return array;
	}

	public SparseArray<T> consumeAdded() {
		SparseArray<T> array = added.clone();
		added.clear();

		return array;
	}
}
