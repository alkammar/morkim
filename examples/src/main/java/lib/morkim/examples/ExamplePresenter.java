package lib.morkim.examples;

import lib.morkim.mfw.ui.Presenter;
import lib.morkim.mfw.ui.lists.ListAdapter;

public class ExamplePresenter extends Presenter<ExampleController> {

    private ExampleAdapter adapter;

    @Override
    protected void onControllerAttached() {
        super.onControllerAttached();

        adapter = new ExampleAdapter(controller);
    }

    String getTextViewText() {
        double count = controller.getCount();
        return count == 0 ? context.getString(R.string.second_text) : "" + count;
    }

    ListAdapter getAdapter() {
        return adapter;
    }

    int getListSize() {
        return controller.getEntities().size();
    }

    String getItemNumber(int position) {
        return "" + controller.getEntities().get(position).index;
    }
}
