package lib.morkim.examples;

import android.content.Context;

import lib.morkim.mfw.ui.Presenter;
import lib.morkim.mfw.ui.lists.ListAdapter;

public class ExamplePresenter extends Presenter<ExampleController> {

    private ExampleAdapter adapter;

    public ExamplePresenter(Context context) {
        super(context);
    }

    public String getTextViewText() {
        double count = controller.getCount();
        return count == 0 ? context.getString(R.string.second_text) : "" + count;
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
}
