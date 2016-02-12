package lib.morkim.mfw.ui;

import lib.morkim.mfw.ui.lists.ListItemModel;

public abstract class ListController extends Controller {

	public ListController(Viewable viewable) {
		super(viewable);
	}

	/**
	 * Gets the list elements info to be displayed
	 * 
	 * @return Array of list item
	 */
	protected abstract ListItemModel updateListItemModel(ListItemModel item, int position);

}
