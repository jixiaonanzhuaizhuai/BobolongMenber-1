package com.lgmember.activity.score;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lgmember.activity.BaseActivity;
import com.lgmember.activity.R;
import com.lgmember.activity.project.ProjectMessageDetailActivity;
import com.lgmember.business.score.ExchangeGiftBusiness;
import com.lgmember.business.score.ExchangeGiftInfoBusiness;
import com.lgmember.business.score.ScoresInfoBusiness;
import com.lgmember.business.score.ScoresRuleBusiness;
import com.lgmember.business.SmsCodeBusiness;
import com.lgmember.model.Gift;
import com.lgmember.model.ScoresInfo;
import com.lgmember.util.Common;
import com.lgmember.util.StringUtil;
import com.lgmember.view.TopBarView;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Yanan_Wu on 2016/12/19.
 */

public class ExchangeGiftDetailActivity extends BaseActivity implements ScoresInfoBusiness.ScoresInfoHandler,SmsCodeBusiness.GetCodeResultHandler,ExchangeGiftBusiness.ExchangeGiftHandler,TopBarView.onTitleBarClickListener {

    private TextView nameTxt;
    private EditText adressTxt, phoneTxt;
    private ImageView giftImg;
    private String mobile;
    private String sms_capt_tokenTxt = "";
    private String capt = "";
    private TopBarView topBar;
    private Button btn_exchange_gift;

    private AlertDialog dialog;

    private Gift gift;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchangegiftdetail);
       /* Bundle bundle = this.getIntent().getExtras();
        gift_id = bundle.getInt("gift_id");*/
       String gift_json = getIntent().getStringExtra("gift");
        gift = new Gson().fromJson(gift_json,Gift.class);
        init();
        fillData();
    }


    public void init() {

        topBar = (TopBarView)findViewById(R.id.topbar);
        topBar.setClickListener(this);
        adressTxt = (EditText) findViewById(R.id.adressTxt);
        nameTxt = (TextView) findViewById(R.id.nameTxt);
        phoneTxt = (EditText) findViewById(R.id.phoneTxt);
        giftImg = (ImageView) findViewById(R.id.giftImg);
        String picture = Common.URL_IMG_BASE+gift.getPicture();
        Glide.with(ExchangeGiftDetailActivity.this).load(picture).placeholder(R.mipmap.defaul_background_img).into(giftImg);
        btn_exchange_gift = (Button)findViewById(R.id.btn_exchange_gift);
        btn_exchange_gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogPhoneCode();
            }
        });

    }

    private void fillData() {
        ScoresInfoBusiness scoresInfoBusiness = new ScoresInfoBusiness(context);
        //处理结果
        scoresInfoBusiness.setHandler(this);
        scoresInfoBusiness.getScoresInfo();
    }
    @Override
    public void onSuccess(ScoresInfo scoresInfo) {
        nameTxt.setText(scoresInfo.getName());
        adressTxt.setText(scoresInfo.getAddr());
        mobile = scoresInfo.getMobile();
        phoneTxt.setText(mobile+"");

    }

    public void DialogPhoneCode() {
        AlertDialog.Builder adb = new AlertDialog.Builder(ExchangeGiftDetailActivity.this);
        dialog = adb.create();
        View view = getLayoutInflater().inflate(R.layout.dialog_exchange_gift_phone_code, null);
        TextView txt_phone = (TextView) view.findViewById(R.id.txt_phone);
        txt_phone.setText(getText(phoneTxt));
        final Button btn_request_code = (Button) view.findViewById(R.id.btn_request_code);
        class TimeCount extends CountDownTimer {
            public TimeCount(long millisInFuture, long countDownInterval) {
                super(millisInFuture, countDownInterval);
            }
            public void onFinish() {
                btn_request_code.setText("获取验证码");
                btn_request_code.setClickable(true);
            }
            public void onTick(long millisUntilFinished) {
                btn_request_code.setClickable(false);
                btn_request_code.setText(millisUntilFinished / 1000 + "秒后点击重发");
            }
        }
        final TimeCount timeCount = new TimeCount(60000,1000);
        btn_request_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (btn_request_code.isClickable()) {
                    timeCount.start();
                    getCode();
                }
            }
        });
        final EditText edt_code = (EditText)view.findViewById(R.id.edt_code);
        Button btn_ok = (Button) view.findViewById(R.id.btn_ok);
        Button btn_cancle = (Button) view.findViewById(R.id.btn_cancle);
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                capt = getText(edt_code);
                exchangeGift(capt);
                dialog.dismiss();
            }
        });
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.setView(view, 0, 0, 0, 0);
        dialog.show();
        }




    private void exchangeGift(String smsCode) {
        ExchangeGiftBusiness exchangeGiftBusiness = new ExchangeGiftBusiness(context,gift.getId(), getText(nameTxt),getText(adressTxt),getText(phoneTxt),smsCode,sms_capt_tokenTxt);
        exchangeGiftBusiness.setHandler(this);
        exchangeGiftBusiness.exchangeGift();
    }
    private void getCode(){
        SmsCodeBusiness getCodeBusiness = new SmsCodeBusiness(context,getText(phoneTxt));
        //处理结果
        getCodeBusiness.setHandler(this);
        getCodeBusiness.getCode();
    }
    @Override
    public void onRequestCodeSuccess(String s) {
        sms_capt_tokenTxt = s;
    }
    @Override
    public void onArgumentSmsCodeEmpty() {
        showToast("验证码不能为空");
    }
    @Override
    public void onArgumentMobileFormatError() {
        showToast("手机格式不正确");
    }
    @Override
    public void onExchangeGiftSuccess(String s) {
        btn_exchange_gift.setEnabled(false);
        if (TextUtils.equals(s,"兑换成功")){
            btn_exchange_gift.setText(s +"");
        }else {
            showToast(s);
        }
        Intent intent = new Intent();
        intent.setClass(this, ExchangeScoresActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();

    }

    @Override
    public void onBackClick() {
        finish();
    }

    @Override
    public void onRightClick() {
    }

}