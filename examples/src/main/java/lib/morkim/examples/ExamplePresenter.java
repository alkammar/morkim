package lib.morkim.examples;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;
import lib.morkim.mfw.ui.Presenter;
import lib.morkim.mfw.ui.Viewable;
import lib.morkim.mfw.ui.lists.ListAdapter;

public class ExamplePresenter extends Presenter<ExampleController, Model, MorkimApp<Model, ?>> implements ExampleUpdateListener {

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
            adapter = new ExampleAdapter(this);

        return adapter;
    }

    @Override
    public int getCount() {
        return controller.getEntities().size();
    }

    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public String getItemNumber(int position) {
        return "" + controller.getEntities().get(position).index;
    }
}
