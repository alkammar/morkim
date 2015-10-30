package lib.morkim.mfw.ui;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.ui.lists.ListItemModel;

public abstract class ListController extends Controller {

	public ListController(AppContext context, Viewable viewable) {
		super(context, viewable);
	}

	/**
	 * Gets the list elements info to be displayed
	 * 
	 * @return Array of list item
	 */
	protected abstract ListItemModel updateListItemModel(ListItemModel item, int position);

}
