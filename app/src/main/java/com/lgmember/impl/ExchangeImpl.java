package com.lgmember.impl;

import android.content.Context;
import android.util.Log;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.ExchangeGiftResultBean;
import com.lgmember.bean.ExchangeSendGiftResultBean;
import com.lgmember.bean.GiftDetailResultBean;
import com.lgmember.bean.HttpResultBean;
import com.lgmember.business.score.ExchangeAllGiftBusiness;
import com.lgmember.business.score.ExchangeGiftBusiness;
import com.lgmember.business.score.ExchangeGiftInfoBusiness;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

public class ExchangeImpl extends HttpApi {

    private String TAG = "---ExchangeImpl--";

    public void getAllExchangeGift(int pageNo, int pageSize, final ExchangeAllGiftBusiness.ExchangeAllGiftHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        //http post的json数据格式：  {"name": "****","pwd": "******"}
        JSONObject jsonObject = new JSONObject();

        try {
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_EXCHANGE_ALL_GIGT)
                .jsonParams(jsonObject.toString())          //与 params 不共存 以 jsonParams 优先
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        ExchangeGiftResultBean exchangeGiftResultBean =
                                JsonUtil.parseJsonWithGson(response.toString(),
                                        ExchangeGiftResultBean.class);
                        int code = exchangeGiftResultBean.getCode();
                        if (code == 0){
                            handler.onSuccess(exchangeGiftResultBean);
                        }else {
                            handler.onError(code);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);

                    }
                });
    }
    public void getSelectExchangeGift(int pageNo, int pageSize, final ExchangeAllGiftBusiness.ExchangeAllGiftHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        //http post的json数据格式：  {"name": "****","pwd": "******"}
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNo", pageNo);
            jsonObject.put("pageSize", pageSize);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_EXCHANGE_SELECT_GIGT)
                .jsonParams(jsonObject.toString())          //与 params 不共存 以 jsonParams 优先
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        ExchangeGiftResultBean exchangeGiftResultBean =
                                JsonUtil.parseJsonWithGson(response.toString(),
                                        ExchangeGiftResultBean.class);
                        int code = exchangeGiftResultBean.getCode();
                        if (code == 0){
                            handler.onSuccess(exchangeGiftResultBean);
                        }else {
                            handler.onError(code);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);
                    }
                });

    }
    public void getAlreadyExchangeGift(int pageNo, int pageSize, final ExchangeAllGiftBusiness.ExchangeSendGiftHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_EXCHANGE_ALREADY_GIGT)
                .addParam("pageNo",String.valueOf(pageNo))
                .addParam("pageSize",String.valueOf(pageSize))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {

                        ExchangeSendGiftResultBean exchangeSendGiftResultBean =
                                JsonUtil.parseJsonWithGson(response.toString(),
                                        ExchangeSendGiftResultBean.class);
                        int code = exchangeSendGiftResultBean.getCode();
                        if (code == 0){
                            handler.onSuccess(exchangeSendGiftResultBean);
                        }else {
                            handler.onError(code);
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);
                    }
                });

    }
    public void getGiftInfo(int gift_id, final ExchangeGiftInfoBusiness.ExchangeGiftInfoHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        //http post的json数据格式：  {"name": "****","pwd": "******"}
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gift_id", gift_id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_EXCHANGE_GIFT_INFO)
                .jsonParams(jsonObject.toString())          //与 params 不共存 以 jsonParams 优先
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {
                        GiftDetailResultBean giftDetailResultBean = JsonUtil.parseJsonWithGson(response.toString(),GiftDetailResultBean.class);

                        if (giftDetailResultBean.getCode() == 0){
                            handler.onSuccess(giftDetailResultBean.getData());
                        }else {
                            handler.onError(giftDetailResultBean.getCode());
                        }
                        //
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);
                    }
                });

    }
    public void exchangeGift(int gift_id, String name, String addr, String mobile, String capt,
                             String sms_capt_token, final ExchangeGiftBusiness.ExchangeGiftHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        //http post的json数据格式：  {"name": "****","pwd": "******"}
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("gift_id", gift_id);
            jsonObject.put("name",name);
            jsonObject.put("addr",addr);
            jsonObject.put("mobile",mobile);
            jsonObject.put("capt",capt);
            jsonObject.put("sms_capt_token",sms_capt_token);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_EXCHANGE_GIFT)
                .jsonParams(jsonObject.toString())          //与 params 不共存 以 jsonParams 优先
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, final JSONObject response) {
                        /**
                         * 返回结果
                         *
                         * @param code   操作代码
                         * @param result 结果 0 成功 1 数量不足 2 积分不足 3 超过个人数量限制
                         */
                        int code = 100;
                        int result = 99;
                        try {
                            code = response.getInt("code");
                            result = response.getInt("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        if (code == 0){
                            if (result == 0){
                                handler.onExchangeGiftSuccess("兑换成功！");
                            }else if (result == 1){
                                handler.onExchangeGiftSuccess("对不起，该礼品已兑换完！");
                            }else if (result == 2){
                                handler.onExchangeGiftSuccess("对不起，该礼品兑换超过个人数量限制！");
                            }
                        }else {
                            handler.onError(code);
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);
                    }
                });

    }

}
