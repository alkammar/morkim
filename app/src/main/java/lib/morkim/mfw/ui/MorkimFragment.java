package lib.morkim.mfw.ui;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;

import java.util.UUID;

import lib.morkim.mfw.app.MorkimApp;
import lib.morkim.mfw.domain.Model;

public abstract class MorkimFragment<A extends MorkimApp<M, ?>, M extends Model, V extends UpdateListener, C extends Controller, P extends Presenter>
        extends Fragment
        implements Viewable<V, C, P> {

    private UUID id;

    protected C controller;
    protected P presenter;

    protected int layoutId() {
        return 0;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        id = (savedInstanceState == null) ? UUID.randomUUID() : UUID.fromString(savedInstanceState.getString(VIEWABLE_ID));

        UiComponentHelper.createUiComponents(this, getActivity());
    }

    @Override
    public Bundle getBundledData() {
        return getArguments();
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    @Override
    public void runOnUi(Runnable runnable) {
        getActivity().runOnUiThread(runnable);
    }

    @Override
    public void finish() {
        getFragmentManager()
                .beginTransaction()
                .remove(this)
                .commit();
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

    @Override
    public <T> T getParentListener() {
        return UiComponentHelper.getParentAsListener(this);
    }
}
