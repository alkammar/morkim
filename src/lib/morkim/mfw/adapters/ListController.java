package lib.morkim.mfw.adapters;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.ui.MView;
import lib.morkim.mfw.ui.lists.ListItemModel;

public abstract class ListController extends Controller {

	public ListController(AppContext context, MView view) {
		super(context, view);
	}

	/**
	 * Gets the list elements info to be displayed
	 * 
	 * @return Array of list item
	 */
	protected abstract ListItemModel updateListItemModel(ListItemModel item, int position);

}
