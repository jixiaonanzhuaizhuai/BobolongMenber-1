package com.lgmember.business.project;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.ClubListResultBean;
import com.lgmember.impl.ClubImpl;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class MyClubListBusiness {
    private Context context;
    private ClubImpl clubImpl;

    public MyClubListBusiness(Context context) {
        super();
        this.context = context;
    }

    // 先验证参数的可发性，再登陆
    public void myClubList() {

        // 判断活动码是否有效
        clubImpl = new ClubImpl();
        clubImpl.myClubList(handler,context);
    }

    private MyClubListResulHandler handler;

    public interface MyClubListResulHandler extends HttpHandler {

            public void onSuccess(ClubListResultBean clubListResultBean);

    }
    public void setHandler(MyClubListResulHandler handler){
        this.handler = handler;
    }

    }