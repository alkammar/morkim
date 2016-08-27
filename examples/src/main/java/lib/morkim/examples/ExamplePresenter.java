package lib.morkim.examples;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.Presenter;
import lib.morkim.mfw.ui.Viewable;
import lib.morkim.mfw.ui.lists.ListAdapter;

public class ExamplePresenter extends Presenter<ExampleController, Model, MorkimApp<Model, ?>> {

    private ExampleAdapter adapter;

    public ExamplePresenter(Viewable<MorkimApp<Model, ?>, ExampleController, ?> viewable) {
        super(viewable);
    }

    public String getTextViewText() {
        double count = controller.getCount();
        return count == 0 ? getContext().getString(R.string.second_text) : "" + count;
    }

    public ListAdapter getAdapter() {

        if (adapter == null)
            adapter = new ExampleAdapter(controller);

        return adapter;
    }

    public int getListSize() {
        return controller.getEntities().size();
    }

    public String getItemNumber(int position) {
        return "" + controller.getEntities().get(position).index;
    }

    public boolean isItemEnabled(int position) {
        return true;
    }
}
