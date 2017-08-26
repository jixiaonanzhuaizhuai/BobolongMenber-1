package com.lgmember.activity.score;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.model.GiftSend;
import com.lgmember.util.Common;
import com.lgmember.view.TopBarView;

/*
    已兑换礼品详情
 */
public class ExchangeAlreadyGiftDetail extends BaseActivity implements TopBarView.onTitleBarClickListener{
    private ImageView iv_gift_img;
    private TextView tv_gift_name,tv_receive_name,tv_receive_mobile,tv_receive_addr,tv_gift_send_create_time,tv_postcode;
    private TopBarView topBar;

    private String picture;
    private GiftSend giftSend;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_already_gift_detail);
        String gift_json = getIntent().getStringExtra("giftSend");
        giftSend = new Gson().fromJson(gift_json,GiftSend.class);
        init();
    }
    private void init(){
        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);
        iv_gift_img = (ImageView)findViewById(R.id.iv_gift_img);
        tv_gift_name = (TextView)findViewById(R.id.tv_gift_name);
        tv_receive_name = (TextView)findViewById(R.id.tv_receive_name);
        tv_receive_mobile = (TextView)findViewById(R.id.tv_receive_mobile);
        tv_receive_addr = (TextView)findViewById(R.id.tv_receive_addr);
        tv_gift_send_create_time = (TextView) findViewById(R.id.tv_gift_send_create_time);
        tv_postcode = (TextView) findViewById(R.id.tv_postcode);
        picture = Common.URL_IMG_BASE+giftSend.getPicture();
        Glide.with(ExchangeAlreadyGiftDetail.this).load(picture).placeholder(R.mipmap.defaul_background_img).into(iv_gift_img);

        tv_gift_name.setText(""+giftSend.getGift_name());
        tv_receive_name.setText(""+giftSend.getReceive_name());
        tv_receive_mobile.setText(""+giftSend.getReceive_mobile());
        tv_receive_addr.setText(""+giftSend.getReceive_addr());
        String create_time = giftSend.getCreate_time().substring(0,19);
        tv_gift_send_create_time.setText(""+create_time);
        if(giftSend.getPostcode() == null || giftSend.getPostcode().isEmpty()){
            tv_postcode.setText("暂无，请等待管理员添加单号!");
        }else{
            tv_postcode.setText(""+giftSend.getPostcode());
        }

    }
    @Override
    public void onBackClick() {finish();}

    @Override
    public void onRightClick() {

    }
}
