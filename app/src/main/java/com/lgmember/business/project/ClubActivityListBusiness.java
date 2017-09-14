package com.lgmember.business.project;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.ProjectMessageBean;
import com.lgmember.impl.ClubImpl;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class ClubActivityListBusiness {
    private int pageNo;
    private int pageSize;
    private String search;
    private int tag;
    private int club_id;
    private Context context;
    private ClubImpl clubImpl;

    public ClubActivityListBusiness(Context context, int pageNo, int pageSize, String search,int tag,int club_id) {
        super();
        this.context = context;
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.search = search;
        this.tag = tag;
        this.club_id = club_id;
    }

    // 先验证参数的可发性，再登陆
    public void getClubActivityList() {

        // 判断活动码是否有效
        clubImpl = new ClubImpl();
        clubImpl.getClubActivityList(pageNo,pageSize,search,tag,club_id,handler,context);
    }

    private ClubActivityListResulHandler handler;

    public interface ClubActivityListResulHandler extends HttpHandler {

            public void onSuccess(ProjectMessageBean projectMessageBean);

    }
    public void setHandler(ClubActivityListResulHandler handler){
        this.handler = handler;
    }

    }