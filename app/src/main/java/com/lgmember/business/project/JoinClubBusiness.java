package com.lgmember.business.project;

import android.content.Context;

import com.lgmember.api.HttpHandler;
import com.lgmember.bean.ClubListResultBean;
import com.lgmember.impl.ClubImpl;

/**
 * Created by Yanan_Wu on 2017/2/27.
 */

public class JoinClubBusiness {
    private int club_id;
    private Context context;
    private ClubImpl clubImpl;

    public JoinClubBusiness(Context context, int club_id) {
        super();
        this.context = context;
        this.club_id = club_id;
    }

    // 先验证参数的可发性，再登陆
    public void joinClub() {

        // 判断活动码是否有效
        clubImpl = new ClubImpl();
        clubImpl.joinClub(club_id,handler,context);
    }

    private JoinClubResulHandler handler;

    public interface JoinClubResulHandler extends HttpHandler {

            public void onSuccess();

    }
    public void setHandler(JoinClubResulHandler handler){
        this.handler = handler;
    }

    }