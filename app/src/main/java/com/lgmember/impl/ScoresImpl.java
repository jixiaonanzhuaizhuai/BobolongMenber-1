package com.lgmember.impl;

import android.content.Context;
import android.util.Log;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.HistoryScoresBean;
import com.lgmember.bean.HttpResultBean;
import com.lgmember.bean.MemberResultBean;
import com.lgmember.bean.ScoresInfoResultBean;
import com.lgmember.bean.ScoresRuleResultBean;
import com.lgmember.business.score.HistoryScoresBusiness;
import com.lgmember.business.score.ScoresInfoBusiness;
import com.lgmember.business.score.ScoresRuleBusiness;
import com.lgmember.business.score.StartGetScoresBusiness;
import com.lgmember.business.score.UpgradeScoresBusiness;
import com.lgmember.model.Member;
import com.lgmember.model.ScoresInfo;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;


public class ScoresImpl extends HttpApi {

    public void upgradeScores(int level,int point_before,int point_after,final UpgradeScoresBusiness.UpgradeScoresHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        //http post的json数据格式：  {"name": "****","pwd": "******"}
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("member_level", level);
            jsonObject.put("before_point", point_before);
            jsonObject.put("after_point", point_after);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_SCORES_UPGRADE)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        HttpResultBean httpResultBean = JsonUtil.parseJsonWithGson(response.toString(),HttpResultBean.class);
                        int code = httpResultBean.getCode();
                        if (code == 0){
                            handler.onSuccess();
                        }else {
                            handler.onError(code);
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }
    public void getScoresRule(final ScoresRuleBusiness.ScoresRuleHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_SCORES_RULE)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        ScoresRuleResultBean scoresRuleResultBean = JsonUtil.parseJsonWithGson(response.toString(),ScoresRuleResultBean.class);
                        int code = scoresRuleResultBean.getCode();
                        if (code == 0){
                            handler.onSuccess(scoresRuleResultBean.getData());
                        }else {
                            handler.onError(code);
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }

    public void startGetScores(final StartGetScoresBusiness.StartGetScoresHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_START_SCORES)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {
                        HttpResultBean httpResultBean = JsonUtil.parseJsonWithGson(response.toString(),HttpResultBean.class);
                        int code = httpResultBean.getCode();
                        if (code == 0){
                            handler.onSuccess();
                        }else {
                            handler.onError(code);
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }

    public void getScoresInfo(final ScoresInfoBusiness.ScoresInfoHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }
        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_SCORES_INFORMATION)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        ScoresInfoResultBean scoresInfoResultBean =
                                JsonUtil.parseJsonWithGson(response
                                        .toString(),ScoresInfoResultBean.class);
                        ScoresInfo scoresInfo = scoresInfoResultBean.getData();
                        if (scoresInfoResultBean.getCode() == 0) {
                            Log.i("----99999----", "onSuccess: "+scoresInfo);
                            handler.onSuccess(scoresInfo);
                        }else {
                            handler.onError(scoresInfoResultBean.getCode());
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }
    public void getHistoryScores(int pageNo, int pageSize, final HistoryScoresBusiness.HistoryScoresResultHandler handler, Context context){
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        //http post的json数据格式：  {"name": "****","pwd": "******"}
        final JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNo", 1);
            jsonObject.put("pageSize", 10);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.post()
                .url(Common.URL_HISTORY_SCORES)
                .jsonParams(jsonObject.toString())
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {


                        HistoryScoresBean historyScoresBean = JsonUtil.parseJsonWithGson(response.toString(),HistoryScoresBean.class);
                        if (historyScoresBean.getCode() == 0){
                            handler.onHisSuccess(historyScoresBean);
                        }else {
                            handler.onError(historyScoresBean.getCode());
                        }


                    }
                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode,error_msg);
                    }
                });
    }


}
