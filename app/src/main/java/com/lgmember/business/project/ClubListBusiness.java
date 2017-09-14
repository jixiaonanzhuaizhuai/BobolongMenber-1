package com.lgmember.business.project;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.ClubListResultBean;
import com.lgmember.impl.ClubImpl;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class ClubListBusiness {
    private int pageNo;
    private int pageSize;
    private String search;
    private Context context;
    private ClubImpl clubImpl;

    public ClubListBusiness(Context context, int pageNo, int pageSize, String search) {
        super();
        this.context = context;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.search = search;
    }

    // 先验证参数的可发性，再登陆
    public void getClubList() {

        // 判断活动码是否有效
        clubImpl = new ClubImpl();
        clubImpl.getClubList(pageNo,pageSize,search,handler,context);
    }

    private ClubListResulHandler handler;

    public interface ClubListResulHandler extends HttpHandler {

            public void onSuccess(ClubListResultBean clubListResultBean);

    }
    public void setHandler(ClubListResulHandler handler){
        this.handler = handler;
    }

    }