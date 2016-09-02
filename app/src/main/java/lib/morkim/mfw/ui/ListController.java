package lib.morkim.mfw.ui;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.ui.lists.ListItemModel;

public abstract class ListController extends Controller {

	public ListController(MorkimApp morkimApp) {
		super(morkimApp);
	}

	/**
	 * Gets the list elements info to be displayed
	 * 
	 * @return Array of list item
	 */
	protected abstract ListItemModel updateListItemModel(ListItemModel item, int position);

}
