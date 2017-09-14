package com.lgmember.impl;

import android.content.Context;

import com.lgmember.api.HttpApi;
import com.lgmember.bean.ClubListResultBean;
import com.lgmember.bean.HttpResultBean;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.business.project.ClubActivityListBusiness;
import com.lgmember.business.project.ClubListBusiness;
import com.lgmember.business.project.JoinClubBusiness;
import com.lgmember.business.project.MyClubListBusiness;
import com.lgmember.util.Common;
import com.lgmember.util.JsonUtil;
import com.tsy.sdk.myokhttp.MyOkHttp;
import com.tsy.sdk.myokhttp.response.JsonResponseHandler;

import org.json.JSONObject;

public class ClubImpl extends HttpApi {

    public void getClubList(int pageNo, int pageSize, String search, final ClubListBusiness.ClubListResulHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_CLUB_LIST)
                .addParam("pageNo", String.valueOf(pageNo))
                .addParam("pageSize", String.valueOf(pageSize))
                .addParam("search", search)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        ClubListResultBean clubListResultBean = JsonUtil.parseJsonWithGson(response.toString(), ClubListResultBean.class);

                        if (clubListResultBean.getCode() == 0) {
                            handler.onSuccess(clubListResultBean);
                        } else {
                            handler.onError(clubListResultBean.getCode());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);
                    }
                });
    }

    public void getClubActivityList(int pageNo, int pageSize, String search, int tag, int club_id, final ClubActivityListBusiness.ClubActivityListResulHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_CLUB_ACTIVITY_LIST)
                .addParam("pageNo", String.valueOf(pageNo))
                .addParam("pageSize", String.valueOf(pageSize))
                .addParam("search", search)
                .addParam("tag", String.valueOf(tag))
                .addParam("club", String.valueOf(club_id))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        ProjectMessageBean projectMessageBean = JsonUtil.parseJsonWithGson(response.toString(), ProjectMessageBean.class);

                        if (projectMessageBean.getCode() == 0) {
                            handler.onSuccess(projectMessageBean);
                        } else {
                            handler.onError(projectMessageBean.getCode());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);
                    }
                });
    }

    public void joinClub(int club_id, final JoinClubBusiness.JoinClubResulHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_ADD_CLUB)
                .addParam("club_id", String.valueOf(club_id))
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        HttpResultBean httpResultBean = JsonUtil.parseJsonWithGson(response.toString(), HttpResultBean.class);

                        if (httpResultBean.getCode() == 0) {
                            handler.onSuccess();
                        } else {
                            handler.onError(httpResultBean.getCode());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);
                    }
                });
    }
    public void myClubList(final MyClubListBusiness.MyClubListResulHandler handler, Context context) {
        //判断没有网络应该如何处理
        if (!app.isNetWorkEnable(context)) {
            handler.onNetworkDisconnect();
        }

        MyOkHttp mMyOkhttp = new MyOkHttp(okHttpClient());
        mMyOkhttp.get()
                .url(Common.URL_MY_CLUB_LIST)
                .tag(this)
                .enqueue(new JsonResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, JSONObject response) {

                        ClubListResultBean clubListResultBean = JsonUtil.parseJsonWithGson(response.toString(), ClubListResultBean.class);

                        if (clubListResultBean.getCode() == 0) {
                            handler.onSuccess(clubListResultBean);
                        } else {
                            handler.onError(clubListResultBean.getCode());
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, String error_msg) {
                        handler.onFailed(statusCode, error_msg);
                    }
                });
    }

}

