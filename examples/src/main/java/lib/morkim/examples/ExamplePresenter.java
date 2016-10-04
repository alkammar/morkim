package lib.morkim.examples;

import lib.morkim.mfw.ui.Presenter;

class ExamplePresenter extends Presenter<ExampleScreenController> {

    public ExamplePresenter() {
        super();
    }

    String getTextViewText() {
        double count = controller.getCount();
        return count == 0 ? context.getString(R.string.second_text) : "" + count;
    }

    int getListSize() {
        return controller.getEntities().size();
    }

    String getItemNumber(int position) {
        return "" + controller.getEntities().get(position).index;
    }
}
