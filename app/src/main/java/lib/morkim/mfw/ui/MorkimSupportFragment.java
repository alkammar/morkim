package lib.morkim.mfw.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public abstract class MorkimSupportFragment<A extends MorkimApp<M, ?>, M extends Model, V extends UpdateListener, C extends Controller, P extends Presenter>
        extends Fragment
        implements Viewable<A, M, V, C, P> {

    private UUID id;

    protected C controller;
    protected P presenter;

    protected int layoutId() {
        return 0;
    }

    protected <T> T getParentAsListener() {

        Fragment parentFragment = getParentFragment();
        if (parentFragment != null)
            try {
                return (T) parentFragment;
            } catch (ClassCastException e) {
                throw new ClassCastException(parentFragment.toString()
                        + " must implement interface");
            }
        else {
            Activity activity = getActivity();
            if (activity != null) {
                try {
                    return (T) activity;
                } catch (ClassCastException e) {
                    throw new ClassCastException(activity.toString()
                            + " must implement interface");
                }
            }
        }

        return null;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = (savedInstanceState == null) ? UUID.randomUUID() : UUID.fromString(savedInstanceState.getString(VIEWABLE_ID));

        getMorkimContext().acquireController(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(layoutId(), container, false);
    }

    @Override
    public Bundle getBundledData() {
        return getArguments();
    }

    @Override
    public void attachController(C controller) {

        presenter = createPresenter();
        this.controller = controller;

        this.controller.attachViewable(this);
        this.presenter.setController(controller);
    }

    @Override
    public A getMorkimContext() {
        //noinspection unchecked
        return (A) getActivity().getApplication();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public C getController() {
        return controller;
    }

    @Override
    public V getUpdateListener() {
        return (V) this;
    }

    @Override
    public void onStart() {
        super.onStart();

        controller.bindViews();
    }

    @Override
    public void onStop() {
        super.onStop();

        controller.unbindViews();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (getActivity().isFinishing())
            ((MorkimApp) getActivity().getApplication()).destroyController(this);
    }

    @Override
    public void keepScreenOn(boolean b) {

    }

    @Override
    public UUID getInstanceId() {
        return id;
    }

    @Override
    public void registerPermissionListener(String s, onPermissionResultListener onPermissionResultListener) {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(VIEWABLE_ID, id.toString());
    }
}
