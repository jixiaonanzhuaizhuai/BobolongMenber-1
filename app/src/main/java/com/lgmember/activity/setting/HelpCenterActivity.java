package com.lgmember.activity.setting;

import android.os.Bundle;

import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.view.TopBarView;

/**
 * Created by Yanan_Wu on 2017/9/5.
 */

public class HelpCenterActivity extends BaseActivity implements TopBarView.onTitleBarClickListener{

    private TopBarView topBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_center);
        topBar = (TopBarView) findViewById(R.id.topbar);
        topBar.setClickListener(this);
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}
