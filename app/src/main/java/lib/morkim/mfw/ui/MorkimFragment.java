package lib.morkim.mfw.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import lib.morkim.mfw.app.AppContext;
import lib.morkim.mfw.app.MorkimApp;

/**
 * Created by Kammar on 2/16/2016.
 */
public abstract class MorkimFragment extends Fragment implements Viewable {

    private Controller controller;
    private Presenter presenter;

    protected int layoutId() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(layoutId(), container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        presenter = ((MorkimApp) getMorkimContext()).acquirePresenter(this);
        controller = ((MorkimApp) getMorkimContext()).acquireController(this);
    }

    @Override
    public AppContext getMorkimContext() {
        return (AppContext) getActivity().getApplication();
    }

    @Override
    public Context getContext() {
        return super.getContext();
    }

    @Override
    public Controller getController() {
        return controller;
    }

    @Override
    public Presenter getPresenter() {
        return presenter;
    }
}
