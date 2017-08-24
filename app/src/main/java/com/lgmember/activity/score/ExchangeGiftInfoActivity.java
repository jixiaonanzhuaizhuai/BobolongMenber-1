package com.lgmember.activity.score;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.activity.project.ProjectMessageDetailActivity;
import com.lgmember.business.score.ExchangeGiftInfoBusiness;
import com.lgmember.model.Gift;
import com.lgmember.util.Common;
import com.lgmember.util.StringUtil;
import com.lgmember.view.TopBarView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2017/3/8.
 */

public class ExchangeGiftInfoActivity extends BaseActivity implements View.OnClickListener ,TopBarView.onTitleBarClickListener {
    private ImageView iv_gift_img;
    private TextView tv_gift_name,tv_gift_desc,tv_gift_point,tv_gift_number,tv_gift__limit_number;
    private Button btn_change;
    private TopBarView topBar;
    private boolean flag ;

    private String picture;
    private Gift gift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_gift_info);
        flag = getIntent().getBooleanExtra("flag",false);
        String gift_json = getIntent().getStringExtra("gift");
        gift = new Gson().fromJson(gift_json,Gift.class);
        init();
    }
    private void init() {
        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);
        iv_gift_img = (ImageView)findViewById(R.id.iv_gift_img);
        tv_gift_name = (TextView)findViewById(R.id.tv_gift_name);
        tv_gift_desc = (TextView)findViewById(R.id.tv_gift_desc);
        tv_gift_point = (TextView)findViewById(R.id.tv_gift_point);
        tv_gift_number = (TextView)findViewById(R.id.tv_gift_number);
        tv_gift__limit_number = (TextView) findViewById(R.id.tv_gift__limit_number);
        btn_change = (Button)findViewById(R.id.btn_exchange);
        picture = Common.URL_IMG_BASE+gift.getPicture();
        Glide.with(ExchangeGiftInfoActivity.this).load(picture).placeholder(R.mipmap.defaul_background_img).into(iv_gift_img);

        tv_gift_name.setText(""+gift.getName());
        tv_gift_desc.setText(""+gift.getDescription());
        tv_gift_point.setText(""+gift.getPoint());
        tv_gift_number.setText(""+gift.getNumber());
        tv_gift__limit_number.setText(""+gift.getLimit_number());
        btn_change.setOnClickListener(this);

        if (!flag){
            btn_change.setVisibility(View.GONE);
        }
    }



    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_exchange:
                Intent intent = new Intent(ExchangeGiftInfoActivity.this,ExchangeGiftDetailActivity.class);
                intent.putExtra("gift",new Gson().toJson(gift));
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRightClick() {

    }
}
