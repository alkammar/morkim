package lib.morkim.mfw.ui;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

/**
 * Created by Kammar on 2/16/2016.
 */
public abstract class ToolbarScreen extends Screen {

    protected abstract int toolbarId();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Toolbar toolbar = (Toolbar) findViewById(toolbarId());
        setSupportActionBar(toolbar);
    }
}
